package com.capstone.demo.controllers;

import com.capstone.demo.models.Child;
import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.ParentRepository;
import com.capstone.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChildController {
    private ChildRepository childDao;
    private ParentRepository parentDao;
    private PasswordEncoder encoder;
    private UserRepository userDao;


    @Autowired
    public ChildController(
        ChildRepository childDao,
        ParentRepository parentDao,
        PasswordEncoder encoder,
        UserRepository userDao
    ) {
        this.childDao=childDao;
        this.parentDao = parentDao;
        this.encoder = encoder;
        this.userDao = userDao;
    }

//    @GetMapping("/child-profile")
//    public String childHomePage() {
//        return "users/child-profile";
//    }

    @GetMapping("/children/new")
    public String registerForm(Model viewModel){
        viewModel.addAttribute("user", new User());
        return "users/register-child";
    }

    @PostMapping("/children/new")
    public String registerChild(@ModelAttribute User user) {
        User parentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Parent parent = parentDao.findByUser(parentUser);

        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);
        user.isAChild();
        userDao.save(user);

        Child child = new Child();
        child.setUser(user);
        child.setParent(parent);
        childDao.save(child);

        return "redirect:/profile";
    }
}
