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
import com.capstone.demo.services.GoalsService;
import com.capstone.demo.services.TasksService;
import com.capstone.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class GoalController {

    //////// ATTRIBUTES ////////////
    ////////////////////////////////
    // Private fields(attributes)
    ////////////////////////////////
    private final GoalsService service;
    private final GoalRepository goalDao;
    private ParentRepository parentDao;
    private UserRepository userDao;
    private final TasksService Tservice;
    private final UserService userService;
    private ChildRepository childDao;

    ///////////////////// CONSTRUCTOR METHOD /////////////////////////////
    //////////////////////////////////////////////////////////////////////
    // Constructor method used to initialize this class when instantiated.
    // Also, dependency injection is being used in order to facilitate the
    // usage of methods and attributes that belong to other classes.
    //////////////////////////////////////////////////////////////////////
    @Autowired
    public GoalController(
        GoalsService service,
        GoalRepository goalDao,
        ParentRepository parentDao,
        UserRepository userDao,
        TasksService Tservice,
        UserService userService,
        ChildRepository childDao
    ) {
        this.service = service;
        this.goalDao = goalDao;
        this.parentDao = parentDao;
        this.userDao = userDao;
        this.Tservice = Tservice;
        this.userService = userService;
        this.childDao = childDao;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// METHODS ///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    @GetMapping("/goals")
    public String showGoals(Model viewModel, @ModelAttribute Goal goal) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Parent parent = parentDao.findByUser(user);

        List<Child> children = childDao.findAllByParentId(parent.getId());

        List<Long> childrenIds = children.stream().map(child -> child.getUser().getId()).collect(Collectors.toList());
        Iterable<Goal> incompleteGoals = goalDao.childrenIncompleteGoals(childrenIds);

        String role=user.getRole();

        viewModel.addAttribute("role",role);
        viewModel.addAttribute("goals", incompleteGoals);
        viewModel.addAttribute("child", children);
        viewModel.addAttribute("parent",parent);

        return "goals/index";

    }

    @GetMapping("/goals/child")
    public String showGoalsIfChild(Model viewModel, @ModelAttribute Goal goal) {
        viewModel.addAttribute("goals", service.findAll());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Parent parent = parentDao.findByUser(user);
        viewModel.addAttribute("child", childDao.findByUser(user));
        viewModel.addAttribute("parent",parent);
        String role = user.getRole();
        System.out.println(user.getRole());
        viewModel.addAttribute("role",role);

        return "goals/child-index";

    }



    @GetMapping("/goals/{id}")
    public String showGoals (@PathVariable long id, Model viewModel){
        Goal goal = service.findById(id);
        viewModel.addAttribute("goal", service.findById(id));


        return "goals/show";
    }
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    ////////////////////////////////
    // Parent user create new goal.
    ////////////////////////////////
    @GetMapping("/goals/create")
    public String showCreateForm(Model viewModel) {

        ////////////////////////////
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ///////////////////////////
        Parent parent = parentDao.findByUserId(user.getId());

        //////////////////////////
        viewModel.addAttribute("children", childDao.findAllByParentId(parent.getId()));
        viewModel.addAttribute("goal", new Goal());

        /////////////////////////
        return "goals/create";
    }

    ////////////////////////////////////////
    // Persist new goal data to goal table
    ////////////////////////////////////////
    @PostMapping("/goals/create")
    public String createGoal(@ModelAttribute Goal goal,
                             Errors validation,
                             @RequestParam(name = "child_id") Long childId,
                             Model model,
                             RedirectAttributes redirect
    ) {

        ////////////////////////////////
        // Validate user.
        ////////////////////////////////
        if (validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("goal", goal);
            return "goals/create";
        }

        ////////////////////////////////////////////////////////////////////
        // If user validate create goal and redirect user to parent profile
        ////////////////////////////////////////////////////////////////////
        goal.setUser(userDao.findByChildId(childId));
        service.save(goal);
        return "redirect:/profile";
    }

    //////////////////////////////////////////////
    // Delete goal; not need for get mapping,
    // since edit method is retrieving the data.
    ///////////////////////////////////////////////
    @PostMapping("/goals/delete")
    public String deleteGoal (@ModelAttribute Goal goal, @RequestParam(name = "goalIdy") long goalIdy) {

        //////////////////////////////
        service.delete(goalIdy);

        ///////////////////////////////
        return "redirect:/profile";
    }

    ////////////////////////////////
    // Edit goal
    ////////////////////////////////
    @GetMapping("/goals/update/{id}")
    public String showGoalToBeEdited (@PathVariable long id, Model viewModel){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ///////////////////////////////
        Parent parent = parentDao.findByUser(user);

        //////////////////////////////
        Goal goal = goalDao.findById(id);

        //////////////////////////////
        List<Child> children = childDao.findAllByParentId(parent.getId());

        //////////////////////////////
        List<Long> childrenIds = children.stream().map(child -> child.getUser().getId()).collect(Collectors.toList());

        //////////////////////////////
        Iterable<Goal> incompleteGoals = goalDao.childrenIncompleteGoals(childrenIds);

        //////////////////////////////
        String role = user.getRole();

        //////////////////////////////
        viewModel.addAttribute("goal", goal);
        viewModel.addAttribute("role",role);
        viewModel.addAttribute("goals", incompleteGoals);
        viewModel.addAttribute("child", children);
        viewModel.addAttribute("parent",parent);

        ///////////////////////////
        return "goals/update";
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // Persist updated data back into goals table. Retrieve goal id as a hidden input from view,
    // and used request param annotation to to use id in order to find goal and edit the same.
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/goals/update")
    public String editGoal(@ModelAttribute Goal goal, @RequestParam(name = "goalIdx") long goalIdx){

        ////////////////////////////////
        goal.setId(goalIdx);

        ////////////////////////////////
        goal.setUser(userDao.findByGoalId(goal.getId()));

        ////////////////////////////////
        service.save(goal);

        ////////////////////////////////
        return "redirect:/profile";
    }


    /////////////////////////////////////////////////////////////
    // Show goals that are reached. I don't know if this works.
    //////////////////////////////////////////////////////////////
    @GetMapping("/goals/{id}/reached")
    public String showReachedGoals (@PathVariable long id, Model viewModel, @ModelAttribute Goal goal){

        //////////////////////////////
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //List<Goal> goals = goalDao.findIfGoalIsComplete(user.getId());
        //Goal goal = service.findById(id);
        //List<Goal>goals = goalDao.findIfGoalIsComplete(user.getId());
        //viewModel.addAttribute("goal", service.findById(id));
        //viewModel.addAttribute("goals",goals);

        ///////////////////////////////
        return "goals/reached";
    }

    /////////////////////////////////////////////////////////////
    // Show goals that are complete. I don't know if this works.
    //////////////////////////////////////////////////////////////
    @GetMapping("/goals/completed/{id}")
    public String showCompletedGoals(@PathVariable Long id, Model viewModel, @ModelAttribute Goal goal){

        ////////////////////////////////
        User parentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ////////////////////////////////
        Parent parent = parentDao.findByUser(parentUser);

        ////////////////////////////////
        User user = userDao.findById(id);

        ////////////////////////////////
        viewModel.addAttribute("user", user);

        ////////////////////////////////
        Child child = childDao.findByUser(user);

        ///////////////////////////////
        List<Goal> completeGoals = goalDao.findIfGoalIsComplete(user.getId());

        ///////////////////////////////
        viewModel.addAttribute("user", user);

        //////////////////////////////
        viewModel.addAttribute("goals",completeGoals);
        viewModel.addAttribute("parent",parent);
        viewModel.addAttribute("child",child);

        //////////////////////////////
        return "goals/completed";

    }

}
