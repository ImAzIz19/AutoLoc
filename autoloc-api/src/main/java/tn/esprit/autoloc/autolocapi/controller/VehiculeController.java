package tn.esprit.autoloc.autolocapi.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.service.IVehiculeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

  private final IVehiculeService vehiculeService;

  @PostMapping
  public Vehicule create(@RequestBody Vehicule vehicule) {
    return vehiculeService.saveVehicule(vehicule);
  }

  @GetMapping("/{id}")
  public Optional<Vehicule> getById(@PathVariable Long id) {
    return vehiculeService.getVehiculeById(id);
  }

  @GetMapping
  public List<Vehicule> getAll() {
    return vehiculeService.getAllVehicules();
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    vehiculeService.deleteVehicule(id);
  }
}
