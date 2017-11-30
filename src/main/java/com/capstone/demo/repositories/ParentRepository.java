package com.capstone.demo.repositories;

import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import org.springframework.data.repository.CrudRepository;

public interface ParentRepository extends CrudRepository<Parent, Long>{
    Parent findByUser(User user);

    Parent findByUserId(Long id);


}
