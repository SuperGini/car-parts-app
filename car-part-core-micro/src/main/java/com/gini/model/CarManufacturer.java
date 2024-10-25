package com.gini.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "car_manufacturer")
public class CarManufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private Car car;



}
