package com.capstone.demo.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "children")
public class Child {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Column(nullable = false, name = "username")
//    private String username;

    @OneToOne
    private User user;


    @Column
    private long score;

    //////////////////////////////////////////////////////////
    // Many to many connection to Parent model.
    //////////////////////////////////////////////////////////
    @ManyToOne
//    @JoinColumn(name = "parent_id")
    private Parent parent;

    public Child() {
    }

    public Child(Long id, Long score, Parent parent, User user){
        this.id=id;
        this.score=score;
        this.parent=parent;
        this.user=user;
    }


    //////////////////////////////////////////////////////////
    // Setters and getters.
    //////////////////////////////////////////////////////////
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void increasScore(Goal goal) {
        score += goal.getTotalPoints();
    }
}
