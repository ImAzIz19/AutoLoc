
package tn.esprit.autoloc.autolocapi.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Contrat;

public interface IContratRepository extends JpaRepository<Contrat, Long> {
  List<Contrat> findByReservation_DateFinBetween(LocalDate dateDebut, LocalDate dateFin);
}
