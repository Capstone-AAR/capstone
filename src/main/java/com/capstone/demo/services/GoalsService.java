package com.capstone.demo.services;


import com.capstone.demo.models.Goal;
import com.capstone.demo.repositories.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("GoalService")
public class GoalsService {
    private final GoalRepository goalDao;

    @Autowired
    public GoalsService(GoalRepository goalDao) {
        this.goalDao = goalDao;
    }

    //Get all the goals//

    public Iterable<Goal> findAll(){
        return goalDao.findAll();
    }

    public Goal findById(Long id){
        return goalDao.findOne(id);
    }

    public Goal save (Goal goal){
        return goalDao.save(goal);
    }

    public void delete (long id){goalDao.delete(id);}
}
