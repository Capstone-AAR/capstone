package com.capstone.demo.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, name = "username")
    private String username;


    @Column(nullable = false, unique = true, name = "email")
    @NotBlank
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Goal>goals;

    public User() {
    }
    public User(Long id, String username, String password, String email, List<Goal> goals){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.goals = goals;
    }

    public User(User copy) {
        id = copy.id;
        username = copy.username;
        password = copy.password;
        email = copy.email;
        goals = copy.goals;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
