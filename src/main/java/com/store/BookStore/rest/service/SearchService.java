package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.Book;

import java.util.List;

public interface SearchService {
    List<Book> findBooksByParams(String title, String author, String subject);
}
