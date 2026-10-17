package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.domain.Equipement;
import java.util.List;

public interface IEquipementService {
  Equipement saveEquipement(Equipement equipement);
  Equipement getEquipementById(Long id);
  List<Equipement> getAllEquipements();
  void deleteEquipement(Long id);
}
