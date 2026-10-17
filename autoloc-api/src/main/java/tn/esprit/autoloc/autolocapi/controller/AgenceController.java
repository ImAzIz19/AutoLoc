package tn.esprit.autoloc.autolocapi.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.service.IAgenceService;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

  private final IAgenceService agenceService;

  @PostMapping
  public Agence create(@RequestBody Agence agence) {
    return agenceService.saveAgence(agence);
  }

  @GetMapping("/{id}")
  public Agence getById(@PathVariable Long id) {
    return agenceService.getAgenceById(id);
  }

  @GetMapping
  public List<Agence> getAll() {
    return agenceService.getAllAgences();
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    agenceService.deleteAgence(id);
  }
}
