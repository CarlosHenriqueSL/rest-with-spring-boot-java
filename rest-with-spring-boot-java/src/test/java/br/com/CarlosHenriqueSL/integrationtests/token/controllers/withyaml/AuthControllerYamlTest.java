package br.com.CarlosHenriqueSL.integrationtests.token.controllers.withyaml;

import br.com.CarlosHenriqueSL.config.TestConfigs;
import br.com.CarlosHenriqueSL.integrationtests.mapper.YAMLMapper;
import br.com.CarlosHenriqueSL.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.CarlosHenriqueSL.integrationtests.token.dto.AccountCredentialsDTO;
import br.com.CarlosHenriqueSL.integrationtests.token.dto.TokenDTO;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=8888")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerYamlTest extends AbstractIntegrationTest {

    private static YAMLMapper yamlObjectMapper;

    private static TokenDTO token;

    @BeforeAll
    static void setUp() {
        yamlObjectMapper = new YAMLMapper();

        token = new TokenDTO();
    }

    @Test
    @Order(1)
    void singIn() {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO("leandro", "admin123");

        token = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .config(RestAssuredConfig
                        .config().encoderConfig(
                                EncoderConfig.encoderConfig().encodeContentTypeAs(
                                        MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT
                                )
                        ))
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(credentials, yamlObjectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(TokenDTO.class, yamlObjectMapper);

        assertNotNull(token.getAccessToken());
        assertNotNull(token.getRefreshToken());
    }

    @Test
    @Order(2)
    void refreshToken() {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO("leandro", "admin123");

        token = given()
                .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .config(RestAssuredConfig
                        .config().encoderConfig(
                                EncoderConfig.encoderConfig().encodeContentTypeAs(
                                        MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT
                                )
                        ))
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("username", token.getUsername())
                .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + token.getRefreshToken())
                .body(credentials, yamlObjectMapper)
                .when()
                .put("{username}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(TokenDTO.class, yamlObjectMapper);

        assertNotNull(token.getAccessToken());
        assertNotNull(token.getRefreshToken());
    }
}
