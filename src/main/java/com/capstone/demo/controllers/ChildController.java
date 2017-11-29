package com.capstone.demo.controllers;

import com.capstone.demo.models.Child;
import com.capstone.demo.repositories.ChildRepository;

import com.capstone.demo.services.ChildService;
import com.capstone.demo.services.ParentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ChildController {
    private ChildRepository childDao;
    private ChildService service;
    private PasswordEncoder encoder;


    @Autowired
    public ChildController(ChildRepository childDao, ChildService service, PasswordEncoder encoder) {
        this.childDao=childDao;
        this.service = service;
        this.encoder = encoder;
    }

//    @GetMapping("/child-profile")
//    public String childHomePage() {
//        return "users/child-profile";
//    }

    @GetMapping("/register-child")
    public String registerForm(Model viewModel){
        viewModel.addAttribute("child", new Child());
        return "users/register-child";
    }

//    @PostMapping("/users/register-child")
//    public String registerChild(@ModelAttribute Child child){
//        String hash = encoder.encode(child.getPassword());
//        child.setPassword(hash);
//        childDao.save(child);
//        return "redirect:/child-login";
//    }




}
