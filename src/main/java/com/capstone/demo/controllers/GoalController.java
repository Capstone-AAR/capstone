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
        viewModel.addAttribute("goal", new Goal());
        return "goals/create";
    }

    @PostMapping("/goals/create")
    public String createGoal(@ModelAttribute Goal goal) {
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
