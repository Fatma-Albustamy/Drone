package com.example.drone.repository;

import com.example.drone.entity.DroneEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneEventLogRepo extends JpaRepository<DroneEventLog,Long> {
}
