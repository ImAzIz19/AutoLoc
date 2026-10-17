package tn.esprit.autoloc.autolocapi.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.service.IEquipementService;

import java.util.List;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
public class EquipementController {

  private final IEquipementService equipementService;

  @PostMapping
  public Equipement create(@RequestBody Equipement equipement) {
    return equipementService.saveEquipement(equipement);
  }

  @GetMapping("/{id}")
  public Equipement getById(@PathVariable Long id) {
    return equipementService.getEquipementById(id);
  }

  @GetMapping
  public List<Equipement> getAll() {
    return equipementService.getAllEquipements();
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    equipementService.deleteEquipement(id);
  }
}
