package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findByUsername(String username);

    User saveUser(User user);

    List getCustomers();

}