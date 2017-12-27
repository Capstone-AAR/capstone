package com.capstone.demo.services;

import com.capstone.demo.models.Task;
import com.capstone.demo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////////////////////////////////
// Service class is a special class used to provide more elaborate ways
// to query and interact with data, and tables.
/////////////////////////////////////////////////////////////////////////
@Service
public class TasksService {
    private final TaskRepository taskDao;

    @Autowired
    public TasksService(TaskRepository taskDao) {
        this.taskDao = taskDao;
    }

    public Iterable<Task> findAll() {
        return taskDao.findAll();
    }

    public Task findById(Long id) {
        return taskDao.findOne(id);
    }

    public Task save(Task task) {
        return taskDao.save(task);
    }

    public void delete(long id) {
        taskDao.delete(id);
    }

}
