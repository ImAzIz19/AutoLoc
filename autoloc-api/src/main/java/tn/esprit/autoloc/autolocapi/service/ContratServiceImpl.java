
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

  private final IContratRepository contratRepository;

  @Override
  public Contrat create(Contrat contrat) {
    if (contrat.getIdContrat() != null) {
      throw new IllegalArgumentException(
        "Un nouveau contrat ne doit pas avoir d'identifiant"
      );
    }
    if (contrat.getDateSignature() == null || contrat.getMontantTotal() == null) {
      throw new IllegalArgumentException("La date de signature et le montant total sont obligatoires.");
    }
    return contratRepository.save(contrat);
  }

  @Override
  public Contrat findById(Long id) {
    return contratRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
  }

  @Override
  public List<Contrat> findAll() {
    return contratRepository.findAll();
  }

  @Override
  public Contrat update(Long id, Contrat contrat) {
    Contrat existant = findById(id);

    existant.setDateSignature(contrat.getDateSignature());
    existant.setMontantTotal(contrat.getMontantTotal());
    existant.setValide(contrat.isValide());
    existant.setReservation(contrat.getReservation());

    return contratRepository.save(existant);
  }

  @Override
  public void deleteById(Long id) {
    Contrat contrat = findById(id);
    contratRepository.delete(contrat);
  }
}
