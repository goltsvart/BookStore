package com.store.BookStore.rest.service.bookSearch;

import com.store.BookStore.data.domain.Book;
import com.store.BookStore.data.repo.BookRepository;
import com.store.BookStore.rest.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class Author implements SearchService {
    private final BookRepository bookRepository;
    @Autowired
    public Author(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findBooksByParams(String title, String author, String subject) {
        return bookRepository.findBooksByAuthorIgnoreCase(author);
    }
}
