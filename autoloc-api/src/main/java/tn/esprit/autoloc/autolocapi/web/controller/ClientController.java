package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.service.IClientService;

@RestController @RequestMapping("/clients") @RequiredArgsConstructor
@Tag(name = "Clients", description = "Gestion des clients")
public class ClientController {
  private final IClientService clientService;
  @GetMapping @Operation(summary = "Lister les clients") @ApiResponse(responseCode = "200", description = "Liste des clients")
  public List<Client> findAll() { return clientService.getAllClients(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter un client") @ApiResponses({@ApiResponse(responseCode = "200", description = "Client trouvé"), @ApiResponse(responseCode = "404", description = "Client introuvable")})
  public Client findById(@PathVariable Long id) { return clientService.getClientById(id); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un client") @ApiResponses({@ApiResponse(responseCode = "201", description = "Client créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public Client create(@RequestBody Client client) { return clientService.saveClient(client); }
  @PutMapping("/{id}") @Operation(summary = "Modifier un client") @ApiResponses({@ApiResponse(responseCode = "200", description = "Client modifié"), @ApiResponse(responseCode = "404", description = "Client introuvable"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public Client update(@PathVariable Long id, @RequestBody Client client) { return clientService.updateClient(id, client); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un client") @ApiResponses({@ApiResponse(responseCode = "204", description = "Client supprimé"), @ApiResponse(responseCode = "404", description = "Client introuvable")})
  public void delete(@PathVariable Long id) { clientService.deleteClient(id); }
}