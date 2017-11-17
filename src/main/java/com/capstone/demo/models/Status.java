package com.capstone.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "taskStatus")
public class Status {
    ///might not work, switch to auto increment if problems occur
    @Id
    @Column(unique = true)
    private Long id;

    @Column
    private String statusName;

    public Status() {

    }

    public Status(Status status) {
        this.id = status.id;
        this.statusName = status.statusName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
