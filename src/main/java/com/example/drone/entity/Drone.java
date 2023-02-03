package com.example.drone.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "drone")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Drone extends CommonEntity {

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "model")
    private String model;

    @Column(name = "weight")
    private float weight;

    @Column(name = "battery_capacity")
    private Integer batteryCapacity;

    @Column(name = "state")
    private String state;

    @ManyToMany
    @JoinTable(
            name = "drone_medication",
            joinColumns = @JoinColumn(name = "drone_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id"))
    Set<Medication> medicationSet;

}
