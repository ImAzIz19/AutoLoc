package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ContratDto(
  Long idContrat,
  @NotNull(message = "La date de signature est obligatoire.") LocalDate dateSignature,
  @NotNull(message = "Le montant total du contrat est obligatoire.")
  @PositiveOrZero(message = "Le montant total du contrat doit être positif ou nul.") BigDecimal montantTotal,
  boolean valide,
  Long reservationId,
  List<@Valid PaiementDto> paiements
) {}