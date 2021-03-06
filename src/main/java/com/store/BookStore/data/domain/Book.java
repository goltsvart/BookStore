package com.store.BookStore.data.domain;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Book {
    private @Id @GeneratedValue Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private String image;
    private int quantity;
    private String subject;

    @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval=true)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Comment> comments;

    public Book(){}

    public Book(String title, String author, BigDecimal price, int quantity, String image, String subject){
        this.title = title;
        this.author = author;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.subject = subject;
    }
    public Book(String title, String author, BigDecimal price, String image, String subject, int quantity, List<Comment> comments){
        this.title = title;
        this.author = author;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.comments = comments;
        this.subject = subject;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return id.equals(book.id);
    }
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
