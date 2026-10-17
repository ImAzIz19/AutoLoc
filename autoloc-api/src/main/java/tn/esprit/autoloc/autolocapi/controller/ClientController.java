package tn.esprit.autoloc.autolocapi.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.service.IClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

  private final IClientService clientService;

  @PostMapping
  public Client create(@RequestBody Client client) {
    return clientService.saveClient(client);
  }

  @GetMapping("/{id}")
  public Client getById(@PathVariable Long id) {
    return clientService.getClientById(id);
  }

  @GetMapping
  public List<Client> getAll() {
    return clientService.getAllClients();
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    clientService.deleteClient(id);
  }
}
