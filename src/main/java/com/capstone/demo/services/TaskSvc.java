package com.capstone.demo.services;

import com.capstone.demo.models.Task;
import com.capstone.demo.repositories.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskSvc {
    private List<Task> tasks;

    public TaskSvc() {
        tasks = new ArrayList<>();
        createDummyTasks();
    }

    public List<Task> findAll() {
        return tasks;
    }

    public Task saveTask(Task task) {
        task.setId(tasks.size() + 1L);
        tasks.add(task);
        return task;
    }

    public void createDummyTasks() {

        saveTask(new Task("Clean room", "clean everything"));
        saveTask(new Task("Rake leaves", "rake all the leaves"));
        saveTask(new Task("Take out trash", "trash trash trash"));


    }

}
