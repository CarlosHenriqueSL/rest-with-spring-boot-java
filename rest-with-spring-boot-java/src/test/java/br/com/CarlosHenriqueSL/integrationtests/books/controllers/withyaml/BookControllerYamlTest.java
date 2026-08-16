package br.com.CarlosHenriqueSL.integrationtests.books.controllers.withyaml;

import br.com.CarlosHenriqueSL.config.TestConfigs;
import br.com.CarlosHenriqueSL.integrationtests.books.controllers.withyaml.mapper.YAMLMapper;
import br.com.CarlosHenriqueSL.integrationtests.books.dto.BookDTO;
import br.com.CarlosHenriqueSL.integrationtests.books.dto.xml.PagedModelBook;
import br.com.CarlosHenriqueSL.integrationtests.testcontainers.AbstractIntegrationTest;
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

    @BeforeAll
    static void setUp() {
        yamlObjectMapper = new YAMLMapper();

        book = new BookDTO();
    }

    @Test
    @Order(1)
    void createTest() {
        mockBook();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_CARLOS)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

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
    @Order(2)
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
    @Order(3)
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
    @Order(4)
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
    @Order(5)
    void deleteTest() {
        given(specification)
                .pathParam("id", book.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
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

        BookDTO bookOne = books.getFirst();
        book = bookOne;

        assertNotNull(bookOne.getId());
        assertEquals(215, bookOne.getId());
        assertEquals("Mike Cohn", bookOne.getAuthor());

        Date expectedDateBookOne = Date.from(Instant.parse("1989-03-21T03:00:00.000Z"));
        assertEquals(expectedDateBookOne, bookOne.getLaunchDate());

        assertEquals(80.05, bookOne.getPrice());
        assertEquals("Agile Estimating and Planning", bookOne.getTitle());

        BookDTO bookThree = books.get(2);
        book = bookThree;

        assertNotNull(bookThree.getId());
        assertEquals(42, bookThree.getId());
        assertEquals("Mike Cohn", bookThree.getAuthor());

        Date expectedDateBookThree = Date.from(Instant.parse("1989-07-19T03:00:00.000Z"));
        assertEquals(expectedDateBookThree, bookThree.getLaunchDate());

        assertEquals(35.67, bookThree.getPrice());
        assertEquals("Agile Estimating and Planning", bookThree.getTitle());
    }

    private void mockBook() {
        book.setAuthor("Ralph, Erich Gamma, John Vlissides e Richard Helm");

        Instant instant = Instant.parse("2017-11-29T15:15:13.636Z");
        book.setLaunchDate(Date.from(instant));

        book.setPrice(45.00);
        book.setTitle("Design Patterns");
    }
}
