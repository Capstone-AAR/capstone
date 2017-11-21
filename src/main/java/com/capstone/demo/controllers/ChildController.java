package com.capstone.demo.controllers;

import com.capstone.demo.repositories.ChildRepository;
import org.springframework.stereotype.Controller;

@Controller
public class ChildController {
    private ChildRepository childDao;

    public ChildController(ChildRepository childDao) {
        this.childDao = childDao;
    }



}
