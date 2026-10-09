package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.domain.Contrat;
import java.util.List;
import java.util.Optional;

public interface IContratService {
  Contrat saveContrat(Contrat contrat);
  Optional<Contrat> getContratById(Long id);
  List<Contrat> getAllContrats();
  void deleteContrat(Long id);
}
