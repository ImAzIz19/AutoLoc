package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.domain.Reservation;
import java.util.List;

public interface IReservationService {
  Reservation saveReservation(Reservation reservation);
  Reservation getReservationById(Long id);
  List<Reservation> getAllReservations();
  void deleteReservation(Long id);
}
