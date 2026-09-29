package com.saroj.machine_maintenance_portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saroj.machine_maintenance_portal.model.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findByDescriptionContainingIgnoreCase(String description);
}