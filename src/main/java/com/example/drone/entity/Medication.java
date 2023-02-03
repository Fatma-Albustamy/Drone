package com.example.drone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "medication")
@Setter
@Getter
public class Medication extends CommonEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private float weight;

    @Column(name = "code")
    private String code;

    @Column(name = "image")
    private String image;

    @ManyToMany(mappedBy = "medicationSet")
    Set<Drone> droneSet;

}

