package tn.esprit.autoloc.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

  private final IVehiculeRepository vehiculeRepository;

  @Override
  public Vehicule saveVehicule(Vehicule vehicule) {
    validate(vehicule);
    return vehiculeRepository.save(vehicule);
  }

  @Override
  public Vehicule updateVehicule(Long id, Vehicule vehicule) {
    Vehicule existing = getVehiculeById(id);
    validate(vehicule);
    existing.setImmatriculation(vehicule.getImmatriculation());
    existing.setMarque(vehicule.getMarque());
    existing.setModele(vehicule.getModele());
    existing.setCategorie(vehicule.getCategorie());
    existing.setTarifJournalier(vehicule.getTarifJournalier());
    existing.setStatut(vehicule.getStatut());
    existing.setAgence(vehicule.getAgence());
    existing.setEquipements(vehicule.getEquipements());
    return vehiculeRepository.save(existing);
  }

  @Override
  public Vehicule getVehiculeById(Long id) {
    return vehiculeRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
  }

  private void validate(Vehicule vehicule) {
    if (vehicule.getTarifJournalier() != null && vehicule.getTarifJournalier().signum() < 0) {
      throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif.");
    }
    if (vehicule.getImmatriculation() == null || vehicule.getImmatriculation().isBlank()
      || vehicule.getMarque() == null || vehicule.getMarque().isBlank()
      || vehicule.getModele() == null || vehicule.getModele().isBlank()) {
      throw new IllegalArgumentException("L'immatriculation, la marque et le modèle sont obligatoires.");
    }
  }

  @Override
  public List<Vehicule> getAllVehicules() {
    return vehiculeRepository.findAll();
  }

  @Override
  public void deleteVehicule(Long id) {
    vehiculeRepository.delete(getVehiculeById(id));
  }
}
