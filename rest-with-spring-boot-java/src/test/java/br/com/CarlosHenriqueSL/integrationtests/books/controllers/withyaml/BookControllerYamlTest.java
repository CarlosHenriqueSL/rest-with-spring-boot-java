package br.com.CarlosHenriqueSL.integrationtests.books.controllers.withyaml;

import br.com.CarlosHenriqueSL.config.TestConfigs;
import br.com.CarlosHenriqueSL.integrationtests.mapper.YAMLMapper;
import br.com.CarlosHenriqueSL.integrationtests.books.dto.BookDTO;
import br.com.CarlosHenriqueSL.integrationtests.books.dto.xml.PagedModelBook;
import br.com.CarlosHenriqueSL.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.CarlosHenriqueSL.integrationtests.token.dto.AccountCredentialsDTO;
import br.com.CarlosHenriqueSL.integrationtests.token.dto.TokenDTO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=8888")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static YAMLMapper yamlObjectMapper;

    private static BookDTO book;
    private static TokenDTO token;

    @BeforeAll
    static void setUp() {
        yamlObjectMapper = new YAMLMapper();

        book = new BookDTO();
        token = new TokenDTO();
    }

    @Test
    @Order(1)
    void signIn() {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO("leandro", "admin123");

        token = given().config(RestAssuredConfig
                        .config().encoderConfig(
                                EncoderConfig.encoderConfig().encodeContentTypeAs(
                                        MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT
                                )
                        ))
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
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

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_CARLOS)
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        assertNotNull(token.getAccessToken());
        assertNotNull(token.getRefreshToken());
    }

    @Test
    @Order(2)
    void createTest() {
        mockBook();

        var createdBook = given().config(RestAssuredConfig.
                        config().encoderConfig(
                                EncoderConfig.encoderConfig().encodeContentTypeAs(
                                        MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT
                                )
                        ))
                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(book, yamlObjectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(BookDTO.class, yamlObjectMapper);

        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Ralph, Erich Gamma, John Vlissides e Richard Helm", createdBook.getAuthor());

        Date expectedDate = Date.from(Instant.parse("2017-11-29T15:15:13.636Z"));
        assertEquals(expectedDate, createdBook.getLaunchDate());

        assertEquals(45.00, createdBook.getPrice());
        assertEquals("Design Patterns", createdBook.getTitle());
    }

    @Test
    @Order(3)
    void updateTest() {
        book.setAuthor("Ralph Johnson, Erich Gamma, John Vlissides e Richard Helm");

        var createdBook = given().config(RestAssuredConfig.
                        config().encoderConfig(
                                EncoderConfig.encoderConfig().encodeContentTypeAs(
                                        MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT
                                )
                        ))
                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(book, yamlObjectMapper)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(BookDTO.class, yamlObjectMapper);

        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Ralph Johnson, Erich Gamma, John Vlissides e Richard Helm", createdBook.getAuthor());

        Date expectedDate = Date.from(Instant.parse("2017-11-29T15:15:13.636Z"));
        assertEquals(expectedDate, createdBook.getLaunchDate());

        assertEquals(45.00, createdBook.getPrice());
        assertEquals("Design Patterns", createdBook.getTitle());
    }

    @Test
    @Order(4)
    void findByIdTest() {
        var createdBook = given().config(RestAssuredConfig.
                        config().encoderConfig(
                                EncoderConfig.encoderConfig().encodeContentTypeAs(
                                        MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT
                                )
                        ))
                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", book.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(BookDTO.class, yamlObjectMapper);

        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Ralph Johnson, Erich Gamma, John Vlissides e Richard Helm", createdBook.getAuthor());

        Date expectedDate = Date.from(Instant.parse("2017-11-29T02:00:00.000Z"));
        assertEquals(expectedDate, createdBook.getLaunchDate());

        assertEquals(45.00, createdBook.getPrice());
        assertEquals("Design Patterns", createdBook.getTitle());
    }

    @Test
    @Order(5)
    @Disabled("This test does NOT applies to Books")
    void disableTest() {
        var createdBook = given().config(RestAssuredConfig.
                        config().encoderConfig(
                                EncoderConfig.encoderConfig().encodeContentTypeAs(
                                        MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT
                                )
                        ))
                .spec(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", book.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(BookDTO.class, yamlObjectMapper);

        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Ralph Johnson, Erich Gamma, John Vlissides e Richard Helm", createdBook.getAuthor());

        Date expectedDate = Date.from(Instant.parse("2017-11-29T15:15:13.636Z"));
        assertEquals(expectedDate, createdBook.getLaunchDate());

        assertEquals(45.00, createdBook.getPrice());
        assertEquals("Design Patterns", createdBook.getTitle());
    }

    @Test
    @Order(6)
    void deleteTest() {
        given(specification)
                .pathParam("id", book.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(7)
    void findAllTest() {
        var response = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .queryParams("page", 2, "size", 3, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PagedModelBook.class, yamlObjectMapper);

        List<BookDTO> books = response.getContent();

        assertNotNull(books);
        assertEquals(3, books.size());

        for (BookDTO b : books) {
            assertNotNull(b.getId());
            assertTrue(b.getId() > 0);
            assertEquals("Mike Cohn", b.getAuthor());
            assertEquals("Agile Estimating and Planning", b.getTitle());
            assertNotNull(b.getLaunchDate());
            assertNotNull(b.getPrice());
        }
    }

    private void mockBook() {
        book.setAuthor("Ralph, Erich Gamma, John Vlissides e Richard Helm");

        Instant instant = Instant.parse("2017-11-29T15:15:13.636Z");
        book.setLaunchDate(Date.from(instant));

        book.setPrice(45.00);
        book.setTitle("Design Patterns");
    }
}
