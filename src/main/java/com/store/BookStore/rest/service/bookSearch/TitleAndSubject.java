package com.store.BookStore.rest.service.bookSearch;

import com.store.BookStore.data.domain.Book;
import com.store.BookStore.data.repo.BookRepository;
import com.store.BookStore.rest.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TitleAndSubject implements SearchService {
    private final BookRepository bookRepository;
    @Autowired
    public TitleAndSubject(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> findBooksByParams(String title, String author, String subject){
        return bookRepository.findBooksByTitleAndSubject(title, subject);
    }
}
