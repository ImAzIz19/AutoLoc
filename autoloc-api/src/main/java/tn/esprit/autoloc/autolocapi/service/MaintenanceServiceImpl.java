package tn.esprit.autoloc.autolocapi.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IMaintenanceRepository;
import tn.esprit.autoloc.autolocapi.service.IMaintenanceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

  private final IMaintenanceRepository maintenanceRepository;

  @Override
  public Maintenance saveMaintenance(Maintenance maintenance) {
    validate(maintenance);
    return maintenanceRepository.save(maintenance);
  }

  @Override
  public Maintenance updateMaintenance(Long id, Maintenance maintenance) {
    Maintenance existing = getMaintenanceById(id);
    validate(maintenance);
    existing.setDateDebut(maintenance.getDateDebut());
    existing.setDateFin(maintenance.getDateFin());
    existing.setDescription(maintenance.getDescription());
    existing.setVehicule(maintenance.getVehicule());
    return maintenanceRepository.save(existing);
  }

  private void validate(Maintenance maintenance) {
    if (maintenance.getDateDebut() == null) {
      throw new IllegalArgumentException("La date de début est obligatoire.");
    }
    if (maintenance.getDateFin() != null && maintenance.getDateFin().isBefore(maintenance.getDateDebut())) {
      throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début.");
    }
  }

  @Override
  public Maintenance getMaintenanceById(Long id) {
    return maintenanceRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
  }

  @Override
  public List<Maintenance> getAllMaintenances() {
    return maintenanceRepository.findAll();
  }

  @Override
  public void deleteMaintenance(Long id) {
    if (!maintenanceRepository.existsById(id)) {
      throw new ResourceNotFoundException("Maintenance", id);
    }
    maintenanceRepository.deleteById(id);
  }
}
