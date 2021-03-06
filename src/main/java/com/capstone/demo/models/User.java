package com.capstone.demo.models;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

//////////////////////////////////////////////////////////////////////////////
// Annotation indicating this class is an entity that will
// model a table inside my database. NOTE; the table annotation
// tells Spring what to name the table that will be modeled after this entity.
///////////////////////////////////////////////////////////////////////////////
@Entity
@Table(name = "users")
public class User {

    //////// ATTRIBUTES ////////////
    ////////////////////////////////
    // Private fields(attributes)
    ////////////////////////////////

    private static final String PARENT = "parent";

    private static final String CHILD = "child";

    ////////////////////////////////////////
    // Annotations connecting table columns
    // to equivalent fields.
    ////////////////////////////////////////
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "username can't be blank")
    @Column(nullable = false, unique = true, name = "username")
    private String username;


    @Column(nullable = false, unique = true, name = "email")
    @NotBlank(message = "email can't be blank")
    private String email;

    @NotBlank(message = "password can't be blank")
    @Column(nullable = false, name = "password")
    private String password;

    private String role;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Goal>goals;

    public User() {

    }

    ///////////////////// CONSTRUCTOR METHOD /////////////////////////////
    //////////////////////////////////////////////////////////////////////
    // Constructor method used to initialize this class when instantiated.
    //////////////////////////////////////////////////////////////////////
    public User(Long id, String username, String password, String email, List<Goal> goals, String role){

        this.id = id;

        this.username = username;

        this.password = password;

        this.email = email;

        this.goals = goals;

        this.role = role;
    }

    /////////////////////////////////////////////////////////////////
    // Copy constructor used for authentication and validation
    ////////////////////////////////////////////////////////////////
    public User(User copy) {

        id = copy.id;

        username = copy.username;

        password = copy.password;

        email = copy.email;

        goals = copy.goals;

        role = copy.role;
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
    public String getUsername() {
        return username;
    }

    ////////////////////////
    public void setUsername(String username) {
        this.username = username;
    }

    ////////////////////////
    public String getEmail() {
        return email;
    }

    ////////////////////////
    public void setEmail(String email) {
        this.email = email;
    }

    ////////////////////////
    public String getPassword() {
        return password;
    }

    ////////////////////////
    public void setPassword(String password) {
        this.password = password;
    }

    ////////////////////////
    public List<Goal> getGoals() {
        return goals;
    }

    ////////////////////////
    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    ////////////////////////
    public String getRole() {
        return role;
    }

    ////////////////////////
    public void setRole(String role) {
        this.role = role;
    }

    ////////////////////////
    public void isAParent() {
        role = PARENT;
    }

    ////////////////////////
    public void isAChild() {
        role = CHILD;
    }
}
