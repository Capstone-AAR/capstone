package com.capstone.demo.repositories;

import com.capstone.demo.models.Goal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GoalRepository extends CrudRepository<Goal,Long> {
    Goal findByTitle(String title);

    @Query("from Goal g where g.id=?1")
    public Goal findById(long id);
}
