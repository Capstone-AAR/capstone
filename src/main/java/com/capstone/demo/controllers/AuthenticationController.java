package com.capstone.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }



    @GetMapping("/child-login")
    public String childLogin(){
        return "users/child-login";
    }
}
