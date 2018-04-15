package com.store.BookStore.data.domain;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Data
@Entity
public class Comment {
    private @Id @GeneratedValue Long id;
    private int rating;
    private String message;

    @ManyToOne(cascade = { CascadeType.ALL })
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    public Comment() { }

    public Comment(int rating, String message, User user) {
        this.rating = rating;
        this.message = message;
        this.user = user;
    }
}
