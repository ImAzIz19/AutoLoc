package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record MaintenanceDto(
  Long idMaintenance,
  @NotNull(message = "La date de début de maintenance est obligatoire.") LocalDate dateDebut,
  LocalDate dateFin,
  String description,
  Long vehiculeId
) {}