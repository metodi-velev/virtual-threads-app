package com.example.virtual_threads_demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "companies")
public class CompanyEntity extends BaseEntity {

    private String name;
    private String catchPhrase;
    private String bs;

    // getters and setters
}

