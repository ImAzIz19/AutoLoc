package tn.esprit.autoloc.autolocapi.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import tn.esprit.autoloc.autolocapi.repository.IClientRepository;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationDto;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationWithContratRequestDTO;
import tn.esprit.autoloc.autolocapi.web.dto.ContratDTO;

import java.util.List;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

  private final IReservationRepository reservationRepository;
  private final IClientRepository clientRepository;
  private final IContratRepository contratRepository;

  @Override
  public ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom) {
    Reservation reservation = getReservationById(idReservation);
    Client client = clientRepository.findByNomAndPrenom(nom, prenom)
      .orElseThrow(() -> new ResourceNotFoundException("Client"));
    reservation.setClient(client);
    return toResponse(reservationRepository.save(reservation));
  }

  @Override
  public ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
    if (requestDTO == null) throw new IllegalArgumentException("La requête de réservation est obligatoire.");

    ReservationDto reservationDto = requestDTO.getReservation();
    ContratDTO contratDto = requestDTO.getContrat();

    Reservation reservation = new Reservation();
    reservation.setDateDebut(reservationDto != null ? reservationDto.dateDebut() : requestDTO.getDateDebut());
    reservation.setDateFin(reservationDto != null ? reservationDto.dateFin() : requestDTO.getDateFin());
    reservation.setStatut(reservationDto != null && reservationDto.statut() != null
      ? reservationDto.statut()
      : (requestDTO.getStatut() != null ? requestDTO.getStatut() : StatutReservation.EN_ATTENTE));

    Long vehiculeId = reservationDto != null ? reservationDto.vehiculeId() : requestDTO.getVehiculeId();
    Long clientId = reservationDto != null ? reservationDto.clientId() : requestDTO.getClientId();
    if (vehiculeId != null) {
      reservation.setVehicule(new tn.esprit.autoloc.autolocapi.domain.Vehicule());
      reservation.getVehicule().setIdVehicule(vehiculeId);
    }
    if (clientId != null) {
      reservation.setClient(new Client());
      reservation.getClient().setIdClient(clientId);
    }

    validate(reservation);
    reservation = reservationRepository.save(reservation);

    Contrat contrat = new Contrat();
    contrat.setDateSignature(contratDto != null ? contratDto.dateSignature() : requestDTO.getDateSignature());
    contrat.setMontantTotal(contratDto != null && contratDto.montantTotal() != null
      ? contratDto.montantTotal()
      : (requestDTO.getMontantTotal() != null ? requestDTO.getMontantTotal() : BigDecimal.ZERO));
    contrat.setValide(contratDto != null ? contratDto.valide() : Boolean.TRUE.equals(requestDTO.getValide()));
    contrat.setReservation(reservation);
    reservation.setContrat(contrat);

    if (contrat.getDateSignature() == null) {
      throw new IllegalArgumentException("La date de signature est obligatoire.");
    }
    contratRepository.save(contrat);
    return toResponse(reservation);
  }

  private ReservationResponseDTO toResponse(Reservation reservation) {
    return new ReservationResponseDTO(
      reservation.getIdReservation(),
      reservation.getDateDebut(),
      reservation.getDateFin(),
      reservation.getStatut(),
      reservation.getVehicule() != null ? reservation.getVehicule().getIdVehicule() : null,
      reservation.getClient() != null ? reservation.getClient().getIdClient() : null
    );
  }

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
