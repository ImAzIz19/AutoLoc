package tn.esprit.autoloc.autolocapi.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.service.IMaintenanceService;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

  private final IMaintenanceService maintenanceService;

  @PostMapping
  public Maintenance create(@RequestBody Maintenance maintenance) {
    return maintenanceService.saveMaintenance(maintenance);
  }

  @GetMapping("/{id}")
  public Maintenance getById(@PathVariable Long id) {
    return maintenanceService.getMaintenanceById(id);
  }

  @GetMapping
  public List<Maintenance> getAll() {
    return maintenanceService.getAllMaintenances();
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    maintenanceService.deleteMaintenance(id);
  }
}
