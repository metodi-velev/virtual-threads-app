package com.example.virtual_threads_demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "geo")
public class GeoEntity extends BaseEntity {

    private String lat;
    private String lng;

    // getters and setters
}
