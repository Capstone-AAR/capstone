package com.capstone.demo.models;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

//////////////////////////////////////////////////////////////////////////////
// Annotation indicating this class is an entity that will
// model a table inside my database. NOTE; the table annotation
// tells Spring what to name the table that will be modeled after this entity.
///////////////////////////////////////////////////////////////////////////////
@Entity
@Table(name = "tasks")
public class Task {

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

    ////////////////////////////////////////////////////
    // Json property annotation indicates that this
    // field will sent as a property in a json object.
    ////////////////////////////////////////////////////
    @Column(nullable = false)
    @JsonProperty("title")
    private String taskName;

    @Column(nullable = false)
    private String taskDescription;

    //////////////////////////////////////////////////////////////////
    // Temporal annotation is used to deal with data, time, calendar
    // in a a table or database. It is used in conjunction with id
    // annotation or element collections.
    /////////////////////////////////////////////////////////////////
    @JsonProperty("start")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @JsonProperty("end")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private Integer points;

    //////////////////////////////////////////////////////////////////////////////////
    // Many to one connection to Goal model. Also, Json managed reference annotation
    // used to prevent infinite loop or stack overflow error
    //////////////////////////////////////////////////////////////////////////////////
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    private TaskStatus status;

    /////////////////////////////////////////////////////////////////
    // Copy constructor used for authentication and validation
    ////////////////////////////////////////////////////////////////
    public Task(Task task) {

        this.id = task.id;

        this.taskName = task.taskName;

        this.status = task.status;

        this.taskDescription = task.taskDescription;

        this.goal = task.goal;
    }

    public Task(String taskName, String taskDescription) {

        this.taskName = taskName;

        this.taskDescription = taskDescription;
    }

    public Task() {

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
    public String getTaskName() {
        return taskName;
    }

    ////////////////////////
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    ////////////////////////
    public String getTaskDescription() {
        return taskDescription;
    }

    ////////////////////////
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    ////////////////////////
    public Date getStartDate() {
        return startDate;
    }

    ////////////////////////
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    ////////////////////////
    public Date getEndDate() {
        return endDate;
    }

    ////////////////////////
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    ////////////////////////
    public TaskStatus getStatus() {
        return status;
    }

    ////////////////////////
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    ////////////////////////
    public Goal getGoal() {
        return this.goal;
    }

    ////////////////////////
    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    ////////////////////////
    public Integer getPoints() {
        return points;
    }

    ////////////////////////
    public void setPoints(Integer points) {
        this.points = points;
    }

    ////////////////////////
    public void updateGoalProgress() {
        goal.setTrackProgress(goal.getTrackProgress() + getPoints());
    }

    ////////////////////////
    public boolean completesGoal() {
        return goal.isComplete();
    }

    ////////////////////////
    public Long childUserId() {
        return goal.getUser().getId();
    }
}
