package com.capstone.demo.controllers;

import com.capstone.demo.models.Child;
import com.capstone.demo.repositories.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChildController {
    @Autowired
    ChildRepository childDao;

    @GetMapping("/child-profile")
    public String childHomePage() {
        return "users/child-profile";
    }



}
