package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.domain.Agence;
import java.util.List;
import java.util.Optional;

public interface IAgenceService {
  Agence saveAgence(Agence agence);
  Agence updateAgence(Long id, Agence agence);
  Agence getAgenceById(Long id);
  List<Agence> getAllAgences();
  void deleteAgence(Long id);
}
