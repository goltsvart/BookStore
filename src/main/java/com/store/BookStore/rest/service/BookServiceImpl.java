package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.Book;
import com.store.BookStore.data.repo.BookRepository;
import com.store.BookStore.rest.service.bookSearch.Author;
import com.store.BookStore.rest.service.bookSearch.SearchFactory;
import com.store.BookStore.rest.service.bookSearch.Title;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService, SearchService{
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

    public List<Book> findBooksByParams(String title, String author, String subject){
        SearchFactory searchFactory = new SearchFactory(bookRepository);
        return searchFactory.findBooksByParams(title, author, subject);
    }
    public List<Book> sortBookList(List<Book> list, String selectedSort, String selectedOrder){
        switch (selectedSort) {
            case "title":  sortListByTitle(list, selectedOrder);
            case "author":  sortListByAuthor(list, selectedOrder);
            case "price":  sortListByPrice(list, selectedOrder);
            default: break;
        }
        return list;
    }
    public List<Book> findAll(){
       return bookRepository.findAll();
    }

    public void sortListByTitle(List<Book> list, String selectedOrder){
        if(selectedOrder.equals("ascending")){
            Collections.sort(list, Comparator.comparing(Book::getTitle));
        }else{
            Collections.sort(list, (b1, b2) -> b2.getTitle().compareTo(b1.getTitle()));
        }
    }
    public void sortListByAuthor(List<Book> list, String selectedOrder){
        if(selectedOrder.equals("ascending")){
            Collections.sort(list, Comparator.comparing(Book::getAuthor));
        }else{
            Collections.sort(list, (b1, b2) -> b2.getAuthor().compareTo(b1.getAuthor()));
        }
    }
    public void sortListByPrice(List<Book> list, String selectedOrder){
        if(selectedOrder.equals("ascending")){
            Collections.sort(list, Comparator.comparing(Book::getPrice));
        }else{
            Collections.sort(list, (b1, b2) -> b2.getPrice().compareTo(b1.getPrice()));
        }
    }
}

