package com.store.BookStore.data.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Comment {
    private @Id @GeneratedValue Long id;
    private int rating;
    private String comment;

    @ManyToOne
    private User user;

    public Comment(int rating, String comment, User user) {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
    }
}
