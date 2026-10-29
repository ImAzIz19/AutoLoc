package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.service.IVehiculeService;

@RestController @RequestMapping("/vehicules") @RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion des véhicules")
public class VehiculeController {
  private final IVehiculeService vehiculeService;
  @GetMapping @Operation(summary = "Lister les véhicules") @ApiResponse(responseCode = "200", description = "Liste des véhicules")
  public List<Vehicule> findAll() { return vehiculeService.getAllVehicules(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter un véhicule") @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule trouvé"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
  public Vehicule findById(@PathVariable Long id) { return vehiculeService.getVehiculeById(id); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un véhicule") @ApiResponses({@ApiResponse(responseCode = "201", description = "Véhicule créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public Vehicule create(@RequestBody Vehicule vehicule) { return vehiculeService.saveVehicule(vehicule); }
  @PutMapping("/{id}") @Operation(summary = "Modifier un véhicule") @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule modifié"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public Vehicule update(@PathVariable Long id, @RequestBody Vehicule vehicule) { return vehiculeService.updateVehicule(id, vehicule); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un véhicule") @ApiResponses({@ApiResponse(responseCode = "204", description = "Véhicule supprimé"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
  public void delete(@PathVariable Long id) { vehiculeService.deleteVehicule(id); }
}