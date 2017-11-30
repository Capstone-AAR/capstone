package com.capstone.demo.controllers;

import com.capstone.demo.models.Goal;
import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.GoalRepository;
import com.capstone.demo.repositories.ParentRepository;
import com.capstone.demo.repositories.UserRepository;
import com.capstone.demo.services.GoalsService;
import com.capstone.demo.services.TasksService;
import com.capstone.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class GoalController {

    private final GoalsService service;
    private final GoalRepository goalDao;
    private ParentRepository parentDao;
    private UserRepository userDao;
    private final TasksService Tservice;
    private final UserService userService;
    private ChildRepository childDao;

    @Autowired
    public GoalController(
        GoalsService service,
        GoalRepository goalDao,
        ParentRepository parentDao,
        UserRepository userDao,
        TasksService Tservice,
        UserService userService,
        ChildRepository childDao
    ) {
        this.service = service;
        this.goalDao = goalDao;
        this.parentDao = parentDao;
        this.userDao = userDao;
        this.Tservice = Tservice;
        this.userService = userService;
        this.childDao = childDao;
    }

    @GetMapping("/goals")
    public String showGoals(Model viewModel) {
        viewModel.addAttribute("goals", service.findAll());
        return "goals/index";
    }


    @GetMapping("/goals/{id}")
    public String showGoals (@PathVariable long id, Model viewmodel){
        Goal goal = service.findById(id);
        viewmodel.addAttribute("goal", service.findById(id));


        return "goals/show";
    }

    @GetMapping("/goals/create")
    public String showCreateForm(Model viewModel) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Parent parent = parentDao.findByUserId(user.getId());
        viewModel.addAttribute("children", childDao.findAllByParentId(parent.getId()));
        viewModel.addAttribute("goal", new Goal());
        return "goals/create";
    }

    @PostMapping("/goals/create")
    public String createGoal(@ModelAttribute Goal goal,
                             Errors validation,
                             @RequestParam(name = "child_id") Long childId,
                             Model model,
                             RedirectAttributes redirect
    ) {

        if (validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("goal", goal);
            return "goals/create";
        }

        goal.setUser(userDao.findByChildId(childId));
        service.save(goal);
        return "redirect:/goals";
    }

    @GetMapping("/goals/{id}/delete")
    public String showGoalToBeDeleted(@PathVariable Long id, Model viewModel){
        Goal goal = service.findById(id);
        viewModel.addAttribute("goal", goal);
        return "goals/delete";
    }

    @PostMapping("/goals/{id}/delete")
    public String deleteGoal (@ModelAttribute Goal goal, @PathVariable Long id) {
        service.delete(id);
        return "redirect:/goals";
    }

    @GetMapping("/goals/{id}/update")
    public String showGoalToBeEdited (@PathVariable Long id,  Model viewModel){
        Goal existingGoal = service.findById(id);
        viewModel.addAttribute("goal", existingGoal);
        return "goals/update";
    }

    @PostMapping("/goals/{id}/update")
    public String editGoal(@ModelAttribute Goal goal, @PathVariable Long id){
        goal.setId(id);
        service.save(goal);
        return "redirect:/goals/" + goal.getId();
    }

    @GetMapping("/goals/{id}/reached")
    public String showReachedGoals (@PathVariable long id, Model viewmodel){
        Goal goal = service.findById(id);
        viewmodel.addAttribute("goal", service.findById(id));


        return "goals/reached";
    }

}
