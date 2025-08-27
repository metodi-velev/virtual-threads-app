package com.example.virtual_threads_demo.model;

// User.java

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`user`")
@Getter
@Setter
public class User {
    @Id
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String company;
    private int postsCount;
    private int commentsCount;

    public User() {}

    public User(String name, String email, String company) {
        this.name = name;
        this.email = email;
        this.company = company;
    }
}
