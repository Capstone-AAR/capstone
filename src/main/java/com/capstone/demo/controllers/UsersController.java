package com.capstone.demo.controllers;

import com.capstone.demo.models.Parent;
import com.capstone.demo.repositories.ParentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {
    private ParentRepository repository;

    public UsersController(ParentRepository repository) {
        this.repository = repository;
    }

    //////////////////////////////////////////////////////////
    // Home page.
    //////////////////////////////////////////////////////////
    @GetMapping("/index")
    public String homePage() {
        return "users/index";
    }


    //////////////////////////////////////////////////////////
    // Registration page. (Parent)
    //////////////////////////////////////////////////////////
    @GetMapping("/register")
    public String registerPage(Model viewModel) {
        viewModel.addAttribute("parent", new Parent());
        return "users/register";
    }

    @PostMapping("/users/register")
    public String registerForm(@ModelAttribute Parent parent) {
        repository.save(parent);
        return "redirect:/users/login";
    }

    @GetMapping("/parent-profile")
    public String viewProfile() {
        return "users/parent-profile";
    }
}
