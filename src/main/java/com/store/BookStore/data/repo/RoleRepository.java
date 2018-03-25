package com.store.BookStore.data.repo;

import com.store.BookStore.data.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
    Role findByRole(String role);
}

