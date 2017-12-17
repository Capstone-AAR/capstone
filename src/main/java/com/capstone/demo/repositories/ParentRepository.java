package com.capstone.demo.repositories;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////
import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import org.springframework.data.repository.CrudRepository;

/////////////////////////////////////////////////////////////////
// Repository is an interface used create simple queries used to
// create, reade, update, delete data in my database.
/////////////////////////////////////////////////////////////////
public interface ParentRepository extends CrudRepository<Parent, Long>{

    Parent findByUser(User user);

    Parent findByUserId(Long id);


}
