package com.capstone.demo.models;

import javax.persistence.*;


@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String taskName;

    @Column(nullable = false)
    private String taskDescription;

    @Column(nullable = false)
    private Integer pointValue;

    private TaskStatus status;

    @ManyToOne
    @JoinColumn (name = "goal_id")
    private Goal goal;

    public Task(Task task) {
        this.id = task.id;
        this.taskName = task.taskName;
        this.pointValue = task.pointValue;
        this.status = task.status;
        this.taskDescription = task.taskDescription;
        this.goal = task.goal;
    }

    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    public Task(){

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

    public Integer getPointValue() {
        return pointValue;
    }

    public void setPointValue(Integer pointValue) {
        this.pointValue = pointValue;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void seStatus(TaskStatus status) {
        this.status = status;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }


}
