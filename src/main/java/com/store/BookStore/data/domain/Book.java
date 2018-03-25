package com.store.BookStore.data.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Book {
    private @Id @GeneratedValue Long id;
    private String title;
    private String author;
    private double price;
    private String category;
    private String image;
    private int quantity;
    @OneToMany
    private List<Comment> comments;

    public Book(){}

    public Book(String title, String author, double price, String category, int quantity, String image){
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
        this.image = image;
        this.quantity = quantity;
    }
    public Book(String title, String author, double price, String category, String image, int quantity, List<Comment> comments){
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
        this.image = image;
        this.quantity = quantity;
        this.comments = comments;
    }
}
