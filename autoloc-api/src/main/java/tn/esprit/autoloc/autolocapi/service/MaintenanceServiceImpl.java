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
    if (maintenance.getDateFin() != null && maintenance.getDateDebut() != null
      && maintenance.getDateFin().isBefore(maintenance.getDateDebut())) {
      throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début.");
    }
    return maintenanceRepository.save(maintenance);
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
