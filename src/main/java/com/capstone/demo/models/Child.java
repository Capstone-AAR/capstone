package com.capstone.demo.models;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////

import javax.persistence.*;
import java.util.List;

//////////////////////////////////////////////////////////////////////////////
// Annotation indicating this class is an entity that will
// model a table inside my database. NOTE; the table annotation
// tells Spring what to name the table that will be modeled after this entity.
///////////////////////////////////////////////////////////////////////////////
@Entity
@Table(name = "children")
public class Child {

    //////// ATTRIBUTES ////////////
    ////////////////////////////////
    // Private fields(attributes)
    ////////////////////////////////

    ////////////////////////////////////////
    // Annotations connecting table columns
    // to equivalent fields.
    ////////////////////////////////////////
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;


    @Column
    private long score;

    //////////////////////////////////////////////////////////
    // Many to many connection to Parent model.
    //////////////////////////////////////////////////////////
    @ManyToOne
    private Parent parent;

    public Child() {

    }

    ///////////////////// CONSTRUCTOR METHOD /////////////////////////////
    //////////////////////////////////////////////////////////////////////
    // Constructor method used to initialize this class when instantiated.
    //////////////////////////////////////////////////////////////////////
    public Child(Long id, Long score, Parent parent, User user) {

        this.id = id;

        this.score = score;

        this.parent = parent;

        this.user = user;
    }


    //////////////////////////////////////////////////////////
    ///////////////// Setters and getters. ///////////////////
    //////////////////////////////////////////////////////////

    ////////////////////////
    public long getId() {
        return id;
    }

    ////////////////////////
    public void setId(long id) {
        this.id = id;
    }
    ////////////////////////
    public long getScore() {
        return score;
    }

    ////////////////////////
    public void setScore(long score) {
        this.score = score;
    }

    ////////////////////////
    public Parent getParent() {
        return parent;
    }

    ////////////////////////
    public void setParent(Parent parent) {
        this.parent = parent;
    }

    ////////////////////////
    public User getUser() {
        return user;
    }

    ////////////////////////
    public void setUser(User user) {
        this.user = user;
    }

    ////////////////////////
    public void increasScore(Goal goal) {
        score += goal.getTotalPoints();
    }
}
