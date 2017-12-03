package com.capstone.demo.repositories;

import com.capstone.demo.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findById(Long id);
    User findByRole(String role);


    @Query("select u from Child c join c.user u where c.id = ?1")
    User findByChildId(Long id);

    User findByEmail(String email);

    @Query(nativeQuery = true, value="SELECT * FROM users WHERE role = \"parent\";")
    User findIfParent(String role);

    @Query("select u from Goal g join g.user u where g.id =?1")
    User findByGoalId(Long id);
}
