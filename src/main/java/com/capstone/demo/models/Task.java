package com.capstone.demo.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {
    private Long id;
    private String taskName;
    private Integer pointValue;

    public Task() {

    }

    public Task(Task task) {
        this.id = task.id;
        this.taskName = task.taskName;
        this.pointValue = task.pointValue;
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


}
