package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> findById(Long id);

    Book findBookById(long id);

    Page<Book> findAllProductsPageable(Pageable pageable);

    List<Book> findAll();

    List<Book> sortBookList(List<Book> list, String selectedSort, String selectedOrder);

    Book saveBook(Book book);

    Book editBook(Book book);

    Book deleteBookById(long id);

}