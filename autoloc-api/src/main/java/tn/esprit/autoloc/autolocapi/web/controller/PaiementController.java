package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.service.IPaiementService;

@RestController @RequestMapping("/paiements") @RequiredArgsConstructor
@Tag(name = "Paiements", description = "Consultation des paiements liés aux contrats")
public class PaiementController {
  private final IPaiementService paiementService;
  @GetMapping @Operation(summary = "Lister les paiements") @ApiResponse(responseCode = "200", description = "Liste des paiements")
  public List<Paiement> findAll() { return paiementService.getAllPaiements(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter un paiement") @ApiResponses({@ApiResponse(responseCode = "200", description = "Paiement trouvé"), @ApiResponse(responseCode = "404", description = "Paiement introuvable")})
  public Paiement findById(@PathVariable Long id) { return paiementService.getPaiementById(id); }
}