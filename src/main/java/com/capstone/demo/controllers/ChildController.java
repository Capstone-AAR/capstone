package com.capstone.demo.controllers;

import com.capstone.demo.models.Child;
import com.capstone.demo.repositories.ChildRepository;

import com.capstone.demo.services.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChildController {
    private ChildRepository childDao;
    private ChildService service;


    @Autowired
    public ChildController(ChildRepository childDao, ChildService service) {
        this.childDao=childDao;
        this.service = service;
    }

    @GetMapping("/child-profile")
    public String childHomePage() {
        return "users/child-profile";
    }

    @GetMapping("/register-child")
    public String registerForm(Model viewModel){
        viewModel.addAttribute("child", new Child());
        return "users/register-child";
    }

    @PostMapping("/users/register-child")
    public String registerChild(@ModelAttribute Child child){
        childDao.save(child);
        return "redirect:/child-login";
    }




}
