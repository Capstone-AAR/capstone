package com.capstone.demo.repositories;

import com.capstone.demo.models.Task;
import com.capstone.demo.models.TaskStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Tasks extends CrudRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
}
