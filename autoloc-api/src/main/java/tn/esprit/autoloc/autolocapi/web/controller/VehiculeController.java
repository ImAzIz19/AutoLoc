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
import tn.esprit.autoloc.autolocapi.service.IVehiculeService;
import tn.esprit.autoloc.autolocapi.web.dto.VehiculeDto;
import tn.esprit.autoloc.autolocapi.web.mapper.VehiculeMapper;

@RestController @RequestMapping("/vehicules") @RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion des véhicules")
public class VehiculeController {
  private final IVehiculeService vehiculeService;
  private final VehiculeMapper vehiculeMapper;
  @GetMapping @Operation(summary = "Lister les véhicules") @ApiResponse(responseCode = "200", description = "Liste des véhicules")
  public List<VehiculeDto> findAll() { return vehiculeService.getAllVehicules().stream().map(vehiculeMapper::toDto).toList(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter un véhicule") @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule trouvé"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
  public VehiculeDto findById(@PathVariable Long id) { return vehiculeMapper.toDto(vehiculeService.getVehiculeById(id)); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un véhicule") @ApiResponses({@ApiResponse(responseCode = "201", description = "Véhicule créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public VehiculeDto create(@Valid @RequestBody VehiculeDto dto) { return vehiculeMapper.toDto(vehiculeService.saveVehicule(vehiculeMapper.toEntity(dto))); }
  @PutMapping("/{id}") @Operation(summary = "Modifier un véhicule") @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule modifié"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public VehiculeDto update(@PathVariable Long id, @Valid @RequestBody VehiculeDto dto) { return vehiculeMapper.toDto(vehiculeService.updateVehicule(id, vehiculeMapper.toEntity(dto))); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un véhicule") @ApiResponses({@ApiResponse(responseCode = "204", description = "Véhicule supprimé"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
  public void delete(@PathVariable Long id) { vehiculeService.deleteVehicule(id); }
}