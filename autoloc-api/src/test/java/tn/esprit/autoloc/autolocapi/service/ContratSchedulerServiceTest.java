package tn.esprit.autoloc.autolocapi.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;

class ContratSchedulerServiceTest {
  private final IContratRepository contratRepository = org.mockito.Mockito.mock(IContratRepository.class);
  private final ContratSchedulerService scheduler = new ContratSchedulerService(contratRepository);

  @Test
  void searchesContractsExpiringTodayOrTomorrow() {
    LocalDate today = LocalDate.now(ZoneId.of("Europe/Paris"));
    when(contratRepository.findByReservation_DateFinBetween(today, today.plusDays(1)))
      .thenReturn(List.of());

    scheduler.identifierContratsArrivantAEcheance();

    verify(contratRepository).findByReservation_DateFinBetween(today, today.plusDays(1));
  }
}
