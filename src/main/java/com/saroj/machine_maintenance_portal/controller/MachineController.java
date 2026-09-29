package com.saroj.machine_maintenance_portal.controller;

import com.saroj.machine_maintenance_portal.model.Machine;
import com.saroj.machine_maintenance_portal.repository.MachineRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MachineController {

    private final MachineRepository machineRepository;

    public MachineController(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @GetMapping("/machines")
    public String machines(
            @RequestParam(required = false) String search,
            Model model) {

        List<Machine> machines;

        if (search != null && !search.trim().isEmpty()) {
            machines = machineRepository
                    .findByMachineNameContainingIgnoreCase(search);
        } else {
            machines = machineRepository.findAll();
        }

        model.addAttribute("machines", machines);
        model.addAttribute("search", search);

        return "machines";
    }

    @GetMapping("/machines/add")
    public String addMachineForm(Model model) {

        model.addAttribute("machine", new Machine());

        return "add-machine";
    }

    @PostMapping("/machines/save")
    public String saveMachine(@ModelAttribute Machine machine) {

        if (machine.getStatus() == null ||
                machine.getStatus().isEmpty()) {

            machine.setStatus("ACTIVE");
        }

        machineRepository.save(machine);

        return "redirect:/machines";
    }
}
