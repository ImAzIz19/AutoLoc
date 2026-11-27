package tn.esprit.autoloc.autolocapi.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehiculeSchedulerService {
  private static final ZoneId SCHEDULER_ZONE = ZoneId.of("Europe/Paris");

  private final IReservationRepository reservationRepository;
  private final IVehiculeRepository vehiculeRepository;

  @Scheduled(cron = "0 0 1 * * *", zone = "Europe/Paris")
  @Transactional
  public void terminerLocationsEtLibererVehicules() {
    LocalDate aujourdHui = LocalDate.now(SCHEDULER_ZONE);
    List<Reservation> reservationsTerminees = reservationRepository.findByStatutAndDateFinLessThan(
      StatutReservation.CONFIRMEE,
      aujourdHui
    );

    if (reservationsTerminees.isEmpty()) {
      log.info("Aucune location à terminer aujourd'hui.");
      return;
    }

    reservationsTerminees.forEach(reservation -> reservation.setStatut(StatutReservation.TERMINEE));
    reservationRepository.saveAll(reservationsTerminees);

    reservationsTerminees.stream()
      .map(Reservation::getVehicule)
      .filter(vehicule -> vehicule != null)
      .distinct()
      .forEach(vehicule -> libererVehiculeSiDisponible(vehicule, aujourdHui));

    log.info("{} location(s) terminée(s).", reservationsTerminees.size());
  }

  private void libererVehiculeSiDisponible(Vehicule vehicule, LocalDate aujourdHui) {
    boolean autreLocationEnCours =
      reservationRepository.existsByVehicule_IdVehiculeAndStatutAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
        vehicule.getIdVehicule(),
        StatutReservation.CONFIRMEE,
        aujourdHui,
        aujourdHui
      );

    if (!autreLocationEnCours && vehicule.getStatut() != StatutVehicule.MAINTENANCE) {
      vehicule.setStatut(StatutVehicule.DISPONIBLE);
      vehiculeRepository.save(vehicule);
      log.info("Véhicule {} remis à DISPONIBLE.", vehicule.getIdVehicule());
    }
  }
}
