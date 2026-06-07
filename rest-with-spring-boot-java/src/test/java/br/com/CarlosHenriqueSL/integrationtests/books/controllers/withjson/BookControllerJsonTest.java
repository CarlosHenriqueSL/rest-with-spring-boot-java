package br.com.CarlosHenriqueSL.integrationtests.books.controllers.withjson;

import br.com.CarlosHenriqueSL.config.TestConfigs;
import br.com.CarlosHenriqueSL.integrationtests.books.dto.BookDTO;
import br.com.CarlosHenriqueSL.integrationtests.books.dto.wrapper.json.WrapperBookDTO;
import br.com.CarlosHenriqueSL.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
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
class BookControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static BookDTO book;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        book = new BookDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockBook();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_CARLOS)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
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
    void updateTest() throws JsonProcessingException {
        book.setAuthor("Ralph Johnson, Erich Gamma, John Vlissides e Richard Helm");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
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
    void findByIdTest() throws JsonProcessingException {
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", book.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
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
    void disableTest() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", book.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
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
    void findAllTest() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParams("page", 2, "size", 3, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        WrapperBookDTO wrapper = objectMapper.readValue(content, WrapperBookDTO.class);
        List<BookDTO> books = wrapper.getEmmbedded().getBooks();

        BookDTO bookOne = books.getFirst();
        book = bookOne;

        assertNotNull(bookOne.getId());
        assertTrue(bookOne.getId() > 0);

        assertEquals("Eric Freeman, Elisabeth Freeman, Kathy Sierra, Bert Bates", bookOne.getAuthor());

        Date expectedDateBookOne = Date.from(Instant.parse("2017-11-07T02:00:00.000Z"));
        assertEquals(expectedDateBookOne, bookOne.getLaunchDate());

        assertEquals(110.00, bookOne.getPrice());
        assertEquals("Head First Design Patterns", bookOne.getTitle());

        BookDTO bookThree = books.get(2);
        book = bookThree;

        assertNotNull(bookThree.getId());
        assertTrue(bookThree.getId() > 0);

        assertEquals("Brian Goetz e Tim Peierls", bookThree.getAuthor());

        Date expectedDateBookThree = Date.from(Instant.parse("2017-11-07T02:00:00.000Z"));
        assertEquals(expectedDateBookThree, bookThree.getLaunchDate());

        assertEquals(80.00, bookThree.getPrice());
        assertEquals("Java Concurrency in Practice", bookThree.getTitle());
    }

    private void mockBook() {
        book.setAuthor("Ralph, Erich Gamma, John Vlissides e Richard Helm");

        Instant instant = Instant.parse("2017-11-29T15:15:13.636Z");
        book.setLaunchDate(Date.from(instant));

        book.setPrice(45.00);
        book.setTitle("Design Patterns");
    }
}
