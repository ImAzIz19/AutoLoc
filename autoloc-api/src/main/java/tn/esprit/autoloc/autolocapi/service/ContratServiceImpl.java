
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;
import tn.esprit.autoloc.autolocapi.web.dto.ContratDTO;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

  private final IContratRepository contratRepository;
  private final IReservationRepository reservationRepository;

  @Override
  public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
    if (dateSignature == null || dateDebut == null || dateFin == null) {
      throw new IllegalArgumentException("Les dates de signature, de début et de fin sont obligatoires.");
    }
    if (dateFin.isBefore(dateDebut)) {
      throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début.");
    }

    Reservation reservation = new Reservation();
    reservation.setDateDebut(dateDebut);
    reservation.setDateFin(dateFin);
    reservation.setStatut(StatutReservation.EN_ATTENTE);
    reservation = reservationRepository.save(reservation);

    Contrat contrat = new Contrat();
    contrat.setDateSignature(dateSignature);
    contrat.setMontantTotal(BigDecimal.ZERO);
    contrat.setValide(false);
    contrat.setReservation(reservation);
    contrat.setPaiements(new ArrayList<>());
    contrat = contratRepository.save(contrat);

    return new ContratDTO(
      contrat.getIdContrat(),
      contrat.getDateSignature(),
      contrat.getMontantTotal(),
      contrat.isValide(),
      reservation.getIdReservation(),
      List.of()
    );
  }

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
