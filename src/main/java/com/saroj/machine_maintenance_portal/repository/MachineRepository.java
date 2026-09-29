package com.saroj.machine_maintenance_portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saroj.machine_maintenance_portal.model.Machine;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    List<Machine> findByMachineNameContainingIgnoreCase(String machineName);
}