package com.capstone.demo.controllers;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////
import com.capstone.demo.models.Child;
import com.capstone.demo.models.Goal;
import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.GoalRepository;
import com.capstone.demo.repositories.ParentRepository;
import com.capstone.demo.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

///////////////////////////////////////////////////////////////////
// This controller class is used to authenticate users.
// Therefore, it offers methods that can be used to login
// and can be used to view certain pages that need authentication.
///////////////////////////////////////////////////////////////////

//////////////////////////////
// Controller annotation. Indicating spring framework how this specific class behaves.
//////////////////////////////
@Controller
public class AuthenticationController {

    //////// ATTRIBUTES ////////////
    ////////////////////////////////
    // Private fields(attributes)
    ////////////////////////////////
    private ParentRepository parentDao;
    private ChildRepository childDao;
    private GoalRepository goalDao;
    private UserRepository userDao;

    ///////////////////// CONSTRUCTOR METHOD /////////////////////////////
    //////////////////////////////////////////////////////////////////////
    // Constructor method used to initialize this class when instantiated.
    // Also, dependency injection is being used in order to facilitate the
    // usage of methods and attributes that belong to other classes.
    //////////////////////////////////////////////////////////////////////
    public AuthenticationController(ParentRepository parentDao, ChildRepository childDao, GoalRepository goalDao, UserRepository userDao) {
        this.parentDao = parentDao;
        this.childDao = childDao;
        this.goalDao = goalDao;
        this.userDao = userDao;

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// METHODS ///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////
    // Method used to take user to login page
    ///////////////////////////////////////////
    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }


    ///////////////////////////////////////////////////
    // Method displaying view based on the user role.
    ///////////////////////////////////////////////////
    @GetMapping("/profile")
    public String dashboard(Model viewModel) {

        //////////////////////////////
        //
        //////////////////////////////
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //////////////////
        Parent parent = parentDao.findByUser(user);

        //////////////////////////////
        // List og goals that have a relation with this user.
        //////////////////////////////
        List<Goal> goals = goalDao.findByUserId(user.getId());

        ///////////////////////////////////////////////////////////////
        // Take user to child interface view if parent object is of type null
        ////////////////////////////////////////////////////////////////
        if (parent == null) {
            viewModel.addAttribute("child", childDao.findByUser(user));
            return "users/profile/child-profile";
        }


        ///////////////////////////////////////////////////////
        // List of children that have  relation to this parent.
        ///////////////////////////////////////////////////////
        List<Child> children = childDao.findAllByParentId(parent.getId());

        /////////////////////////////////////////////////////
        // Add these instantiated objects to the view model.
        // So, they can be accessed in the parent/child view.
        /////////////////////////////////////////////////////
        viewModel.addAttribute("parent", parent);
        viewModel.addAttribute("goals", goals);
        viewModel.addAttribute("children", children);

        ///////////////////////////////////////
        // Take user to parent interface view.
        ///////////////////////////////////////
        return "users/profile/parent-profile";
    }


    ///////////////////////////////////////////////
    // Method used to take authenticated user
    // to any child child profile that is related
    // this user.
    ////////////////////////////////////////////////
    @GetMapping("/view-child-profile/{id}")
    public String viewProfile(Model viewModel, @PathVariable Long id) {

        /////////////////////////////
        User parentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ////////////////////////////
        Parent parent = parentDao.findByUser(parentUser);

        ////////////////////////////
        User user = userDao.findById(id);

        ////////////////////////////
        Child child = childDao.findByUser(user);

        ////////////////////////////
        List<Goal> incompleteGoals = goalDao.findByUserIfGoalIsIncomplete(user.getId());

        ////////////////////////////
        viewModel.addAttribute("goals", incompleteGoals);
        viewModel.addAttribute("child", child);
        viewModel.addAttribute("child", child);
        viewModel.addAttribute("user", user);
        viewModel.addAttribute("parent", parent);

        return "users/profile/view-child-profile";


    }


    //////////////////////////////
    // Take user to about us page
    //////////////////////////////
    @GetMapping("/about-us")
    public String aboutUsPage() {
        return "users/about-us";
    }
}