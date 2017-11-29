package com.capstone.demo.controllers;

import com.capstone.demo.models.Child;
import com.capstone.demo.models.Goal;
import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.ParentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AuthenticationController {

    private ParentRepository parentDao;
    private ChildRepository childDao;

    public AuthenticationController(ParentRepository parentDao, ChildRepository childDao) {
        this.parentDao = parentDao;
        this.childDao = childDao;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }

    @GetMapping("/profile")
    public String dashboard(Model viewModel) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Parent parent = parentDao.findByUser(user);

        if (parent == null) {
        viewModel.addAttribute("child", childDao.findByUser(user));
            return "users/profile/child-profile";
        }
        viewModel.addAttribute("parent",parentDao.findByUser(user));
        return "users/profile/parent-profile";
    }



//    @GetMapping("/child-login")
//    public String childLogin(){
//        return "users/child-login";
//    }
}
