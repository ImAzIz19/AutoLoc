package tn.esprit.autoloc.autolocapi.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.CategorieVehicule;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;
import tn.esprit.autoloc.autolocapi.repository.IClientRepository;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
  private final IAgenceRepository agenceRepository;
  private final IClientRepository clientRepository;
  private final IEquipementRepository equipementRepository;
  private final IVehiculeRepository vehiculeRepository;
  private final IReservationRepository reservationRepository;
  private final IContratRepository contratRepository;

  @Override
  public void run(String... args) {
    if (!agenceRepository.findAll().isEmpty()
      || !clientRepository.findAll().isEmpty()
      || !vehiculeRepository.findAll().isEmpty()) {
      return;
    }

    Agence agence = new Agence();
    agence.setNom("Agence Tunis");
    agence.setVille("Tunis");
    agence.setAdresse("1 avenue Habib Bourguiba");
    agence.setTelephone("71111111");
    agence = agenceRepository.save(agence);

    Client client = new Client();
    client.setNom("Ben Ali");
    client.setPrenom("Mohamed");
    client.setEmail("mohamed.benali@autoloc.tn");
    client.setTelephone("20111222");
    client.setNumPermis("PERMIS-AUTOLOC-001");
    client.setDateInscription(LocalDate.now());
    client = clientRepository.save(client);

    Equipement gps = new Equipement();
    gps.setLibelle("GPS");
    gps = equipementRepository.save(gps);

    Equipement camera = new Equipement();
    camera.setLibelle("Caméra de recul");
    camera = equipementRepository.save(camera);

    Vehicule vehicule = new Vehicule();
    vehicule.setImmatriculation("TU-1234");
    vehicule.setMarque("Peugeot");
    vehicule.setModele("208");
    vehicule.setCategorie(CategorieVehicule.CITADINE);
    vehicule.setTarifJournalier(new BigDecimal("85.50"));
    vehicule.setStatut(StatutVehicule.DISPONIBLE);
    vehicule.setAgence(agence);
    vehicule.setEquipements(new ArrayList<>());
    vehicule = vehiculeRepository.save(vehicule);

    Vehicule secondVehicule = new Vehicule();
    secondVehicule.setImmatriculation("TU-5678");
    secondVehicule.setMarque("Renault");
    secondVehicule.setModele("Arkana");
    secondVehicule.setCategorie(CategorieVehicule.SUV);
    secondVehicule.setTarifJournalier(new BigDecimal("140.00"));
    secondVehicule.setStatut(StatutVehicule.DISPONIBLE);
    secondVehicule.setAgence(agence);
    secondVehicule.setEquipements(new ArrayList<>());
    secondVehicule = vehiculeRepository.save(secondVehicule);

    Reservation reservationSansClient = new Reservation();
    reservationSansClient.setDateDebut(LocalDate.now().plusDays(3));
    reservationSansClient.setDateFin(LocalDate.now().plusDays(7));
    reservationSansClient.setStatut(StatutReservation.EN_ATTENTE);
    reservationSansClient.setVehicule(vehicule);
    reservationSansClient = reservationRepository.save(reservationSansClient);

    Reservation reservationAvecContrat = new Reservation();
    reservationAvecContrat.setDateDebut(LocalDate.now());
    reservationAvecContrat.setDateFin(LocalDate.now().plusDays(2));
    reservationAvecContrat.setStatut(StatutReservation.CONFIRMEE);
    reservationAvecContrat.setVehicule(secondVehicule);
    reservationAvecContrat.setClient(client);
    reservationAvecContrat = reservationRepository.save(reservationAvecContrat);
    secondVehicule.setStatut(StatutVehicule.LOUE);
    vehiculeRepository.save(secondVehicule);

    Contrat contrat = new Contrat();
    contrat.setDateSignature(LocalDate.now());
    contrat.setMontantTotal(new BigDecimal("280.00"));
    contrat.setValide(true);
    contrat.setReservation(reservationAvecContrat);
    contrat.setPaiements(new ArrayList<>());
    contratRepository.save(contrat);
  }
}
