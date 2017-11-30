package com.capstone.demo.repositories;

import com.capstone.demo.models.Child;
import com.capstone.demo.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChildRepository extends CrudRepository<Child, Long> {
    Child findByUser(User user);

    Child findByUserId(Long id);

    @Query(nativeQuery = true, value="SELECT * from children where parent_id=?1")
    List<Child> findAllBy (Long id);


}
