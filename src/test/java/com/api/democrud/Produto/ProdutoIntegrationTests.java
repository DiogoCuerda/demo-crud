package com.api.democrud.Produto;

import com.api.democrud.autentication.AutenticationRequest;
import com.api.democrud.dto.request.ProdutoRequestDTO;
import com.api.democrud.enums.CategoriaProdutoEnum;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.exception.ProdutoDuplicadoException;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.ProdutoRepository;
import com.api.democrud.service.AuthenticationService;
import com.api.democrud.service.ProdutoService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProdutoIntegrationTests {


    @LocalServerPort
    private int port;

    private String postRequestBody = "{\n" +
            "    \"nome\": \"Fanta\",\n" +
            "    \"preco\":     4.56,\n" +
            "    \"estoque\":   55,\n" +
            "    \"ativo\":     true,\n" +
            "    \"categoria\": \"0\"\n" +
            "}";
    private String putRequestBody = "{\n" +
            "    \"nome\": \"Cafe\",\n" +
            "    \"preco\":     7.56,\n" +
            "    \"estoque\":   66,\n" +
            "    \"ativo\":     false,\n" +
            "    \"categoria\": \"1\"\n" +
            "}";
    private String idProdutoPut = "6698b118-26fc-4541-8d8f-d00945b015c7";
    private String idProdutoDelete = "c50bb329-5be3-431c-b623-050b163df16a";
    private String token;
    @Autowired
    private ProdutoService service;

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private Flyway flyway1;

    @Autowired
    private AuthenticationService authService1;



    @BeforeEach
    void setUp() throws InterruptedException {
        RestAssured.reset();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = this.port;
        RestAssured.basePath = "/produto";
        token = authService1.authenticate(new AutenticationRequest("admin", "admin")).getAcessToken();

        flyway1.clean();
        flyway1.migrate();
    }

    @Test
    void retornarStatus200ConsultarProdutos() {

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
    void cadastrarProdutoRetornar201() {

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

        Produto produto = repository.findByNome("Fanta").orElseThrow(() -> new ElementoNencontradoException("Não encontrado"));

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        Assertions.assertEquals("Fanta",produto.getNome());
        Assertions.assertEquals( BigDecimal.valueOf(4.56),produto.getPreco());
        Assertions.assertEquals(Integer.valueOf(55),produto.getEstoque());
        Assertions.assertEquals(true,produto.getAtivo());
        Assertions.assertEquals("REVENDA",produto.getCategoria().toString());
    }


    @Test
    void excluirProdutoRetornar204() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .and()
                .header("Content-Type", ContentType.JSON)
                .and()
                .when()
                .delete("/" + idProdutoDelete)
                .then()
                .extract().response();

        Optional<Produto> produto = repository.findByNome("Presunto");

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.statusCode());
        Assertions.assertFalse(produto.isPresent());

    }

    @Test
    void testarFalhaCadastroProdutoDuplicado() {

        ProdutoRequestDTO produto = new ProdutoRequestDTO();
        produto.setAtivo(true);
        produto.setCategoria(CategoriaProdutoEnum.REVENDA);
        produto.setEstoque(50);
        produto.setPreco(BigDecimal.valueOf(50.06));
        produto.setNome("Batata2");

        service.save(produto);

        assertThrows(ProdutoDuplicadoException.class, () -> service.save(produto));

    }
}
