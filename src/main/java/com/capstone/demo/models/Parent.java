package com.capstone.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(nullable = false, unique = true, name = "username")
    @NotBlank()
    private String username;

    @Column(nullable = false, unique = true, name = "email")
    @NotBlank
    private String email;

    @Column(nullable = false, name = "password")
    @NotBlank
    private String password;

//    public List<Task> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(List<Task> tasks) {
//        this.tasks = tasks;
//    }

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
//    private List<Task> tasks;

//    private List<Task> getJtasks() {
//        return Jtasks;
//    }
//
//    public void setJtasks(List<Task> jtasks) {
//        Jtasks = jtasks;
//    }

    //////////////////////////////////////////////////////////
    // Many to many connection to child model.
    //////////////////////////////////////////////////////////
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "relation",
            joinColumns = {@JoinColumn(name = "parent_id")},
            inverseJoinColumns = {@JoinColumn(name = "child_id")}
    )

    //////////////////////////////////////////////////////////
    // Many to many connection to Task model. JSON usage
    //////////////////////////////////////////////////////////
//    @OneToMany(mappedBy = "parent")
//    @JsonBackReference
//    private List<Task> Jtasks;

    //////////////////////////////////////////////////////////
    // Child object list populated by children retrieved
    // from the many to many connection.
    //////////////////////////////////////////////////////////
    private List<Child> children;

    public Parent() {

    }

    public Parent(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /////////////////////////////////////////////////////////////////
    // Copy constructor used for authentication and validation
    ////////////////////////////////////////////////////////////////
    public Parent(Parent parentCopy) {
        this.id = parentCopy.id;
        this.username = parentCopy.username;
        this.password = parentCopy.password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}

