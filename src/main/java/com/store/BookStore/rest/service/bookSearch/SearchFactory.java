package com.store.BookStore.rest.service.bookSearch;

import com.store.BookStore.data.domain.Book;
import com.store.BookStore.data.repo.BookRepository;
import com.store.BookStore.rest.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchFactory {
    private final BookRepository bookRepository;
    @Autowired
    public SearchFactory(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findBooksByParams(String title, String author, String subject) {
        SearchService search = null;
        if (!StringUtils.isBlank(title) && StringUtils.isBlank(author) && StringUtils.isBlank(subject)) {
            search = new Title(bookRepository);
        } else if (StringUtils.isBlank(title) && !StringUtils.isBlank(author) && StringUtils.isBlank(subject)) {
            search = new Author(bookRepository);
        } else if (StringUtils.isBlank(title) && StringUtils.isBlank(author) && !StringUtils.isBlank(subject)) {
            search = new Subject(bookRepository);
        } else if (!StringUtils.isBlank(title) && !StringUtils.isBlank(author) && StringUtils.isBlank(subject)) {
            search = new TitleOrAuthor(bookRepository);
        }else if (!StringUtils.isBlank(title) && StringUtils.isBlank(author) && !StringUtils.isBlank(subject)) {
            search = new TitleAndSubject(bookRepository);
        }else if (StringUtils.isBlank(title) && !StringUtils.isBlank(author) && !StringUtils.isBlank(subject)) {
            search = new AuthorAndSubject(bookRepository);
        }else if (!StringUtils.isBlank(title) && !StringUtils.isBlank(author) && !StringUtils.isBlank(subject)) {
            search = new TitleOrAuthorAndSubject(bookRepository);
        }else if (StringUtils.isBlank(title) && StringUtils.isBlank(author) && StringUtils.isBlank(subject)){
            return null;
        }
        return search.findBooksByParams(title, author, subject);
    }
}
