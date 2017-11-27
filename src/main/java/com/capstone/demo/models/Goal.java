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
    private Integer totalPoints;

    @Column()
    private Integer trackProgress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goal")
    private List<Task> tasks;

//    @ManyToOne
//    @JoinColumn(name="parent_id")
//    private Parent parent;

    public Goal(Goal goal) {
        this.id = goal.id;
        this.goalName = goal.goalName;
        this.trackProgress = goal.trackProgress;
        this.tasks = goal.tasks;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Goal(String goalName, Integer totalPoints, Integer trackProgress){
        this.goalName = goalName;
        this.totalPoints =totalPoints;
        this.trackProgress = trackProgress;


    }
    public Goal(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

//    public List<Task> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(List<Task> tasks) {
//        this.tasks = tasks;
//    }


}

