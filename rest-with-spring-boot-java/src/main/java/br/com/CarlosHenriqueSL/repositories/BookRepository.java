package br.com.CarlosHenriqueSL.repositories;

import br.com.CarlosHenriqueSL.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
