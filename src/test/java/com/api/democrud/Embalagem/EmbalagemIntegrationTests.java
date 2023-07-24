package com.api.democrud.Embalagem;

import com.api.democrud.autentication.AutenticationRequest;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.model.Embalagem;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.EmbalagemRepository;
import com.api.democrud.service.AuthenticationService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
//@SpringBootTest
public class EmbalagemIntegrationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private Flyway flyway;

    private String postRequestBody = "{\n" +
            "    \"nome\":\"Tinta 10mL\",\n" +
            "    \"produtoId\":\"20bfdbf2-4b64-452e-8376-c2a230ef6265\"\n" +
            "}";

    private String putRequestBody = "{\n" +
            "    \"nome\":\"Coca-Cola 80mL\",\n" +
            "    \"produtoId\":\"c50bb329-5be3-431c-b623-050b163df16a\"\n" +
            "}";

    private String idEmbalagemPut = "2c08d298-2722-4731-9dc3-270f8614a93c";
    private String idEmbalagemDelete = "4ddbfdfe-ed3c-4307-8519-55bd2a29e8c7";
    private String token;
    @Autowired
    private AuthenticationService authService;

    @Autowired
    private EmbalagemRepository repository;


    @BeforeEach
    void setUp() {
        RestAssured.reset();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "/embalagem";
        token = authService.authenticate(new AutenticationRequest("admin", "admin")).getAcessToken();
        RestAssured.port = this.port;


        flyway.clean();
       // flyway.baseline();
        flyway.migrate();
    }

//    @Test
//    void retornarStatus200ConsultarEmbalagem() {
//
//        Response response = RestAssured.given()
//                .header("Authorization", "Bearer " + token)
//                .and()
//                .header("Content-Type", ContentType.JSON)
//                .accept(ContentType.JSON)
//                .when()
//                .get()
//                .then().extract().response();
//               // .statusCode(HttpStatus.OK.value());
//
//        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
//    }

    @Test
    void cadastraEmbalagemRetonar201() {
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

        Optional<Embalagem> embalagem = repository.findByNome("Tinta 10mL");

        Assertions.assertTrue(embalagem.isPresent());
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
    }

    @Test
    void editarEmbalagemRetornar204() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .and()
                .header("Content-Type", ContentType.JSON)
                .and()
                .body(putRequestBody)
                .when()
                .put("/" + idEmbalagemPut)
                .then()
                .extract().response();

        Embalagem embalagem = repository.findByNome("Coca-Cola 80mL").orElseThrow(() -> new ElementoNencontradoException("Não encontrado"));

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.statusCode());
        Assertions.assertEquals("Coca-Cola 80mL", embalagem.getNome());
        Assertions.assertEquals(UUID.fromString("c50bb329-5be3-431c-b623-050b163df16a"), embalagem.getProduto().getId());

    }

    @Test
    void excluirEmbalagemRetornar204(){
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .and()
                .header("Content-Type", ContentType.JSON)
                .when()
                .delete("/" + idEmbalagemDelete)
                .then()
                .extract().response();

        Optional<Embalagem> embalagem = repository.findById(UUID.fromString(idEmbalagemDelete));

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(),response.statusCode());
        Assertions.assertFalse(embalagem.isPresent());

    }
}
