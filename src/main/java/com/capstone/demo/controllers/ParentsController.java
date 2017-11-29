package com.capstone.demo.controllers;

import com.capstone.demo.models.Child;
import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.ParentRepository;
import com.capstone.demo.repositories.UserRepository;
import com.capstone.demo.services.ChildService;
import com.capstone.demo.services.ParentsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ParentsController {
    private UserRepository repository;
    private PasswordEncoder encoder;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;

    public ParentsController(
            PasswordEncoder encoder,
            UserRepository repository,
            ParentRepository parentRepository,
            ChildRepository childRepository
    ) {
        this.repository = repository;
        this.encoder = encoder;
        this.parentRepository = parentRepository;
        this.childRepository = childRepository;
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
        viewModel.addAttribute("parent", new User());
        return "users/register";
    }

    @PostMapping("/users/register")
    public String registerForm(@ModelAttribute User user, @RequestParam(value = "isParent", defaultValue = "false") Boolean isParent) {
        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);
        repository.save(user);
        if (isParent) {
            Parent parent = new Parent();
            parent.setUser(user);
            parentRepository.save(parent);
        } else {
            Child child = new Child();
            child.setUser(user);
            childRepository.save(child);
        }
        return "redirect:/login";
    }

//    @GetMapping("/parent-profile")
//    public String viewProfile(Model viewModel) {
//        List<Parent> parents =(List<Parent>)service.findAll();
//        return "users/parent-profile";
//    }
}
