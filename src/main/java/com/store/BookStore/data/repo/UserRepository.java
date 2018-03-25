package com.store.BookStore.data.repo;

import com.store.BookStore.data.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);
    User findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
}
