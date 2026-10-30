package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autoloc.autolocapi.domain.CategorieVehicule;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;

public record VehiculeDto(
  Long idVehicule,
  @NotBlank(message = "L'immatriculation du véhicule est obligatoire.") String immatriculation,
  @NotBlank(message = "La marque du véhicule est obligatoire.") String marque,
  @NotBlank(message = "Le modèle du véhicule est obligatoire.") String modele,
  @NotNull(message = "La catégorie du véhicule est obligatoire.") CategorieVehicule categorie,
  @NotNull(message = "Le tarif journalier est obligatoire.")
  @DecimalMin(value = "0.0", message = "Le tarif journalier doit être supérieur ou égal à zéro.") BigDecimal tarifJournalier,
  @NotNull(message = "Le statut du véhicule est obligatoire.") StatutVehicule statut,
  @NotNull(message = "L'identifiant de l'agence est obligatoire.") Long agenceId,
  List<Long> equipementIds
) {}