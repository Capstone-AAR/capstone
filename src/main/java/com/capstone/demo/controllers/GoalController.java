package com.capstone.demo.controllers;

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

    private final GoalsService service;
    private final GoalRepository goalDao;
    private ParentRepository parentDao;
    private UserRepository userDao;
    private final TasksService Tservice;
    private final UserService userService;
    private ChildRepository childDao;

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

    @GetMapping("/goals")
    public String showGoals(Model viewModel, @ModelAttribute Goal goal) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Parent parent = parentDao.findByUser(user);

        List<Child> children = childDao.findAllByParentId(parent.getId());

        List<Long> childrenIds = children.stream().map(Child::getId).collect(Collectors.toList());
        Iterable<Goal> incompleteGoals = goalDao.childrenIncompleteGoals(childrenIds);

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
        String role=user.getRole();
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

    @GetMapping("/goals/create")
    public String showCreateForm(Model viewModel) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Parent parent = parentDao.findByUserId(user.getId());
        viewModel.addAttribute("children", childDao.findAllByParentId(parent.getId()));
        viewModel.addAttribute("goal", new Goal());
        return "goals/create";
    }

    @PostMapping("/goals/create")
    public String createGoal(@ModelAttribute Goal goal,
                             Errors validation,
                             @RequestParam(name = "child_id") Long childId,
                             Model model,
                             RedirectAttributes redirect
    ) {

        if (validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("goal", goal);
            return "goals/create";
        }

        goal.setUser(userDao.findByChildId(childId));
        service.save(goal);
        return "redirect:/goals";
    }

    @GetMapping("/goals/{id}/delete")
    public String showGoalToBeDeleted(@PathVariable Long id, Model viewModel){
        Goal goal = service.findById(id);
        viewModel.addAttribute("goal", goal);
        return "goals/delete";
    }

    @PostMapping("/goals/{id}/delete")
    public String deleteGoal (@ModelAttribute Goal goal, @PathVariable Long id) {
        service.delete(id);
        return "redirect:/goals";
    }

    @GetMapping("/goals/{id}/update")
    public String showGoalToBeEdited (@PathVariable Long id,  Model viewModel){
        Goal existingGoal = service.findById(id);
        viewModel.addAttribute("goal", existingGoal);

        return "goals/update";
    }

    @PostMapping("/goals/{id}/update")
    public String editGoal(@ModelAttribute Goal goal){
        User user = userDao.findByGoalId(goal.getId());
        goal.setUser(user);
        service.save(goal);

        if (goal.isComplete()) {
            Child child = childDao.findByUserId(goal.getUser().getId());
            child.increasScore(goal);
            childDao.save(child);
        }


        return "redirect:/goals/" + goal.getId();
    }

    @GetMapping("/goals/{id}/reached")
    public String showReachedGoals (@PathVariable long id, Model viewModel, @ModelAttribute Goal goal){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //List<Goal> goals = goalDao.findIfGoalIsComplete(user.getId());
        //Goal goal = service.findById(id);
        //List<Goal>goals = goalDao.findIfGoalIsComplete(user.getId());
        //viewModel.addAttribute("goal", service.findById(id));
        //viewModel.addAttribute("goals",goals);



        return "goals/reached";
    }

    @GetMapping("/goals/completed/{id}")
    public String showCompletedGoals(@PathVariable Long id, Model viewModel, @ModelAttribute Goal goal){
        User parentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Parent parent = parentDao.findByUser(parentUser);
        User user = userDao.findById(id);
        viewModel.addAttribute("user", user);

        //User user = userDao.findById(id);
        Child child = childDao.findByUser(user);
        List<Goal> completeGoals = goalDao.findIfGoalIsComplete(user.getId());
        //List<User> users = userDao.findAllByParentId(user.getId());
        viewModel.addAttribute("user", user);
        //viewModel.addAttribute("children",children);

        viewModel.addAttribute("goals",completeGoals);
        viewModel.addAttribute("parent",parent);
        viewModel.addAttribute("child",child);
        System.out.println(goalDao.findIfGoalIsComplete(user.getId()));

        return "goals/completed";

//        User parentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Parent parent = parentDao.findByUser(parentUser);
//        User user = userDao.findById(id);
//        viewModel.addAttribute("goals",goalDao.findIfGoalIsComplete(user.getId()));


        //Working on making a points system

        //System.out.println(goals.getTotalPoints());
        //if (goal.getTotalPoints() == goal.getTrackProgress()) {
          //  System.out.println("goal complete!!!");
        //}


        //List<Goal>goals =
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Parent parent = parentDao.findByUser(user);
        //Erased... but used to work for parents' goals
        //List<Goal> goals = goalDao.findIfGoalIsComplete(goal.getId());

//        List<Child> children = childDao.findAllByParentId(parent.getId());
//        viewModel.addAttribute("parent",parent);
        //viewModel.addAttribute("goals", goals);
        // viewModel.addAttribute("children",children);
        //System.out.println(goalDao.findIfGoalIsComplete(goal.getId()));

//        if (goal.getTotalPoints() == goal.getTrackProgress()) {
//
//
//            System.out.println(goalDao.findIfGoalIsComplete(user.getId()));
//        }
    }

}
