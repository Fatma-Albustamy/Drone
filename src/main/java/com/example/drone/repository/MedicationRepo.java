package com.example.drone.repository;

import com.example.drone.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepo extends JpaRepository<Medication,Long> {
}
