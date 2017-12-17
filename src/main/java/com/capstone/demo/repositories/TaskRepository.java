package com.capstone.demo.repositories;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////
import com.capstone.demo.models.Task;
import com.capstone.demo.models.TaskStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/////////////////////////////////////////////////////////////////
// Repository is an interface used create simple queries used to
// create, reade, update, delete data in my database.
/////////////////////////////////////////////////////////////////
@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByGoalId(Long goalId);

}
