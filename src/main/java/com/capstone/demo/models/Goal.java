package com.capstone.demo.models;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


//////////////////////////////////////////////////////////////////////////////
// Annotation indicating this class is an entity that will
// model a table inside my database. NOTE; the table annotation
// tells Spring what to name the table that will be modeled after this entity.
///////////////////////////////////////////////////////////////////////////////
@Entity
@Table(name = "goals")
public class Goal {

    //////// ATTRIBUTES ////////////
    ////////////////////////////////
    // Private fields(attributes)
    ////////////////////////////////

    ////////////////////////////////////////
    // Annotations connecting table columns
    // to equivalent fields.
    ////////////////////////////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String goalName;

    @Column(nullable = false)
    private Integer totalPoints;

    @Column()
    private Integer trackProgress;

    /////////////////////////////////////////////////////////////////////////////////
    // Instance of user model inside goal. Create so goal has access to users fields.
    //////////////////////////////////////////////////////////////////////////////////
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /////////////////////////////////////////////////////////////////////////////
    // One to many connection to Task model, and back reference json annotation.
    /////////////////////////////////////////////////////////////////////////////
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goal")
    private List<Task> tasks;


    public Goal(Goal goal) {

        this.id = goal.id;

        this.goalName = goal.goalName;

        this.trackProgress = goal.trackProgress;

        this.tasks = goal.tasks;

        this.user = goal.user;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    ///////////////////// CONSTRUCTOR METHOD /////////////////////////////
    //////////////////////////////////////////////////////////////////////
    // Constructor method used to initialize this class when instantiated.
    //////////////////////////////////////////////////////////////////////
    public Goal(String goalName, Integer totalPoints, Integer trackProgress, User user) {

        this.goalName = goalName;

        this.totalPoints = totalPoints;

        this.trackProgress = trackProgress;

        this.user = user;
    }


    public Goal() {

    }

    //////////////////////////////////////////////////////////
    ///////////////// Setters and getters. ///////////////////
    //////////////////////////////////////////////////////////

    ////////////////////////
    public Long getId() {
        return id;
    }

    ////////////////////////
    public void setId(Long id) {
        this.id = id;
    }

    ////////////////////////
    public Integer getTrackProgress() {
        return trackProgress;
    }

    ////////////////////////
    public void setTrackProgress(Integer trackProgress) {
        this.trackProgress = trackProgress;
    }

    public String getGoalName() {
        return goalName;
    }

    ////////////////////////
    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    ////////////////////////
    public List<Task> getTasks() {
        return tasks;
    }

    ////////////////////////
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    ////////////////////////
    public User getUser() {
        return user;
    }

    ////////////////////////
    public void setUser(User user) {
        this.user = user;
    }

    ////////////////////////
    public boolean isComplete() {
        return totalPoints == trackProgress;
    }
}

