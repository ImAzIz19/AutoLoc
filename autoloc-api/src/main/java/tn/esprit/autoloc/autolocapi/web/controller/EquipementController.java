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
import tn.esprit.autoloc.autolocapi.service.IEquipementService;
import tn.esprit.autoloc.autolocapi.web.dto.EquipementDto;
import tn.esprit.autoloc.autolocapi.web.mapper.EquipementMapper;

@RestController @RequestMapping("/equipements") @RequiredArgsConstructor
@Tag(name = "Équipements", description = "Gestion des équipements")
public class EquipementController {
  private final IEquipementService equipementService;
  private final EquipementMapper equipementMapper;
  @GetMapping @Operation(summary = "Lister les équipements") @ApiResponse(responseCode = "200", description = "Liste des équipements")
  public List<EquipementDto> findAll() { return equipementService.getAllEquipements().stream().map(equipementMapper::toDto).toList(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter un équipement") @ApiResponses({@ApiResponse(responseCode = "200", description = "Équipement trouvé"), @ApiResponse(responseCode = "404", description = "Équipement introuvable")})
  public EquipementDto findById(@PathVariable Long id) { return equipementMapper.toDto(equipementService.getEquipementById(id)); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un équipement") @ApiResponses({@ApiResponse(responseCode = "201", description = "Équipement créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public EquipementDto create(@Valid @RequestBody EquipementDto dto) { return equipementMapper.toDto(equipementService.saveEquipement(equipementMapper.toEntity(dto))); }
  @PutMapping("/{id}") @Operation(summary = "Modifier un équipement") @ApiResponses({@ApiResponse(responseCode = "200", description = "Équipement modifié"), @ApiResponse(responseCode = "404", description = "Équipement introuvable"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public EquipementDto update(@PathVariable Long id, @Valid @RequestBody EquipementDto dto) { return equipementMapper.toDto(equipementService.updateEquipement(id, equipementMapper.toEntity(dto))); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un équipement") @ApiResponses({@ApiResponse(responseCode = "204", description = "Équipement supprimé"), @ApiResponse(responseCode = "404", description = "Équipement introuvable")})
  public void delete(@PathVariable Long id) { equipementService.deleteEquipement(id); }
}