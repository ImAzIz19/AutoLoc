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
    if (reservation.getDateFin() != null && reservation.getDateDebut() != null
      && reservation.getDateFin().isBefore(reservation.getDateDebut())) {
      throw new IllegalArgumentException("La date de fin de la réservation ne peut pas être antérieure à la date de début.");
    }
    return reservationRepository.save(reservation);
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
