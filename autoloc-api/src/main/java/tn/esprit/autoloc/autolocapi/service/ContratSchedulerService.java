package tn.esprit.autoloc.autolocapi.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContratSchedulerService {
  private static final ZoneId SCHEDULER_ZONE = ZoneId.of("Europe/Paris");

  private final IContratRepository contratRepository;

  @Scheduled(cron = "0 0 7 * * *", zone = "Europe/Paris")
  @Transactional(readOnly = true)
  public void identifierContratsArrivantAEcheance() {
    LocalDate aujourdHui = LocalDate.now(SCHEDULER_ZONE);
    List<Contrat> contrats = contratRepository.findByReservation_DateFinBetween(
      aujourdHui,
      aujourdHui.plusDays(1)
    );

    if (contrats.isEmpty()) {
      log.info("Aucun contrat n'arrive à échéance dans les prochaines 48 heures.");
      return;
    }

    contrats.forEach(contrat -> log.info(
      "Contrat {} arrivant à échéance sous 48 heures (fin de réservation : {}).",
      contrat.getIdContrat(),
      contrat.getReservation().getDateFin()
    ));
  }
}
