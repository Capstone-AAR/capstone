package com.capstone.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class HomeController {
    @GetMapping("/")
    @ResponseBody
    public String getMapping() { return "This is the landing page!"; }
}
