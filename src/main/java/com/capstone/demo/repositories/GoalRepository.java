package com.capstone.demo.repositories;

import com.capstone.demo.models.Goal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GoalRepository extends CrudRepository<Goal,Long> {
    Goal findByGoalName(String goalName);

    @Query("from Goal g where g.id=?1")
    public Goal findById(long id);

    @Query("FROM Goal g where user_id = ?1")
    public Goal findAllByUserId(long id);
}
