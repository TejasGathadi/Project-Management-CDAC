package com.cdacProject.taskhive.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // full name of user
    private String fullName;

    // user email to login
    private String email;

    // user password to login
    private String password;

    // to specify the project size to check the number of projects
    // so the user have only 3 free plans
    private int projectSize;


    // entity relation one to many as one user has many
    // issues so that one to many
    @JsonIgnore
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    private List<Issues> assignIssues = new ArrayList<>();





}
