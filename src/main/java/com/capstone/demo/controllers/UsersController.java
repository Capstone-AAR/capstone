package com.capstone.demo.controllers;

import com.capstone.demo.models.User;
import com.capstone.demo.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {
    private UserRepository repository;

    public UsersController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/index")
    public String homePage() {
        return "users/index";
    }

    @GetMapping("/register")
    public String registerPage(Model viewModel){
        viewModel.addAttribute("user", new User());
        return"users/register";
    }

    @PostMapping("/register")
    public String registerForm(@ModelAttribute User user){
        repository.save(user);
        return "redirect:/login";
    }
}
