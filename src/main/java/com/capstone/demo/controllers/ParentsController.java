package com.capstone.demo.controllers;

import com.capstone.demo.models.Parent;
import com.capstone.demo.repositories.ParentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ParentsController {
    private ParentRepository repository;
    PasswordEncoder encoder;

    public ParentsController(PasswordEncoder encoder, ParentRepository repository) {
        this.repository = repository;
        this.encoder = encoder;
    }

    //////////////////////////////////////////////////////////
    // Home page.
    //////////////////////////////////////////////////////////
    @GetMapping("/")
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
        String hash = encoder.encode(parent.getPassword());
        parent.setPassword(hash);
        repository.save(parent);
        return "redirect:/login";
    }

    @GetMapping("/parent-profile")
    public String viewProfile() {
        return "users/parent-profile";
    }
}
