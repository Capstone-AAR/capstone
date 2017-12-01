package com.capstone.demo.repositories;

import com.capstone.demo.models.Goal;
import com.capstone.demo.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoalRepository extends CrudRepository<Goal,Long> {
    Goal findByGoalName(String goalName);

    @Query("from Goal g where g.id=?1")
    public Goal findById(long id);

    List<Goal> findByUserId(Long id);

    @Query(nativeQuery = true, value="SELECT * FROM GOALS WHERE total_points = track_progress;")
    List<Goal> findIfGoalIsComplete(Long userId);

    //Iterable<Goal> findIfGoalIsComplete (Long userId);

    //@Query(nativeQuery = true, value = "Select * from goals where user_id ")
}

