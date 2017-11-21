package com.capstone.demo.controllers;

import com.capstone.demo.models.Goal;
import com.capstone.demo.models.Parent;
import com.capstone.demo.repositories.GoalRepository;
import com.capstone.demo.services.GoalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class GoalController {

    private final GoalsService service;
    private final GoalRepository goalDao;

    @Autowired
    public GoalController(GoalsService service, GoalRepository goalDao) {
        this.service = service;
        this.goalDao = goalDao;
    }

    @GetMapping("/goals")

    public String showGoals(Model viewModel) {
        List<Goal> goals = (List<Goal>) service.findAll();
        viewModel.addAttribute("goals", service.findAll());
        return "goals/index";
    }


    ////////////////Not being implemented yet...////////////////////
    @GetMapping("/goals/{id}")
    public String showGoals (@PathVariable long id, Model viewmodel){
        Goal goal = service.findById(id);
        viewmodel.addAttribute("goal", service.findById(id));


        return "goals/show";
    }

    @GetMapping("/goals/create")
    public String showCreateForm(Model viewModel) {
        viewModel.addAttribute("goal", new Goal());
        return "goals/create";
    }

    @PostMapping("/goals/create")
    public String createGoal(@ModelAttribute Goal goal) {
        service.save(goal);
        return "redirect:/goals";
    }
}
