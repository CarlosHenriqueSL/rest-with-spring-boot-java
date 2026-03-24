package br.com.CarlosHenriqueSL.repositories;

import br.com.CarlosHenriqueSL.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
