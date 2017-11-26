package com.capstone.demo.controllers;

import com.capstone.demo.models.Task;
import com.capstone.demo.repositories.Tasks;
import com.capstone.demo.services.TaskSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class TaskController {
//    initializer when not using dummy data
//    @Autowired
//    Tasks taskDao;


    /////using service for dummy data
    private final TaskSvc taskSvc;
    public TaskController(TaskSvc taskSvc) {
        this.taskSvc = taskSvc;
    }

    //////////
    //method uses tasks repo to find tasks and pass to view
    //////////
    @GetMapping("/tasks")
    public String showAll(Model model){
        Iterable<Task> tasks = taskSvc.findAll();
        model.addAttribute("tasks", tasks);

        return "users/tasks";
    }

}
