package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import tn.esprit.autoloc.autolocapi.domain.ModePaiement;

public record PaiementDto(
  Long idPaiement,
  @NotNull(message = "Le montant du paiement est obligatoire.")
  @Positive(message = "Le montant du paiement doit être supérieur à zéro.") BigDecimal montant,
  @NotNull(message = "La date du paiement est obligatoire.") LocalDate datePaiement,
  @NotNull(message = "Le mode de paiement est obligatoire.") ModePaiement modePaiement
) {}