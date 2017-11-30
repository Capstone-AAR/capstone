package com.capstone.demo.repositories;

import com.capstone.demo.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findById(Long id);
}
