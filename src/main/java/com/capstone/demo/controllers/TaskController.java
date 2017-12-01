package com.capstone.demo.controllers;

import com.capstone.demo.models.*;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.ParentRepository;
import com.capstone.demo.repositories.TaskRepository;
import com.capstone.demo.repositories.UserRepository;
import com.capstone.demo.services.GoalsService;
import com.capstone.demo.services.ParentsService;
import com.capstone.demo.services.TasksService;
import com.capstone.demo.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final ParentsService parentsService;
    private final GoalsService goalsService;
    private final UserService userService;
    private final UserRepository userDao;
    private final ParentRepository parentDao;
    private final ChildRepository childDao;

    @Autowired
    public TaskController(TasksService service, TaskRepository taskDao, ParentsService parentsService, GoalsService goalsService, UserService userService, UserRepository userDao, ParentRepository parentDao, ChildRepository childDao) {
        this.service = service;
        this.taskDao = taskDao;
        this.parentsService = parentsService;
        this.goalsService = goalsService;
        this.userService = userService;
        this.userDao=userDao;
        this.parentDao=parentDao;
        this.childDao = childDao;
    }

    ///////////////////////////////////////////////////////////////////////
    // See all tasks created by this user
    ///////////////////////////////////////////////////////////////////////
    @GetMapping("/tasks")
    public String showAll(Model viewModel) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Parent parent = parentDao.findByUser(user);
        viewModel.addAttribute("parent", parent);
        viewModel.addAttribute("child", childDao.findByUser(user));

        String role = user.getRole();
        System.out.println(role);
        viewModel.addAttribute("role", role);

        viewModel.addAttribute("tasks", service.findAll());
        viewModel.addAttribute("loggedUser", userService.loggedInUser());

        return "users/tasks";
    }

    //////////////////////////////////////////////////////////////////////
    // Create new task
    //////////////////////////////////////////////////////////////////////
    @GetMapping("/tasks/create")
    public String showCreateTaskForm(Model viewModel, @RequestParam(value = "id", required = false) Long goalId) throws JsonProcessingException {
        Iterable<Task> tasks = taskDao.findAll();
        ObjectMapper mapper = new ObjectMapper();
        viewModel.addAttribute("id", goalId);
        viewModel.addAttribute("tasks", mapper.writeValueAsString(tasks));
        viewModel.addAttribute("task", new Task());
        return "tasks/create";
    }

    @PostMapping("/tasks/create")
    public String createNewTask(@ModelAttribute Task task,
                                @RequestParam(name = "startDateMoment") String startDate,
                                @RequestParam(name = "goalId") long goalId,
                                Errors validation,
                                Model model,
                                RedirectAttributes redirect
    ) throws ParseException {

        if (validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("task", task);
            return "tasks/create";
        }

        if (!parentsService.isLoggedIn()) {
            redirect.addFlashAttribute("test", true);
            return "redirect:/register";
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        System.out.println(startDate);
        Date start = format.parse(startDate);
        task.setStartDate(start);
        System.out.println(start.toString());
        System.out.println(goalsService.findById(goalId).getGoalName());
        task.setGoal(goalsService.findById(goalId));
        task.setStatus(TaskStatus.NEW);
        service.save(task);
        return "redirect:/tasks/create?id=" + goalId;
    }

    //////////////////////////////////////////////////////////////////////
    // View all tasks using Json
    ////////////////////////////////////////////////////////////////
    @GetMapping("/tasks.json")
    public @ResponseBody
    Iterable<Task> viewAllTasksInJsonFormat(@RequestParam(name = "goalId") Long goalId) {
        return taskDao.findByGoalId(goalId);
    }


    //////////////////////////////////////////////////////////////////////
    // View all tasks through Ajax call
    //////////////////////////////////////////////////////////////////////
    @GetMapping("/tasks/tasks")
    public String viewAllTasksWithAjax() {
        return "tasks/tasks";
    }


    ////querying approved tasks from repo using enum
    @PostMapping("/tasks")
    public String getStatus(Model model) {
        List<Task> completedTasks = taskDao.findByStatus(TaskStatus.REQUEST_APPROVAL);
        model.addAttribute("pendingTasks", completedTasks);

        return "users/tasks";
    }

    @GetMapping("/tasks/approve/{id}")
    @ResponseBody
    public String approveTask(@PathVariable Long id) {
        Task task = taskDao.findOne(id);
        task.setStatus(TaskStatus.APPROVED);
        task.updateGoalProgress();

        if (task.completesGoal()) {
            Child child = childDao.findByUserId(task.childUserId());
            child.increasScore(task.getGoal());
            childDao.save(child);
        }

        taskDao.save(task);
        return "";
    }

    @GetMapping("/tasks/completed/{id}")
    @ResponseBody
    public String completeTask(@PathVariable Long id) {
        Task task = taskDao.findOne(id);
        task.setStatus(TaskStatus.REQUEST_APPROVAL);
        taskDao.save(task);
        return "";
    }

    //////////////////////////////////////////////////////////////////////
    // Delete event.
    //////////////////////////////////////////////////////////////////////
    @GetMapping("/tasks/delete")
    public String showTaskToBeDeleted(@PathVariable Long id, Model viewModel) {
        Task task = service.findById(id);
        viewModel.addAttribute("task", task);
        return "tasks/edit";
    }

    @PostMapping("/tasks/delete")
    public String deleteTask (@RequestParam(value = "taskId") Long id, @RequestParam(name = "goalId") long goalId) {
        System.out.println("////////////////////");
        System.out.println(id);
        System.out.println("////////////////////");
        service.delete(id);
        return "redirect:/tasks/create?id=" + goalId;
    }
}



















