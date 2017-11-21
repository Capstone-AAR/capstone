package com.capstone.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String goalName;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    @Column()
    private Integer trackProgress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goal")
    private List<Task> tasks;

    public Goal(Goal goal) {
        this.id = goal.id;
        this.goalName = goal.goalName;
        this.startDate = goal.startDate;
        this.endDate = goal.endDate;
        this.trackProgress = goal.trackProgress;
        this.tasks = goal.tasks;
    }

    public Goal(String goalName, Date startDate, Date endDate ){
        this.goalName = goalName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Goal(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getTrackProgress() {
        return trackProgress;
    }

    public void setTrackProgress(Integer trackProgress) {
        this.trackProgress = trackProgress;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
