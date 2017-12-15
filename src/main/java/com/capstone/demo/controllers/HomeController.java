package com.capstone.demo.controllers;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

///////////////////////////////////////////////////////////////
// Take user to mock landing page. Page contains simple text.
///////////////////////////////////////////////////////////////
public class HomeController {
    @GetMapping("/")
    @ResponseBody
    public String getMapping() { return "This is the landing page!"; }
}
