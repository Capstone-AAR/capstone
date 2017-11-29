package com.capstone.demo.repositories;

import com.capstone.demo.models.Child;
import com.capstone.demo.models.User;
import org.springframework.data.repository.CrudRepository;

public interface ChildRepository extends CrudRepository<Child, Long> {
    Child findByUser(User user);
}
