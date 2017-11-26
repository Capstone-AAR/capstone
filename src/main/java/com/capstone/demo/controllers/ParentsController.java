package com.capstone.demo.controllers;

import com.capstone.demo.models.Parent;
import com.capstone.demo.repositories.ParentRepository;
import com.capstone.demo.services.ParentsService;
import com.capstone.demo.services.UserDetailLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ParentsController {
    private ParentRepository repository;
    PasswordEncoder encoder;
    private ParentsService service;

    public ParentsController(PasswordEncoder encoder, ParentRepository repository) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Autowired
    public ParentsController(PasswordEncoder encoder, ParentRepository repository, ParentsService service){
        this.repository = repository;
        this.encoder = encoder;
        this.service = service;
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
    public String viewProfile(Model viewModel) {
        List<Parent> parents =(List<Parent>)service.findAll();
        return "users/parent-profile";
    }
}
