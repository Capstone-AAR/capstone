package com.capstone.demo.controllers;

import com.capstone.demo.models.Child;
import com.capstone.demo.models.Goal;
import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.GoalRepository;
import com.capstone.demo.repositories.ParentRepository;
import com.capstone.demo.repositories.UserRepository;
import com.capstone.demo.services.GoalsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AuthenticationController {

    private ParentRepository parentDao;
    private ChildRepository childDao;
    private GoalRepository goalDao;
    private UserRepository userDao;
    private GoalsService goalsService;

    public AuthenticationController(ParentRepository parentDao,
                                    ChildRepository childDao,
                                    GoalRepository goalDao,
                                    UserRepository userDao) {
        this.parentDao = parentDao;
        this.childDao = childDao;
        this.goalDao = goalDao;
        this.userDao = userDao;
        this.goalsService = goalsService;

    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }

    @GetMapping("/profile")
    public String dashboard(Model viewModel) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Parent parent = parentDao.findByUser(user);
        //Erased... but used to work for parents' goals
        List<Goal> goals = goalDao.findByUserId(user.getId());
        //Child child = childDao.findByUserId(parent.getId());
        //List<Goal> childGoal= goalDao.findByUserId(child.getId());
        System.out.println(goals);

        if (parent == null) {
            viewModel.addAttribute("child", childDao.findByUser(user));
            viewModel.addAttribute("goals", childDao.findByUser(user).getUser().getGoals());
            return "users/profile/child-profile";
        }
        //This is where parent stuff starts.

        //List<Goal> goals = goalDao.findByUserId(child.getId());
        List<Child> children = childDao.findAllByParentId(parent.getId());
        //Child child = childDao.findByUserId(user.getId());


        viewModel.addAttribute("parent", parent);
        viewModel.addAttribute("goals", goals);
        //viewModel.addAttribute("goals",goalDao.findByUserId(child.getId()));
        viewModel.addAttribute("children", children);
        //viewModel.addAttribute("childGoal",childGoal);
        return "users/profile/parent-profile";
    }

    @GetMapping("/view-child-profile/{id}")
    public String viewProfile(Model viewModel, @PathVariable Long id) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Parent parent = parentDao.findByUserId(id);
        Child child = childDao.findByUserId(id);
        List<Child> children = childDao.findAllByParentId(child.getId());
        viewModel.addAttribute("child", child);
        viewModel.addAttribute("children", children);
        viewModel.addAttribute("goals", goalDao.findByUserId(child.getId()));
        System.out.println("hi");
        System.out.println(child.getId());
        ;
        //System.out.println(child.getChildren());
        return "users/profile/view-child-profile";

    }
}
