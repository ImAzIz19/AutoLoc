package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.service.IEquipementService;

@RestController @RequestMapping("/equipements") @RequiredArgsConstructor
@Tag(name = "Équipements", description = "Gestion des équipements")
public class EquipementController {
  private final IEquipementService equipementService;
  @GetMapping @Operation(summary = "Lister les équipements") @ApiResponse(responseCode = "200", description = "Liste des équipements")
  public List<Equipement> findAll() { return equipementService.getAllEquipements(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter un équipement") @ApiResponses({@ApiResponse(responseCode = "200", description = "Équipement trouvé"), @ApiResponse(responseCode = "404", description = "Équipement introuvable")})
  public Equipement findById(@PathVariable Long id) { return equipementService.getEquipementById(id); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un équipement") @ApiResponses({@ApiResponse(responseCode = "201", description = "Équipement créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public Equipement create(@RequestBody Equipement equipement) { return equipementService.saveEquipement(equipement); }
  @PutMapping("/{id}") @Operation(summary = "Modifier un équipement") @ApiResponses({@ApiResponse(responseCode = "200", description = "Équipement modifié"), @ApiResponse(responseCode = "404", description = "Équipement introuvable"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public Equipement update(@PathVariable Long id, @RequestBody Equipement equipement) { return equipementService.updateEquipement(id, equipement); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un équipement") @ApiResponses({@ApiResponse(responseCode = "204", description = "Équipement supprimé"), @ApiResponse(responseCode = "404", description = "Équipement introuvable")})
  public void delete(@PathVariable Long id) { equipementService.deleteEquipement(id); }
}