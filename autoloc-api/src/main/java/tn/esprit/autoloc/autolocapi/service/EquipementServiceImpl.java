package tn.esprit.autoloc.autolocapi.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

  private final IEquipementRepository equipementRepository;

  @Override
  public Equipement saveEquipement(Equipement equipement) {
    if (equipement.getLibelle() == null || equipement.getLibelle().isEmpty()) {
      throw new IllegalArgumentException("Le libellé de l'équipement est obligatoire.");
    }
    return equipementRepository.save(equipement);
  }

  @Override
  public Equipement getEquipementById(Long id) {
    return equipementRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
  }

  @Override
  public List<Equipement> getAllEquipements() {
    return equipementRepository.findAll();
  }

  @Override
  public void deleteEquipement(Long id) {
    if (!equipementRepository.existsById(id)) {
      throw new ResourceNotFoundException("Equipement", id);
    }
    equipementRepository.deleteById(id);
  }
}
