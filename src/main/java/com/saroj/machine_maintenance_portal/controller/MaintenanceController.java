package com.saroj.machine_maintenance_portal.controller;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.saroj.machine_maintenance_portal.model.Machine;
import com.saroj.machine_maintenance_portal.model.Maintenance;
import com.saroj.machine_maintenance_portal.repository.MachineRepository;
import com.saroj.machine_maintenance_portal.repository.MaintenanceRepository;

@Controller
public class MaintenanceController {

    private final MaintenanceRepository maintenanceRepository;
    private final MachineRepository machineRepository;

   public MaintenanceController(
        MaintenanceRepository maintenanceRepository,
        MachineRepository machineRepository) {

    this.maintenanceRepository = maintenanceRepository;
    this.machineRepository = machineRepository;
}

    // View all maintenance records
    @GetMapping("/maintenance")
    public String maintenance(
            @RequestParam(required = false) String search,
            Model model) {

        List<Maintenance> maintenanceList;

        if (search != null && !search.trim().isEmpty()) {

            maintenanceList =
                    maintenanceRepository
                            .findByDescriptionContainingIgnoreCase(search);

        } else {

            maintenanceList =
                    maintenanceRepository.findAll();
        }

        model.addAttribute("maintenanceList", maintenanceList);
        model.addAttribute("search", search);

        return "maintenance";
    }


    // Open Add Maintenance form
   @GetMapping("/maintenance/add")
public String addMaintenanceForm(Model model) {

    model.addAttribute("maintenance", new Maintenance());

    List<Machine> machines = machineRepository.findAll();

    model.addAttribute("machines", machines);

    return "add-maintenance";
}


    // Save maintenance record
    @PostMapping("/maintenance/save")
    public String saveMaintenance(
            @ModelAttribute Maintenance maintenance) {

        if (maintenance.getStatus() == null ||
                maintenance.getStatus().isEmpty()) {

            maintenance.setStatus("PENDING");
        }

        if (maintenance.getCreatedDate() == null) {

            maintenance.setCreatedDate(LocalDateTime.now());
        }

        maintenance.setUpdatedDate(LocalDateTime.now());

        maintenanceRepository.save(maintenance);

        return "redirect:/maintenance";
    }


    // Open Edit Maintenance form
    @GetMapping("/maintenance/edit/{id}")
    public String editMaintenance(
            @PathVariable Long id,
            Model model) {

        Maintenance maintenance =
                maintenanceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Maintenance not found"));

        model.addAttribute("maintenance", maintenance);

        return "edit-maintenance";
    }


    // Update Maintenance
    @PostMapping("/maintenance/update")
    public String updateMaintenance(
            @ModelAttribute Maintenance maintenance) {

        maintenance.setUpdatedDate(LocalDateTime.now());

        maintenanceRepository.save(maintenance);

        return "redirect:/maintenance";
    }
}