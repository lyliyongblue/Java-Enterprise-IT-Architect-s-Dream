package com.yong.spring.data.jdbc.repository;

import com.yong.spring.data.jdbc.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    public void saveBook() {
        Book book = new Book();
        book.setId("T-P-1000");
        book.setAuthor("Steven");
        book.setTitle("Gardens of the Moon");
        book.setPageCount(590);

        Book save = bookRepository.save(book);
        System.out.println("save: " + save);

        Optional<Book> optional = bookRepository.findById(book.getId());
        optional.ifPresent(value -> System.out.println("query book: " + value));
    }
}
