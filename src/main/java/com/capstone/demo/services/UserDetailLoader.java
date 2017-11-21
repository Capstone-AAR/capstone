package com.capstone.demo.services;

import com.capstone.demo.models.Parent;
import com.capstone.demo.models.UserWithRoles;
import com.capstone.demo.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.capstone.demo.repositories.ParentRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDetailLoader implements UserDetailsService {

    private final ParentRepository repository;

    @Autowired
    public UserDetailLoader(ParentRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Parent parent = repository.findByUsername(username);

        if (parent == null) {
            throw new UsernameNotFoundException(String.format("The parent with username %s cannot be found", username));
        }
        return new UserWithRoles(parent);
    }
}

