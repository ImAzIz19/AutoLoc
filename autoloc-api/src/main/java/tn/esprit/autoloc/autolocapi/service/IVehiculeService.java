package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import java.util.List;
import java.util.Optional;

public interface IVehiculeService {
  Vehicule saveVehicule(Vehicule vehicule);
  Optional<Vehicule> getVehiculeById(Long id);
  List<Vehicule> getAllVehicules();
  void deleteVehicule(Long id);
}
