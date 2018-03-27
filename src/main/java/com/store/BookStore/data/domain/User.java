package com.store.BookStore.data.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class User {
    private @Id
    @GeneratedValue Long id;

    private String name;
    private String surname;
    private String password;
    private String email;
    private String username;
    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Role> roles;
    @OneToMany(cascade=CascadeType.ALL)
    private List<PurchaseHistory> purchaseHistoryList;
    public User(){}

    public User(String name, String surname, String password, String email, String username, Collection<Role> roles){
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.username = username;
        this.roles = roles;
    }
    public User(String name, String surname, String password, String email, String username, Collection<Role> roles, List<PurchaseHistory> purchaseHistoryList){
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.username = username;
        this.roles = roles;
        this.purchaseHistoryList = purchaseHistoryList;
    }
}
