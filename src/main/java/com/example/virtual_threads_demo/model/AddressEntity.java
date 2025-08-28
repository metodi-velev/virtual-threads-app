package com.example.virtual_threads_demo.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "addresses")
public class AddressEntity extends BaseEntity {

    private String street;
    private String suite;
    private String city;
    private String zipcode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geo_id", referencedColumnName = "id")
    private GeoEntity geo;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    // getters and setters
}
