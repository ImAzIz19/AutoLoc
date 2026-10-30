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
import tn.esprit.autoloc.autolocapi.service.IAgenceService;
import tn.esprit.autoloc.autolocapi.web.dto.AgenceDto;
import tn.esprit.autoloc.autolocapi.web.mapper.AgenceMapper;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences")
public class AgenceController {
  private final IAgenceService agenceService;
  private final AgenceMapper agenceMapper;
  @GetMapping @Operation(summary = "Lister les agences") @ApiResponse(responseCode = "200", description = "Liste des agences")
  public List<AgenceDto> findAll() { return agenceService.getAllAgences().stream().map(agenceMapper::toDto).toList(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter une agence") @ApiResponses({@ApiResponse(responseCode = "200", description = "Agence trouvée"), @ApiResponse(responseCode = "404", description = "Agence introuvable")})
  public AgenceDto findById(@PathVariable Long id) { return agenceMapper.toDto(agenceService.getAgenceById(id)); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer une agence") @ApiResponses({@ApiResponse(responseCode = "201", description = "Agence créée"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public AgenceDto create(@Valid @RequestBody AgenceDto dto) { return agenceMapper.toDto(agenceService.saveAgence(agenceMapper.toEntity(dto))); }
  @PutMapping("/{id}") @Operation(summary = "Modifier une agence") @ApiResponses({@ApiResponse(responseCode = "200", description = "Agence modifiée"), @ApiResponse(responseCode = "404", description = "Agence introuvable"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public AgenceDto update(@PathVariable Long id, @Valid @RequestBody AgenceDto dto) { return agenceMapper.toDto(agenceService.updateAgence(id, agenceMapper.toEntity(dto))); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer une agence") @ApiResponses({@ApiResponse(responseCode = "204", description = "Agence supprimée"), @ApiResponse(responseCode = "404", description = "Agence introuvable")})
  public void delete(@PathVariable Long id) { agenceService.deleteAgence(id); }
}