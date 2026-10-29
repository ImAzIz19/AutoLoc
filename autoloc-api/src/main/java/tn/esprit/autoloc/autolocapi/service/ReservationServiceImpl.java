package tn.esprit.autoloc.autolocapi.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

  private final IReservationRepository reservationRepository;

  @Override
  public Reservation saveReservation(Reservation reservation) {
    validate(reservation);
    return reservationRepository.save(reservation);
  }

  @Override
  public Reservation updateReservation(Long id, Reservation reservation) {
    Reservation existing = getReservationById(id);
    validate(reservation);
    existing.setDateDebut(reservation.getDateDebut());
    existing.setDateFin(reservation.getDateFin());
    existing.setStatut(reservation.getStatut());
    existing.setVehicule(reservation.getVehicule());
    existing.setClient(reservation.getClient());
    return reservationRepository.save(existing);
  }

  private void validate(Reservation reservation) {
    if (reservation.getDateDebut() == null || reservation.getDateFin() == null) {
      throw new IllegalArgumentException("Les dates de début et de fin sont obligatoires.");
    }
    if (reservation.getDateFin().isBefore(reservation.getDateDebut())) {
      throw new IllegalArgumentException("La date de fin de la réservation ne peut pas être antérieure à la date de début.");
    }
  }

  @Override
  public Reservation getReservationById(Long id) {
    return reservationRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
  }

  @Override
  public List<Reservation> getAllReservations() {
    return reservationRepository.findAll();
  }

  @Override
  public void deleteReservation(Long id) {
    if (!reservationRepository.existsById(id)) {
      throw new ResourceNotFoundException("Reservation", id);
    }
    reservationRepository.deleteById(id);
  }
}
