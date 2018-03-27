package com.store.BookStore.util;

import com.store.BookStore.data.domain.Book;

public class NotEnoughProductsInStockException extends Exception {

    private static final String DEFAULT_MESSAGE = "Not enough products in stock";

    public NotEnoughProductsInStockException() {
        super(DEFAULT_MESSAGE);
    }

    public NotEnoughProductsInStockException(Book book) {
        super(String.format("Not enough %s id books in stock. Only %d left", book.getId(), book.getQuantity()));
    }
}
