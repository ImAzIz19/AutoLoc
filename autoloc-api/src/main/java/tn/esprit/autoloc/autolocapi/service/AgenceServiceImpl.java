package tn.esprit.autoloc.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

  private final IAgenceRepository agenceRepository;

  @Override
  public Agence saveAgence(Agence agence) {
    if (agence.getNom() == null || agence.getNom().isEmpty()) {
      throw new IllegalArgumentException("Le nom de l'agence est obligatoire.");
    }
    return agenceRepository.save(agence);
  }

  @Override
  public Agence getAgenceById(Long id) {
    return agenceRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
  }

  @Override
  public List<Agence> getAllAgences() {
    return agenceRepository.findAll();
  }

  @Override
  public void deleteAgence(Long id) {
    if (!agenceRepository.existsById(id)) {
      throw new ResourceNotFoundException("Agence", id);
    }
    agenceRepository.deleteById(id);
  }
}
