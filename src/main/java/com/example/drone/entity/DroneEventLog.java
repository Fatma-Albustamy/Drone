package com.example.drone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "drone_event_log")
@Setter
@Getter
public class DroneEventLog extends CommonEntity {

    @Column(name = "drone_id")
    private Long droneId;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "battery_capacity")
    private Integer batteryCapacity;
}


