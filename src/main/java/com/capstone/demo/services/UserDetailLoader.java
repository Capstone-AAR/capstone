package com.capstone.demo.services;

import com.capstone.demo.models.User;
import com.capstone.demo.models.UserWithRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.capstone.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDetailLoader implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserDetailLoader(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("The user with username %s cannot be found", username));
        }
        return new UserWithRoles(user);
    }
}

