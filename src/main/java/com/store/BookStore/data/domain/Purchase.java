package com.store.BookStore.data.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Purchase {
    private @Id @GeneratedValue Long id;
    @OneToOne(cascade= CascadeType.ALL)
    private Book book;
    private int quantity;
    public Purchase() { }
    public Purchase(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }
}
