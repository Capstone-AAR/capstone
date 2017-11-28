package com.capstone.demo.controllers;

import com.capstone.demo.models.Task;
import com.capstone.demo.models.TaskStatus;
import com.capstone.demo.repositories.Tasks;
import com.capstone.demo.services.TaskSvc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class TaskController {
//    initializer when not using dummy data
//    @Autowired
//    Tasks taskDao;

    /////using service for dummy data
    private final TaskSvc taskSvc;
    private Tasks tasks;

    public TaskController(TaskSvc taskSvc, Tasks tasks) {
        this.taskSvc = taskSvc;
        this.tasks = tasks;
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



////Trying to work with Java class Enum to show task status

//    @GetMapping("/tasks")
//    public String showAll(Model model){
//        Iterable<Task> completedTasks = tasks.findByStatus(TaskStatus.APPROVED); //querying for task status that are approved
//
//        Task task = new Task();
//        task.getStatus().equals(TaskStatus.APPROVED);
//        model.addAttribute("tasks", completedTasks);
//
//        return "users/tasks";
//    }


}
