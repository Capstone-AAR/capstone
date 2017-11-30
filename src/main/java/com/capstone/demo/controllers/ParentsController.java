package com.capstone.demo.controllers;

import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.ParentRepository;
import com.capstone.demo.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String registerForm(@ModelAttribute User user) {
        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);
        user.isAParent();
        repository.save(user);

        Parent parent = new Parent();
        parent.setUser(user);
        parentRepository.save(parent);

        return "redirect:/login";
    }

//    @GetMapping("/parent-profile")
//    public String viewProfile(Model viewModel) {
//        List<Parent> parents =(List<Parent>)service.findAll();
//        return "users/parent-profile";
//    }
}
