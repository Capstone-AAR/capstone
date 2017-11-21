package com.capstone.demo.repositories;

import com.capstone.demo.models.Child;
import org.springframework.data.repository.CrudRepository;

public interface ChildRepository extends CrudRepository<Child, Long> {
    public Child findByUsername(String child);
}
