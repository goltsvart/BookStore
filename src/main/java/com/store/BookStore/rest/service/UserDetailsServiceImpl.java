package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.Role;
import com.store.BookStore.data.domain.User;
import com.store.BookStore.data.repo.RoleRepository;
import com.store.BookStore.data.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component(value = "userDetailService")
public class UserDetailsServiceImpl implements UserDetailsService, UserService{
    private List<GrantedAuthority> authorities;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findUserByUsername(username);
        if (username.equals("admin")) {
            if(u == null){
                User user = new User("admin", "admin", "admin", "admin", "admin", "admin", "admin", Collections.singletonList(roleRepository.findByRole(ROLE_ADMIN)));
                user.setRoles(Collections.singletonList(roleRepository.findByRole(ROLE_ADMIN)));
                userRepository.saveAndFlush(user);
                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
            }else if(u != null){
                return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
            }
        }
        if(u == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public Collection<? extends GrantedAuthority> getAuthority() {
        return authorities;
    }

    public List getUsers() {
        return userRepository.findAll();
    }

    public List getCustomers() {
        List<User> users = userRepository.findAll();
        List<Role> roles = roleRepository.findAll();
        List<User> customers = new ArrayList<>();
        for(User user: users) {
            if (!user.getUsername().equals("admin")) {
                customers.add(user);
            }
        }
        return customers;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(user.getPassword());
        user.setRoles(Collections.singletonList(roleRepository.findByRole(ROLE_USER)));
        return userRepository.saveAndFlush(user);
    }
}