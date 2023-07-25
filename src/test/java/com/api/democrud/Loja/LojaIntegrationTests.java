package com.api.democrud.Loja;

import com.api.democrud.autentication.AutenticationRequest;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.model.Loja;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.LojaRepository;
import com.api.democrud.repository.ProdutoRepository;
import com.api.democrud.service.AuthenticationService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LojaIntegrationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private Flyway flyway;
    @Autowired
    private AuthenticationService authService;

    @Autowired
    private LojaRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;
    private String token;

    private String idLojaDelete = "ca81274e-6518-4bd9-8035-060aaa9af1f0";
    private String idLojaPut = "ca81274e-6518-4bd9-8035-060aaa9af1f0";

    private String postRequestBody = "{\n" +
            "    \"nome\":\"loja 4\",\n" +
            "    \"credito\": 5056.66,\n" +
            "    \"produtos\":[\"20bfdbf2-4b64-452e-8376-c2a230ef6265\",\"6698b118-26fc-4541-8d8f-d00945b015c7\"]\n" +
            "}";

    private String putRequestBody = "{\n" +
            "    \"nome\":\"loja 5\",\n" +
            "    \"credito\": 5056.67,\n" +
            "    \"produtos\":[\"f07db05a-639d-4d4b-b0c3-1114214340cb\"]\n" +
            "}";

    @BeforeEach
    void setUp() {
        RestAssured.reset();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "/loja";
        token = authService.authenticate(new AutenticationRequest("admin", "admin")).getAcessToken();
        RestAssured.port = this.port;
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void editarLojaRetornar201() {

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .and()
                .header("Content-Type", ContentType.JSON)
                .and()
                .body(putRequestBody)
                .when()
                .put("/" + idLojaPut)
                .then()
                .extract().response();


        Loja loja = repository.findById(UUID.fromString(idLojaPut)).orElseThrow(() -> new ElementoNencontradoException("Não encontrado"));

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.statusCode());
        Assertions.assertEquals(UUID.fromString("f07db05a-639d-4d4b-b0c3-1114214340cb"), loja.getProduto().get(0).getId());
        Assertions.assertEquals(1, loja.getProduto().size());
        Assertions.assertEquals("loja 5", loja.getNome());
        Assertions.assertEquals(BigDecimal.valueOf(5056.67), loja.getCredito());

    }

    @Test
    void cadastrarLojaRetornar201() {

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .and()
                .header("Content-Type", ContentType.JSON)
                .and()
                .body(postRequestBody)
                .when()
                .post()
                .then()
                .extract().response();


        Loja loja = repository.findByNome("loja 4").orElseThrow(() -> new ElementoNencontradoException("Não encontrado"));
        List<Produto> produtos = produtoRepository
                .findAllById(Arrays.asList(UUID.fromString("20bfdbf2-4b64-452e-8376-c2a230ef6265"),
                        UUID.fromString("6698b118-26fc-4541-8d8f-d00945b015c7")));

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        Assertions.assertTrue(produtos.get(0).getId().equals(loja.getProduto().get(0).getId()));
        Assertions.assertTrue(produtos.get(1).getId().equals(loja.getProduto().get(1).getId()));
        Assertions.assertEquals("loja 4", loja.getNome());
        Assertions.assertEquals(BigDecimal.valueOf(5056.66), loja.getCredito());

    }

    @Test
    void retornarStatus200ConsultarLoja() {

        RestAssured.given()
                .header("Authorization",
                        "Bearer " + token)
                .and()
                .header("Content-Type", ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    void removerLojaRetornar204() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .and()
                .header("Content-Type", ContentType.JSON)
                .and()
                .when()
                .delete("/" + idLojaDelete)
                .then()
                .extract().response();

        Optional<Loja> loja = repository.findById(UUID.fromString(idLojaDelete));

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.statusCode());
        Assertions.assertFalse(loja.isPresent());

    }

}
