package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.service.IMaintenanceService;
import tn.esprit.autoloc.autolocapi.web.dto.MaintenanceDto;
import tn.esprit.autoloc.autolocapi.web.mapper.MaintenanceMapper;

@RestController @RequestMapping("/maintenances") @RequiredArgsConstructor
@Tag(name = "Maintenances", description = "Gestion des maintenances")
public class MaintenanceController {
  private final IMaintenanceService maintenanceService;
  private final MaintenanceMapper maintenanceMapper;
  @GetMapping @Operation(summary = "Lister les maintenances") @ApiResponse(responseCode = "200", description = "Liste des maintenances")
  public List<MaintenanceDto> findAll() { return maintenanceService.getAllMaintenances().stream().map(maintenanceMapper::toDto).toList(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter une maintenance") @ApiResponses({@ApiResponse(responseCode = "200", description = "Maintenance trouvée"), @ApiResponse(responseCode = "404", description = "Maintenance introuvable")})
  public MaintenanceDto findById(@PathVariable Long id) { return maintenanceMapper.toDto(maintenanceService.getMaintenanceById(id)); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer une maintenance") @ApiResponses({@ApiResponse(responseCode = "201", description = "Maintenance créée"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public MaintenanceDto create(@Valid @RequestBody MaintenanceDto dto) { return maintenanceMapper.toDto(maintenanceService.saveMaintenance(maintenanceMapper.toEntity(dto))); }
  @PutMapping("/{id}") @Operation(summary = "Modifier une maintenance") @ApiResponses({@ApiResponse(responseCode = "200", description = "Maintenance modifiée"), @ApiResponse(responseCode = "404", description = "Maintenance introuvable"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public MaintenanceDto update(@PathVariable Long id, @Valid @RequestBody MaintenanceDto dto) { return maintenanceMapper.toDto(maintenanceService.updateMaintenance(id, maintenanceMapper.toEntity(dto))); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer une maintenance") @ApiResponses({@ApiResponse(responseCode = "204", description = "Maintenance supprimée"), @ApiResponse(responseCode = "404", description = "Maintenance introuvable")})
  public void delete(@PathVariable Long id) { maintenanceService.deleteMaintenance(id); }
}