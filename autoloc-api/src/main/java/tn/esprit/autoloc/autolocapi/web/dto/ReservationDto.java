package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;

public record ReservationDto(
  Long idReservation,
  @NotNull(message = "La date de début de réservation est obligatoire.") LocalDate dateDebut,
  @NotNull(message = "La date de fin de réservation est obligatoire.") LocalDate dateFin,
  @NotNull(message = "Le statut de réservation est obligatoire.") StatutReservation statut,
  @NotNull(message = "L'identifiant du véhicule est obligatoire.") Long vehiculeId,
  @NotNull(message = "L'identifiant du client est obligatoire.") Long clientId
) {}