package com.example.libraryManagement.repository;

import com.example.libraryManagement.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepo extends JpaRepository<Patron,Long> {
}