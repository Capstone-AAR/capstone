package com.capstone.demo.models;

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
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column()
    private Integer trackProgress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goals")
    private List<Task> tasks;

    public Goal(Goal goal) {
        this.id = goal.id;
        this.goalName = goal.goalName;
        this.startDate = goal.startDate;
        this.endDate = goal.endDate;
        this.trackProgress = goal.trackProgress;
        this.tasks = goal.tasks;
    }

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
