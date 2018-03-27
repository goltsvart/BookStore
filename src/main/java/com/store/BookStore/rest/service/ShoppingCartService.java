package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.Book;
import com.store.BookStore.util.NotEnoughProductsInStockException;

import java.math.BigDecimal;
import java.util.Map;

public interface ShoppingCartService {

    void addBook(Book book);

    void removeBook(Book book);

    Map<Book, Integer> getBooksInCart();

    void checkout(String username) throws NotEnoughProductsInStockException;

    BigDecimal getTotal();
}
