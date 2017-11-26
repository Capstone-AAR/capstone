package com.capstone.demo.repositories;

import com.capstone.demo.models.Parent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ParentRepository extends CrudRepository<Parent, Long>{
    public Parent findByUsername(String Username);

}
