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

    @Column(nullable = false, unique = true, name = "username")
    private String username;

    @Column(nullable = false, unique = true, name = "password")
    private String password;

    @Column
    private long score;

    //////////////////////////////////////////////////////////
    // Many to many connection to Parent model.
    //////////////////////////////////////////////////////////
    @ManyToMany(mappedBy = "children")
    private List<Parent> parents;

    public Child() {

    }

    public Child(String username, String password, long score) {
        this.username = username;
        this.password = password;
        this.score = score;
    }

    /////////////////////////////////////////////////////////////////
    // Copy constructor used for authentication and validation
    ////////////////////////////////////////////////////////////////
    public Child(Child childCopy) {
        this.id = childCopy.id;
        this.username = childCopy.username;
        this.password = childCopy.password;
        this.score = childCopy.score;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }


}
