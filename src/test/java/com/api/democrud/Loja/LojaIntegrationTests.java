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

import java.util.*;


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

    private String postRequestBody = "{\n" +
            "    \"nome\":\"loja 4\",\n" +
            "    \"credito\": 5056.66,\n" +
            "    \"produtos\":[\"20bfdbf2-4b64-452e-8376-c2a230ef6265\",\"6698b118-26fc-4541-8d8f-d00945b015c7\"]\n" +
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
       // List<Produto> produtos = produtoRepository.findAllById(new ArrayList<>(Collections.)
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        ///Assertions.assertEquals();
        Assertions.assertEquals("loja 4",loja.getNome());

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
