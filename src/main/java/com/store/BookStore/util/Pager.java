package com.store.BookStore.util;

import com.store.BookStore.data.domain.Book;
import org.springframework.data.domain.Page;

public class Pager {
    private final Page<Book> books;

    public Pager(Page<Book> books) {
        this.books = books;
    }

    public int getPageIndex() {
        return books.getNumber() + 1;
    }

    public int getPageSize() {
        return books.getSize();
    }

    public boolean hasNext() {
        return books.hasNext();
    }

    public boolean hasPrevious() {
        return books.hasPrevious();
    }

    public int getTotalPages() {
        return books.getTotalPages();
    }

    public long getTotalElements() {
        return books.getTotalElements();
    }

    public boolean indexOutOfBounds() {
        return this.getPageIndex() < 0 || this.getPageIndex() > this.getTotalElements();
    }
}
