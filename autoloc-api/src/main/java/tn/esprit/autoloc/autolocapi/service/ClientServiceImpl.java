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
    if (client.getNom() == null || client.getEmail() == null) {
      throw new IllegalArgumentException("Le nom et l'e-mail du client sont obligatoires.");
    }
    return clientRepository.save(client);
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
