package com.capstone.demo.repositories;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////
import com.capstone.demo.models.Goal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/////////////////////////////////////////////////////////////////
// Repository is an interface used create simple queries used to
// create, reade, update, delete data in my database.
/////////////////////////////////////////////////////////////////
public interface GoalRepository extends CrudRepository<Goal,Long> {

    @Query("from Goal g where g.id=?1")
    public Goal findById(long id);

    List<Goal> findByUserId(Long id);

    @Query("select g from Goal g join g.user u where g.totalPoints = g.trackProgress and u.id = ?1")
    List<Goal> findIfGoalIsComplete(Long userId);


    @Query("select g from Goal g join g.user u where g.totalPoints <> g.trackProgress and u.id = ?1")
    List<Goal> findByUserIfGoalIsIncomplete(Long userId);

    @Query("select g from Goal g join g.user u where g.totalPoints <> g.trackProgress and u.id IN ?1")
    List<Goal> childrenIncompleteGoals(List<Long> childrenId);

}

