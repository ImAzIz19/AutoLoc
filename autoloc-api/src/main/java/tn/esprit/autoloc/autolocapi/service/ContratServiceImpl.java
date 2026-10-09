package tn.esprit.autoloc.autolocapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContratServiceImpl implements IContratService {

  @Autowired
  private IContratRepository contratRepository;

  @Override
  public Contrat saveContrat(Contrat contrat) {
    return contratRepository.save(contrat);
  }

  @Override
  public Optional<Contrat> getContratById(Long id) {
    return contratRepository.findById(id);
  }

  @Override
  public List<Contrat> getAllContrats() {
    return (List<Contrat>) contratRepository.findAll();
  }

  @Override
  public void deleteContrat(Long id) {
    contratRepository.deleteById(id);
  }
}
