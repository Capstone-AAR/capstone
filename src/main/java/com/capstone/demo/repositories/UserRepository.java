package com.capstone.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import com.capstone.demo.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String name);
}
