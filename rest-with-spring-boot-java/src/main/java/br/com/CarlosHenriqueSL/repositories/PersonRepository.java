package br.com.CarlosHenriqueSL.repositories;

import br.com.CarlosHenriqueSL.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id =:id")
    void disablePerson(@Param("id") Long id);

    /*
     * CONCAT concatenates firstName with '%' before and after, ignoring any characters in between in the passed parameter
     * @Modifying is not necessary, since this is only a reading query
     */
    @Query("SELECT p FROM Person p WHERE p.firstName LIKE LOWER(CONCAT('%', :firstName, '%'))")
    Page<Person> findPeopleByName(@Param("firstName") String firstName, Pageable pageable);
}