package com.capstone.demo.controllers;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////
import com.capstone.demo.models.Child;
import com.capstone.demo.models.Goal;
import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.ParentRepository;
import com.capstone.demo.repositories.UserRepository;
import com.capstone.demo.services.GoalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;


@Controller
public class ChildController {

    //////// ATTRIBUTES ////////////
    ////////////////////////////////
    // Private fields(attributes)
    ////////////////////////////////
    private ChildRepository childDao;
    private ParentRepository parentDao;
    private PasswordEncoder encoder;
    private UserRepository userDao;
    private GoalsService goalsService;


    /////////////////////////////////////////////////////////////////////////////
    // Autowire annotation when used in a constructor method it allows you to
    // skip configurations elsewhere of what to inject and just does it for you.
    //////////////////////////////////////////////////////////////////////////////
    @Autowired
    public ChildController(
            ChildRepository childDao,
            ParentRepository parentDao,
            PasswordEncoder encoder,
            UserRepository userDao,
            GoalsService goalsService
    ) {
        this.childDao = childDao;
        this.parentDao = parentDao;
        this.encoder = encoder;
        this.userDao = userDao;
        this.goalsService = goalsService;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// METHODS ///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////
    // Register new child object
    //////////////////////////////
    @GetMapping("/children/new")
    public String registerForm(Model viewModel) {

        ////////////////////////
        viewModel.addAttribute("user", new User());

        ///////////////////////////////////////
        // Return user to register child page.
        ////////////////////////////////////////
        return "users/register-child";
    }

    ////////////////////////////////////////////////
    // Persist new data(child) entry to user table.
    ////////////////////////////////////////////////
    @PostMapping("/children/new")
    public String registerChild(@ModelAttribute User user) {

        //////////////////////////
        User parentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        /////////////////////////
        Parent parent = parentDao.findByUser(parentUser);

        ////////////////////////////////////////////////////////
        // Hash password and eliminate original password entry.
        /////////////////////////////////////////////////////////
        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);
        user.isAChild();
        userDao.save(user);

        /////////////////////////
        Iterable<Goal> goals = goalsService.findAll();

        /////////////////////////
        Child child = new Child();
        child.setUser(user);
        child.setParent(parent);
        childDao.save(child);

        ///////////////////////////////////////////////
        // Redirect new user to profile view interface
        ///////////////////////////////////////////////
        return "redirect:/profile";
    }
}
