package tn.esprit.autoloc.autolocapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

class VehiculeSchedulerServiceTest {
  private final IReservationRepository reservationRepository = org.mockito.Mockito.mock(IReservationRepository.class);
  private final IVehiculeRepository vehiculeRepository = org.mockito.Mockito.mock(IVehiculeRepository.class);
  private final VehiculeSchedulerService scheduler =
    new VehiculeSchedulerService(reservationRepository, vehiculeRepository);

  @Test
  void completesExpiredReservationAndMakesVehicleAvailable() {
    LocalDate today = LocalDate.now(ZoneId.of("Europe/Paris"));
    Vehicule vehicule = new Vehicule();
    vehicule.setIdVehicule(42L);
    vehicule.setStatut(StatutVehicule.LOUE);

    Reservation reservation = new Reservation();
    reservation.setStatut(StatutReservation.CONFIRMEE);
    reservation.setDateFin(today.minusDays(1));
    reservation.setVehicule(vehicule);

    when(reservationRepository.findByStatutAndDateFinLessThan(
      StatutReservation.CONFIRMEE,
      today
    )).thenReturn(List.of(reservation));
    when(reservationRepository
      .existsByVehicule_IdVehiculeAndStatutAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
        42L,
        StatutReservation.CONFIRMEE,
        today,
        today
      )).thenReturn(false);
    when(vehiculeRepository.save(any(Vehicule.class))).thenAnswer(invocation -> invocation.getArgument(0));

    scheduler.terminerLocationsEtLibererVehicules();

    assertEquals(StatutReservation.TERMINEE, reservation.getStatut());
    assertEquals(StatutVehicule.DISPONIBLE, vehicule.getStatut());
    verify(reservationRepository).saveAll(List.of(reservation));
    verify(vehiculeRepository).save(vehicule);
  }

  @Test
  void doesNotReleaseVehicleWithAnotherActiveReservation() {
    LocalDate today = LocalDate.now(ZoneId.of("Europe/Paris"));
    Vehicule vehicule = new Vehicule();
    vehicule.setIdVehicule(42L);
    vehicule.setStatut(StatutVehicule.LOUE);

    Reservation reservation = new Reservation();
    reservation.setStatut(StatutReservation.CONFIRMEE);
    reservation.setDateFin(today.minusDays(1));
    reservation.setVehicule(vehicule);

    when(reservationRepository.findByStatutAndDateFinLessThan(
      StatutReservation.CONFIRMEE,
      today
    )).thenReturn(List.of(reservation));
    when(reservationRepository
      .existsByVehicule_IdVehiculeAndStatutAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
        eq(42L),
        eq(StatutReservation.CONFIRMEE),
        eq(today),
        eq(today)
      )).thenReturn(true);

    scheduler.terminerLocationsEtLibererVehicules();

    assertEquals(StatutReservation.TERMINEE, reservation.getStatut());
    assertEquals(StatutVehicule.LOUE, vehicule.getStatut());
  }
}
