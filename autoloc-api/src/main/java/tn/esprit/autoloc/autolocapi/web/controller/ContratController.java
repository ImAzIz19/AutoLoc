package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.service.IContratService;
import tn.esprit.autoloc.autolocapi.web.dto.ContratDto;
import tn.esprit.autoloc.autolocapi.web.mapper.ContratMapper;

@RestController @RequestMapping("/contrats") @RequiredArgsConstructor
@Tag(name = "Contrats", description = "Gestion des contrats de location")
public class ContratController {
  private final IContratService contratService;
  private final ContratMapper contratMapper;
  @GetMapping @Operation(summary = "Lister les contrats") @ApiResponse(responseCode = "200", description = "Liste des contrats")
  public List<ContratDto> findAll() { return contratService.findAll().stream().map(contratMapper::toDto).toList(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter un contrat") @ApiResponses({@ApiResponse(responseCode = "200", description = "Contrat trouvé"), @ApiResponse(responseCode = "404", description = "Contrat introuvable")})
  public ContratDto findById(@Parameter(description = "Identifiant du contrat") @PathVariable Long id) { return contratMapper.toDto(contratService.findById(id)); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un contrat") @ApiResponses({@ApiResponse(responseCode = "201", description = "Contrat créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public ContratDto create(@Valid @RequestBody ContratDto dto) { return contratMapper.toDto(contratService.create(contratMapper.toEntity(dto))); }
  @PutMapping("/{id}") @Operation(summary = "Modifier un contrat") @ApiResponses({@ApiResponse(responseCode = "200", description = "Contrat modifié"), @ApiResponse(responseCode = "404", description = "Contrat introuvable"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public ContratDto update(@PathVariable Long id, @Valid @RequestBody ContratDto dto) { return contratMapper.toDto(contratService.update(id, contratMapper.toEntity(dto))); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un contrat") @ApiResponses({@ApiResponse(responseCode = "204", description = "Contrat supprimé"), @ApiResponse(responseCode = "404", description = "Contrat introuvable")})
  public void delete(@PathVariable Long id) { contratService.deleteById(id); }
}