package br.com.CarlosHenriqueSL.integrationtests.people.controllers.withjson;

import br.com.CarlosHenriqueSL.config.TestConfigs;
import br.com.CarlosHenriqueSL.integrationtests.people.dto.PersonDTO;
import br.com.CarlosHenriqueSL.integrationtests.people.dto.wrapper.json.WrapperPersonDTO;
import br.com.CarlosHenriqueSL.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.CarlosHenriqueSL.integrationtests.token.dto.AccountCredentialsDTO;
import br.com.CarlosHenriqueSL.integrationtests.token.dto.TokenDTO;
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

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=8888")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static PersonDTO person;
    private static TokenDTO token;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonDTO();
        token = new TokenDTO();
    }

    @Test
    @Order(1)
    void singIn() {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO("leandro", "admin123");

        token = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(credentials)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenDTO.class);

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_CARLOS)
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        assertNotNull(token.getAccessToken());
        assertNotNull(token.getRefreshToken());
    }

    @Test
    @Order(2)
    void createTest() throws JsonProcessingException {
        mockPerson();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertEquals("https://en.wikipedia.org/wiki/Linus_Torvalds", createdPerson.getProfileUrl());
        assertEquals("https://en.wikipedia.org/wiki/File:LinuxCon_Europe_Linus_Torvalds_03_(cropped).jpg", createdPerson.getPhotoUrl());
        assertTrue(createdPerson.getEnabled());
    }

    @Test
    @Order(3)
    void updateTest() throws JsonProcessingException {
        person.setLastName("Benedict Torvalds");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertEquals("https://en.wikipedia.org/wiki/Linus_Torvalds", createdPerson.getProfileUrl());
        assertEquals("https://en.wikipedia.org/wiki/File:LinuxCon_Europe_Linus_Torvalds_03_(cropped).jpg", createdPerson.getPhotoUrl());
        assertTrue(createdPerson.getEnabled());
    }

    @Test
    @Order(4)
    void findByIdTest() throws JsonProcessingException {
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertEquals("https://en.wikipedia.org/wiki/Linus_Torvalds", createdPerson.getProfileUrl());
        assertEquals("https://en.wikipedia.org/wiki/File:LinuxCon_Europe_Linus_Torvalds_03_(cropped).jpg", createdPerson.getPhotoUrl());
        assertTrue(createdPerson.getEnabled());
    }

    @Test
    @Order(5)
    void disableTest() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertEquals("https://en.wikipedia.org/wiki/Linus_Torvalds", createdPerson.getProfileUrl());
        assertEquals("https://en.wikipedia.org/wiki/File:LinuxCon_Europe_Linus_Torvalds_03_(cropped).jpg", createdPerson.getPhotoUrl());
        assertFalse(createdPerson.getEnabled());
    }

    @Test
    @Order(6)
    void deleteTest() {
         given(specification)
                .pathParam("id", person.getId())
                    .when()
                .delete("{id}")
                    .then()
                .statusCode(204);
    }

    @Test
    @Order(7)
    void findAllTest() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParams("page", 3, "size", 12, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        WrapperPersonDTO wrapper = objectMapper.readValue(content, WrapperPersonDTO.class);
        List<PersonDTO> people = wrapper.getEmmbedded().getPeople();

        PersonDTO personOne = people.getFirst();
        person = personOne;

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Allin", personOne.getFirstName());
        assertEquals("Emmot", personOne.getLastName());
        assertEquals("7913 Lindbergh Way", personOne.getAddress());
        assertEquals("Male", personOne.getGender());
        assertFalse(personOne.getEnabled());

        PersonDTO personFive = people.get(4);
        person = personFive;

        assertNotNull(personFive.getId());
        assertTrue(personFive.getId() > 0);

        assertEquals("Alonso", personFive.getFirstName());
        assertEquals("Luchelli", personFive.getLastName());
        assertEquals("9 Doe Crossing Avenue", personFive.getAddress());
        assertEquals("Male", personFive.getGender());
        assertFalse(personFive.getEnabled());
    }

    @Test
    @Order(8)
    void findByNameTest() throws JsonProcessingException {

        // {{baseUrl}}/api/person/v1/findPeopleByName/and?page=1&size=5&direction=asc
        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("firstName", "and")
                .queryParams("page", 1, "size", 5, "direction", "asc")
                .when()
                .get("findPeopleByName/{firstName}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        WrapperPersonDTO wrapper = objectMapper.readValue(content, WrapperPersonDTO.class);
        List<PersonDTO> people = wrapper.getEmmbedded().getPeople();

        PersonDTO personOne = people.getFirst();
        person = personOne;

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Cassandra", personOne.getFirstName());
        assertEquals("O'Keefe", personOne.getLastName());
        assertEquals("20163 Summer Ridge Avenue", personOne.getAddress());
        assertEquals("Female", personOne.getGender());
        assertFalse(personOne.getEnabled());

        PersonDTO personFive = people.get(4);
        person = personFive;

        assertNotNull(personFive.getId());
        assertTrue(personFive.getId() > 0);

        assertEquals("Mandel", personFive.getFirstName());
        assertEquals("Tokley", personFive.getLastName());
        assertEquals("8885 Shasta Circle", personFive.getAddress());
        assertEquals("Male", personFive.getGender());
        assertTrue(personFive.getEnabled());
    }

    private void mockPerson() {
        person.setFirstName("Linus");
        person.setLastName("Torvalds");
        person.setAddress("Helsinki - Finland");
        person.setGender("Male");
        person.setEnabled(true);
        person.setProfileUrl("https://en.wikipedia.org/wiki/Linus_Torvalds");
        person.setPhotoUrl("https://en.wikipedia.org/wiki/File:LinuxCon_Europe_Linus_Torvalds_03_(cropped).jpg");
    }
}
