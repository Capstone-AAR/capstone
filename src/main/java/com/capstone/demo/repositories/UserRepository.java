package com.capstone.demo.repositories;

import com.capstone.demo.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findById(Long id);

    @Query("select u from Child c join c.user u where c.id = ?1")
    User findByChildId(Long id);

    User findByEmail(String email);
}
