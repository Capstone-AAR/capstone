package com.capstone.demo.controllers;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////
import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.ParentRepository;
import com.capstone.demo.repositories.UserRepository;
import com.capstone.demo.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class ParentsController {

    //////// ATTRIBUTES ////////////
    ////////////////////////////////
    // Private fields(attributes)
    ////////////////////////////////
    private UserRepository repository;
    private PasswordEncoder encoder;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final UserService userService;


    ///////////////////// CONSTRUCTOR METHOD /////////////////////////////
    //////////////////////////////////////////////////////////////////////
    // Constructor method used to initialize this class when instantiated.
    // Also, dependency injection is being used in order to facilitate the
    // usage of methods and attributes that belong to other classes.
    //////////////////////////////////////////////////////////////////////
    public ParentsController(
            PasswordEncoder encoder,
            UserRepository repository,
            ParentRepository parentRepository,
            ChildRepository childRepository,
            UserService userService
    ) {
        this.repository = repository;
        this.encoder = encoder;
        this.parentRepository = parentRepository;
        this.childRepository = childRepository;
        this.userService = userService;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// METHODS ///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////
    // Display index page to user.
    //////////////////////////////////////////////////////////
    @GetMapping("/")
    public String homePage() {

        ////////////////////////////
        return "users/index";
    }

    ////////////////////////////////////////////////////////
    // Registration page. (Parent)
    ////////////////////////////////////////////////////////
    @GetMapping("/register")
    public String registerPage(Model viewModel) {

        //////////////////////////////
        viewModel.addAttribute("parent", new User());

        /////////////////////////////
        return "users/register";
    }

    ////////////////////////////////////////////////////////
    // Persist new user data to user table.
    ////////////////////////////////////////////////////////
    @PostMapping("/users/register")
    public String registerForm(@Valid User user, Errors validation, Model model) {

        /////////////////////////////////////
        String username = user.getUsername();

        //////////////////////////////////
        User existingUsername = repository.findByUsername(username);

        /////////////////////////////////
        User existingEmail = repository.findByEmail(user.getEmail());

        ////////////////////////////////////////////////////////
        // Validating new user.
        ////////////////////////////////////////////////////////
        if (existingUsername != null) {
            validation.rejectValue("username", "user.username", "Duplicated username " + username);
        }

        if (existingEmail != null) {
            validation.rejectValue("email", "user.email", "Duplicated email " + user.getEmail());
        }

        if (validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("parent", user);
            return "users/register";
        }


        user.setPassword(encoder.encode(user.getPassword()));
        user.isAParent();
        repository.save(user);

        Parent parent = new Parent();
        parent.setUser(user);
        parentRepository.save(parent);

        /////////////////////////////////////////////////////////////////////
        // Redirect user to login page in order to login with new account.
        /////////////////////////////////////////////////////////////////////
        return "redirect:/login";
    }

}
