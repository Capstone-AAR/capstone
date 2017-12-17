package com.capstone.demo.repositories;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////
import com.capstone.demo.models.Child;
import com.capstone.demo.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/////////////////////////////////////////////////////////////////
// Repository is an interface used create simple queries used to
// create, reade, update, delete data in my database.
/////////////////////////////////////////////////////////////////
public interface ChildRepository extends CrudRepository<Child, Long> {

    Child findByUser(User user);

    Child findByUserId(Long id);

    @Query(nativeQuery = true, value = "SELECT * from children where parent_id=?1")
    List<Child> findAllByParentId(Long id);


}
