package com.store.BookStore.data.repo;

import com.store.BookStore.data.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookById(long id);
    Book deleteById(long id);
    List<Book> findBooksByTitleOrAuthor(String title, String author);
    List<Book> findBooksBySubject(String subject);
    List<Book> findBooksByAuthorIgnoreCase(String author);
    List<Book> findBooksByTitle(String title);
    List<Book> findBooksByTitleOrAuthorAndSubject(String title, String author, String subject);
    List<Book> findBooksByAuthorAndSubject(String author, String subject);
    List<Book> findBooksByTitleAndSubject(String title, String subject);
}
