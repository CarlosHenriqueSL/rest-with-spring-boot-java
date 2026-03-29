package br.com.CarlosHenriqueSL.unittests.mapper;

import br.com.CarlosHenriqueSL.data.dto.PersonDTO;
import br.com.CarlosHenriqueSL.model.Person;
import br.com.CarlosHenriqueSL.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static br.com.CarlosHenriqueSL.ObjectMapper.parseListObjects;
import static br.com.CarlosHenriqueSL.ObjectMapper.parseObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectMapperTests {
    MockPerson inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToDTOTest() {
        PersonDTO output = parseObject(inputObject.mockEntity(), PersonDTO.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test 0", output.getFirstName());
        assertEquals("Last Name Test 0", output.getLastName());
        assertEquals("Address Test 0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseEntityListToDTOListTest() {
        List<PersonDTO> outputList = parseListObjects(inputObject.mockEntityList(), PersonDTO.class);
        PersonDTO outputZero = outputList.getFirst();

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test 0", outputZero.getFirstName());
        assertEquals("Last Name Test 0", outputZero.getLastName());
        assertEquals("Address Test 0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());

        PersonDTO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test 7", outputSeven.getFirstName());
        assertEquals("Last Name Test 7", outputSeven.getLastName());
        assertEquals("Address Test 7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());

        PersonDTO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test 12", outputTwelve.getFirstName());
        assertEquals("Last Name Test 12", outputTwelve.getLastName());
        assertEquals("Address Test 12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }

    @Test
    public void parseDTOToEntityTest() {
        Person output = parseObject(inputObject.mockDTO(), Person.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test 0", output.getFirstName());
        assertEquals("Last Name Test 0", output.getLastName());
        assertEquals("Address Test 0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseDTOListToEntityListTest() {
        List<Person> outputList = parseListObjects(inputObject.mockDTOList(), Person.class);
        Person outputZero = outputList.getFirst();

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test 0", outputZero.getFirstName());
        assertEquals("Last Name Test 0", outputZero.getLastName());
        assertEquals("Address Test 0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());

        Person outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test 7", outputSeven.getFirstName());
        assertEquals("Last Name Test 7", outputSeven.getLastName());
        assertEquals("Address Test 7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());

        Person outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test 12", outputTwelve.getFirstName());
        assertEquals("Last Name Test 12", outputTwelve.getLastName());
        assertEquals("Address Test 12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }
}
