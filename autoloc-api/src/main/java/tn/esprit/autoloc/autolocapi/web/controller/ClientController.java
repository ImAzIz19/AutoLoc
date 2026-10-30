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
import tn.esprit.autoloc.autolocapi.service.IClientService;
import tn.esprit.autoloc.autolocapi.web.dto.ClientDto;
import tn.esprit.autoloc.autolocapi.web.mapper.ClientMapper;

@RestController @RequestMapping("/clients") @RequiredArgsConstructor
@Tag(name = "Clients", description = "Gestion des clients")
public class ClientController {
  private final IClientService clientService;
  private final ClientMapper clientMapper;
  @GetMapping @Operation(summary = "Lister les clients") @ApiResponse(responseCode = "200", description = "Liste des clients")
  public List<ClientDto> findAll() { return clientService.getAllClients().stream().map(clientMapper::toDto).toList(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter un client") @ApiResponses({@ApiResponse(responseCode = "200", description = "Client trouvé"), @ApiResponse(responseCode = "404", description = "Client introuvable")})
  public ClientDto findById(@PathVariable Long id) { return clientMapper.toDto(clientService.getClientById(id)); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un client") @ApiResponses({@ApiResponse(responseCode = "201", description = "Client créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public ClientDto create(@Valid @RequestBody ClientDto dto) { return clientMapper.toDto(clientService.saveClient(clientMapper.toEntity(dto))); }
  @PutMapping("/{id}") @Operation(summary = "Modifier un client") @ApiResponses({@ApiResponse(responseCode = "200", description = "Client modifié"), @ApiResponse(responseCode = "404", description = "Client introuvable"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public ClientDto update(@PathVariable Long id, @Valid @RequestBody ClientDto dto) { return clientMapper.toDto(clientService.updateClient(id, clientMapper.toEntity(dto))); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un client") @ApiResponses({@ApiResponse(responseCode = "204", description = "Client supprimé"), @ApiResponse(responseCode = "404", description = "Client introuvable")})
  public void delete(@PathVariable Long id) { clientService.deleteClient(id); }
}