package com.store.BookStore.data.domain;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
    private String address;
    private String paymentMethod;
    @ManyToMany(cascade = CascadeType.MERGE)
    private Collection<Role> roles;
    @OneToMany(cascade=CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<PurchaseHistory> purchaseHistoryList;
    public User(){}

    public User(String name, String surname, String password, String email, String username, String address, String paymentMethod, Collection<Role> roles){
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.username = username;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.roles = roles;
    }
    public User(String name, String surname, String password, String email, String username, String address, String paymentMethod, Collection<Role> roles, List<PurchaseHistory> purchaseHistoryList){
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.username = username;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.roles = roles;
        this.purchaseHistoryList = purchaseHistoryList;
    }
}
