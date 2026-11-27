package tn.esprit.autoloc.autolocapi.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
  List<Reservation> findByStatutAndDateFinLessThan(StatutReservation statut, LocalDate dateFin);

  boolean existsByVehicule_IdVehiculeAndStatutAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
    Long idVehicule,
    StatutReservation statut,
    LocalDate dateDebut,
    LocalDate dateFin
  );
}
