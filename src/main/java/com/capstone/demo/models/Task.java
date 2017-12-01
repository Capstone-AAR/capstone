package com.capstone.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty("title")
    private String taskName;

    @Column(nullable = false)
    private String taskDescription;

    @JsonProperty("start")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @JsonProperty("end")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private Integer points;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    private TaskStatus status;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Goal getGoal() {
        return this.goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void updateGoalProgress() {
        goal.setTrackProgress(goal.getTrackProgress() + getPoints());
    }

    public boolean completesGoal() {
        return goal.isComplete();
    }

    public Long childUserId() {
        return goal.getUser().getId();
    }
}
