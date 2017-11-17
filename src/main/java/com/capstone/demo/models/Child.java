package com.capstone.demo.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "children")
public class Child {
    private Long id;

    @ManyToMany(mappedBy = "children")
    private List<User> users;

    public Child(Child child) {
        this.id = child.id;
        this.users = child.users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
