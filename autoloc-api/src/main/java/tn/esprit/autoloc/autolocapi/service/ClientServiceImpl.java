package tn.esprit.autoloc.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

  private final IClientRepository clientRepository;

  @Override
  public Client saveClient(Client client) {
    validate(client);
    return clientRepository.save(client);
  }

  @Override
  public Client updateClient(Long id, Client client) {
    Client existing = getClientById(id);
    validate(client);
    existing.setNom(client.getNom());
    existing.setPrenom(client.getPrenom());
    existing.setEmail(client.getEmail());
    existing.setTelephone(client.getTelephone());
    existing.setNumPermis(client.getNumPermis());
    existing.setDateInscription(client.getDateInscription());
    return clientRepository.save(existing);
  }

  private void validate(Client client) {
    if (client.getNom() == null || client.getNom().isBlank()
      || client.getPrenom() == null || client.getPrenom().isBlank()
      || client.getEmail() == null || client.getEmail().isBlank()
      || client.getTelephone() == null || client.getTelephone().isBlank()
      || client.getNumPermis() == null || client.getNumPermis().isBlank()) {
      throw new IllegalArgumentException("Les informations d'identification du client sont obligatoires.");
    }
  }

  @Override
  public Client getClientById(Long id) {
    return clientRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Client", id));
  }

  @Override
  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }

  @Override
  public void deleteClient(Long id) {
    if (!clientRepository.existsById(id)) {
      throw new ResourceNotFoundException("Client", id);
    }
    clientRepository.deleteById(id);
  }
}
