package com.capstone.demo.controllers;

import com.capstone.demo.models.Task;
import com.capstone.demo.models.Parent;
import com.capstone.demo.repositories.TaskRepository;
import com.capstone.demo.services.TasksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Controller
public class TaskController {

    private final TasksService service;
    private final TaskRepository taskDao;

    @Autowired
    public TaskController(TasksService service, TaskRepository taskDao) {
        this.service = service;
        this.taskDao = taskDao;
    }

    ///////////////////////////////////////////////////////////////////////
    // See all tasks created by this user
    ///////////////////////////////////////////////////////////////////////
    @GetMapping("/tasks")
    public String showAll(Model viewModel){
        viewModel.addAttribute("tasks", service.findAll());
        return "users/tasks";
    }

    //////////////////////////////////////////////////////////////////////
    // Create new task
    //////////////////////////////////////////////////////////////////////
    @GetMapping("/tasks/create")
    public String showCreateTaskForm(Model viewModel) throws JsonProcessingException {
        Iterable<Task> tasks = taskDao.findAll();
        ObjectMapper mapper = new ObjectMapper();
        viewModel.addAttribute("tasks", mapper.writeValueAsString(tasks));
        viewModel.addAttribute("task", new Task());
        return "tasks/create";
    }

    @PostMapping("/tasks/create")
    public String createNewTask(@ModelAttribute Task task, @RequestParam(name = "startDateMoment") String startDate) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        System.out.println(startDate);
        Date start = format.parse(startDate);
        task.setStartDate(start);
        System.out.println(start.toString());
        service.save(task);
        return "redirect:/tasks/create";
    }

    //////////////////////////////////////////////////////////////////////
    // View all tasks using Json
    ////////////////////////////////////////////////////////////////
    @GetMapping("/tasks.json")
    public @ResponseBody
    Iterable<Task> viewAllTasksInJsonFormat() { return taskDao.findAll(); }


    //////////////////////////////////////////////////////////////////////
    // View all tasks through Ajax call
    //////////////////////////////////////////////////////////////////////
    @GetMapping("/tasks/tasks")
    public String viewAllTasksWithAjax() { return "tasks/tasks"; }

























}
