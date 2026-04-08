package br.com.CarlosHenriqueSL.unittests.mapper.mocks;

import br.com.CarlosHenriqueSL.data.dto.BookDTO;
import br.com.CarlosHenriqueSL.model.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<BookDTO>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setAuthor("Author Number " + number);
        book.setLaunchDate(new Date());
        book.setPrice(number.doubleValue());
        book.setId(number.longValue());
        book.setTitle("Title Test " + number);
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setAuthor("Author Number " + number);
        book.setLaunchDate(new Date());
        book.setPrice(number.doubleValue());
        book.setId(number.longValue());
        book.setTitle("Title Test " + number);
        return book;
    }
}
