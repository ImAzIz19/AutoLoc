package tn.esprit.autoloc.autolocapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.service.IPaiementService;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

  private final IPaiementService paiementService;

  @GetMapping("/{id}")
  public Paiement getById(@PathVariable Long id) {
    return paiementService.getPaiementById(id);
  }

  @GetMapping
  public List<Paiement> getAll() {
    return paiementService.getAllPaiements();
  }
}
