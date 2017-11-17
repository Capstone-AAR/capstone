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
    private Integer pointValue;

    @OneToOne
    private Status taskStatus;

    public Task() {

    }

    public Task(Task task) {
        this.id = task.id;
        this.taskName = task.taskName;
        this.pointValue = task.pointValue;
        this.taskStatus = task.taskStatus;
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

    public Status gettaskStatus() {
        return taskStatus;
    }

    public void settaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }




}
