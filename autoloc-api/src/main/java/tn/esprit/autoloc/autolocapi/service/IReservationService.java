package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationWithContratRequestDTO;
import java.util.List;

public interface IReservationService {
  Reservation saveReservation(Reservation reservation);
  Reservation updateReservation(Long id, Reservation reservation);
  Reservation getReservationById(Long id);
  List<Reservation> getAllReservations();
  void deleteReservation(Long id);
  ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom);
  ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO);
}
