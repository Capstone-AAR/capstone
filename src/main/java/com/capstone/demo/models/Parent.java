package com.capstone.demo.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parents")
public class Parent {

    //////////////////////////////////////////////////////////
    // Fields (Attributes)
    //////////////////////////////////////////////////////////
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;

    //////////////////////////////////////////////////////////
    // Many to many connection to child model.
    //////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
    // Child object list populated by children retrieved
    // from the many to many connection.
    //////////////////////////////////////////////////////////

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private List<Child> children;

    public Parent() {

    }

    /////////////////////////////////////////////////////////////////
    // Copy constructor used for authentication and validation
    ////////////////////////////////////////////////////////////////
    public Parent(Parent parentCopy) {
        this.id = parentCopy.id;
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

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

