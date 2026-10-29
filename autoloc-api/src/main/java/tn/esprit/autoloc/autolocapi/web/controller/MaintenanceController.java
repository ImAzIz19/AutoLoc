package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.service.IMaintenanceService;

@RestController @RequestMapping("/maintenances") @RequiredArgsConstructor
@Tag(name = "Maintenances", description = "Gestion des maintenances")
public class MaintenanceController {
  private final IMaintenanceService maintenanceService;
  @GetMapping @Operation(summary = "Lister les maintenances") @ApiResponse(responseCode = "200", description = "Liste des maintenances")
  public List<Maintenance> findAll() { return maintenanceService.getAllMaintenances(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter une maintenance") @ApiResponses({@ApiResponse(responseCode = "200", description = "Maintenance trouvée"), @ApiResponse(responseCode = "404", description = "Maintenance introuvable")})
  public Maintenance findById(@PathVariable Long id) { return maintenanceService.getMaintenanceById(id); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer une maintenance") @ApiResponses({@ApiResponse(responseCode = "201", description = "Maintenance créée"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public Maintenance create(@RequestBody Maintenance maintenance) { return maintenanceService.saveMaintenance(maintenance); }
  @PutMapping("/{id}") @Operation(summary = "Modifier une maintenance") @ApiResponses({@ApiResponse(responseCode = "200", description = "Maintenance modifiée"), @ApiResponse(responseCode = "404", description = "Maintenance introuvable"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public Maintenance update(@PathVariable Long id, @RequestBody Maintenance maintenance) { return maintenanceService.updateMaintenance(id, maintenance); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer une maintenance") @ApiResponses({@ApiResponse(responseCode = "204", description = "Maintenance supprimée"), @ApiResponse(responseCode = "404", description = "Maintenance introuvable")})
  public void delete(@PathVariable Long id) { maintenanceService.deleteMaintenance(id); }
}