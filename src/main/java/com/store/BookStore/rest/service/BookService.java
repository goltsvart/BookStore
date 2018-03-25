package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {

    Optional<Book> findById(Long id);

    Page<Book> findAllProductsPageable(Pageable pageable);

}