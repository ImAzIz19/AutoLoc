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
    validate(agence);
    return agenceRepository.save(agence);
  }

  @Override
  public Agence updateAgence(Long id, Agence agence) {
    Agence existing = getAgenceById(id);
    validate(agence);
    existing.setNom(agence.getNom());
    existing.setVille(agence.getVille());
    existing.setAdresse(agence.getAdresse());
    existing.setTelephone(agence.getTelephone());
    return agenceRepository.save(existing);
  }

  private void validate(Agence agence) {
    if (agence.getNom() == null || agence.getNom().isBlank()
      || agence.getVille() == null || agence.getVille().isBlank()
      || agence.getAdresse() == null || agence.getAdresse().isBlank()
      || agence.getTelephone() == null || agence.getTelephone().isBlank()) {
      throw new IllegalArgumentException("Le nom, la ville, l'adresse et le téléphone sont obligatoires.");
    }
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
