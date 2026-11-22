package tn.esprit.autoloc.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;
import tn.esprit.autoloc.autolocapi.repository.IMaintenanceRepository;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.web.dto.VehiculeDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

  private final IVehiculeRepository vehiculeRepository;
  private final IEquipementRepository equipementRepository;
  private final IMaintenanceRepository maintenanceRepository;

  @Override
  public VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
    Vehicule vehicule = getVehiculeById(idVehicule);
    Equipement equipement = equipementRepository.findById(idEquipement)
      .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
    if (vehicule.getEquipements() == null) vehicule.setEquipements(new ArrayList<>());
    if (!vehicule.getEquipements().contains(equipement)
      && vehicule.getEquipements().stream().noneMatch(e -> e.getIdEquipement().equals(idEquipement))) {
      vehicule.getEquipements().add(equipement);
    }
    return toDTO(vehiculeRepository.save(vehicule));
  }

  @Override
  public VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation) {
    if (description == null || description.isBlank()) throw new IllegalArgumentException("La description de maintenance est obligatoire.");
    Vehicule vehicule = vehiculeRepository.findByImmatriculation(immatriculation)
      .orElseThrow(() -> new ResourceNotFoundException("Vehicule"));

    Maintenance maintenance = new Maintenance();
    maintenance.setDescription(description);
    maintenance.setDateDebut(LocalDate.now());
    maintenance.setVehicule(vehicule);
    maintenanceRepository.save(maintenance);

    vehicule.setStatut(tn.esprit.autoloc.autolocapi.domain.StatutVehicule.MAINTENANCE);
    return toDTO(vehiculeRepository.save(vehicule));
  }

  private VehiculeDTO toDTO(Vehicule vehicule) {
    List<Long> equipementIds = vehicule.getEquipements() == null ? List.of()
      : vehicule.getEquipements().stream().map(Equipement::getIdEquipement).toList();
    return new VehiculeDTO(
      vehicule.getIdVehicule(),
      vehicule.getImmatriculation(),
      vehicule.getMarque(),
      vehicule.getModele(),
      vehicule.getCategorie(),
      vehicule.getTarifJournalier(),
      vehicule.getStatut(),
      vehicule.getAgence() != null ? vehicule.getAgence().getIdAgence() : null,
      equipementIds
    );
  }

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
