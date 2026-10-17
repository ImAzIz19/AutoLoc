package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import java.util.List;

public interface IMaintenanceService {
  Maintenance saveMaintenance(Maintenance maintenance);
  Maintenance getMaintenanceById(Long id);
  List<Maintenance> getAllMaintenances();
  void deleteMaintenance(Long id);
}
