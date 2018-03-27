package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.Book;
import com.store.BookStore.data.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Book> findAllProductsPageable(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book findBookById(long id){
        return bookRepository.findBookById(id);
    }
}

