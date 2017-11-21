package com.capstone.demo.controllers;

import com.capstone.demo.models.Task;
import com.capstone.demo.repositories.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class TaskController {
    @Autowired
    Tasks taskDao;

    //////////
    //method uses tasks repo to find tasks and pass to view
    //////////
    @GetMapping("/child-profile")
    public String showAll(@PathVariable Model model){
        Iterable<Task> tasks = taskDao.findAll();
        model.addAttribute("task", tasks);

        return "users/child-profile";
    }

}
