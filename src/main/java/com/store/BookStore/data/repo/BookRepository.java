package com.store.BookStore.data.repo;

import com.store.BookStore.data.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookById(Long id);
}
