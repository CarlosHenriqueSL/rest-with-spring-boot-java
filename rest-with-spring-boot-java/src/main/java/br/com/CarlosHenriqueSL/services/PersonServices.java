package br.com.CarlosHenriqueSL.services;

import br.com.CarlosHenriqueSL.exception.ResourceNotFoundException;
import br.com.CarlosHenriqueSL.model.Person;
import br.com.CarlosHenriqueSL.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public Person findById(Long id) {
        logger.info("Finding one Person!");

        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public Person create(Person person) {
        logger.info("Creating one Person!");

        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one Person!");

        Person entity = repository.findById(person.getId()).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }

    public void delete(Long id) {
        logger.info("Deleting one Person!");

        Person entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }

    public List<Person> findAll() {
        logger.info("Finding all People!");

        return repository.findAll();
    }

    private Person mockPerson(int i) {

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Firstname " + i);
        person.setLastName("Lastname " + i);
        person.setAddress("Some Address in Brazil");
        person.setGender("Male");
        return person;
    }
}
