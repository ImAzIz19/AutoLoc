package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import java.util.List;

public interface IVehiculeService {
  Vehicule saveVehicule(Vehicule vehicule);
  Vehicule updateVehicule(Long id, Vehicule vehicule);
  Vehicule getVehiculeById(Long id);
  List<Vehicule> getAllVehicules();
  void deleteVehicule(Long id);
}
