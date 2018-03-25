package com.store.BookStore.data.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Role {
    private @Id @GeneratedValue Long id;
    private String role;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<User> users;
    public Role() { }
    public Role(String role){
        this.role = role;
    }
}
