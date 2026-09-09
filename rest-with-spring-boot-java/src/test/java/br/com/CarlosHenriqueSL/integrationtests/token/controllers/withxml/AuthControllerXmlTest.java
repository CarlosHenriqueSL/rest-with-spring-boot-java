package br.com.CarlosHenriqueSL.integrationtests.token.controllers.withxml;

import br.com.CarlosHenriqueSL.config.TestConfigs;
import br.com.CarlosHenriqueSL.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.CarlosHenriqueSL.integrationtests.token.dto.AccountCredentialsDTO;
import br.com.CarlosHenriqueSL.integrationtests.token.dto.TokenDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=8888")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerXmlTest extends AbstractIntegrationTest {

    private static XmlMapper xmlObjectMapper;

    private static TokenDTO token;

    @BeforeAll
    static void setUp() {
        xmlObjectMapper = new XmlMapper();
        xmlObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        token = new TokenDTO();
    }

    @Test
    @Order(1)
    void signIn() throws JsonProcessingException {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO("leandro", "admin123");

        var content = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .body(credentials)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        token = xmlObjectMapper.readValue(content, TokenDTO.class);

        assertNotNull(token.getAccessToken());
        assertNotNull(token.getRefreshToken());
    }

    @Test
    @Order(2)
    void refreshToken() throws JsonProcessingException {
        String content = given()
                .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("username", token.getUsername())
                .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + token.getRefreshToken())
                .when()
                .put("{username}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        token = xmlObjectMapper.readValue(content, TokenDTO.class);

        assertNotNull(token.getAccessToken());
        assertNotNull(token.getRefreshToken());
    }
}
