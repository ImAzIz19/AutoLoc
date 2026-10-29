package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.domain.Client;
import java.util.List;
import java.util.Optional;

public interface IClientService {
  Client saveClient(Client client);
  Client updateClient(Long id, Client client);
  Client getClientById(Long id);
  List<Client> getAllClients();
  void deleteClient(Long id);
}
