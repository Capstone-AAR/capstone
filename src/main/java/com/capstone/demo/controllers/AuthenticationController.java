package com.capstone.demo.controllers;

import com.capstone.demo.models.Child;
import com.capstone.demo.models.Goal;
import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.GoalRepository;
import com.capstone.demo.repositories.ParentRepository;
import com.capstone.demo.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthenticationController {

    private ParentRepository parentDao;
    private ChildRepository childDao;
    private GoalRepository goalDao;
    private UserRepository userDao;

    public AuthenticationController(ParentRepository parentDao, ChildRepository childDao, GoalRepository goalDao, UserRepository userDao) {
        this.parentDao = parentDao;
        this.childDao = childDao;
        this.goalDao=goalDao;
        this.userDao = userDao;

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
            return "users/profile/child-profile";
        }
        //This is where parent stuff starts.

        List<Child> children = childDao.findAllByParentId(parent.getId());
        viewModel.addAttribute("parent",parent);
        viewModel.addAttribute("goals", goals);
        viewModel.addAttribute("children",children);
        return "users/profile/parent-profile";

        //List<Goal> goals = goalDao.findByUserId(child.getId());
        //Child child = childDao.findByUserId(user.getId());
        //viewModel.addAttribute("goals",goalDao.findByUserId(child.getId()));
        //viewModel.addAttribute("childGoal",childGoal);
    }

    @GetMapping("/view-child-profile/{id}")
    public String viewProfile(Model viewModel, @PathVariable Long id){
        User parentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Parent parent = parentDao.findByUser(parentUser);
        User user = userDao.findById(id);
        Child child = childDao.findByUser(user);

        List<Goal> incompleteGoals = goalDao.findByUserIfGoalIsIncomplete(user.getId());
        //List<User> users = userDao.findAllByParentId(user.getId());
        viewModel.addAttribute("goals", incompleteGoals);
        viewModel.addAttribute("child", child);
        viewModel.addAttribute("child",child);
        viewModel.addAttribute("user",user);

        System.out.println("hi");

        //System.out.println(child.getChildren());
        return "users/profile/view-child-profile";


    }
}