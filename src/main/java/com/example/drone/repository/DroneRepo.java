package com.example.drone.repository;

import com.example.drone.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepo extends JpaRepository<Drone,Long> {

    List<Drone> findAllByStateEquals(String state);

    List<Drone> findAllByDeletedFalse();


}
