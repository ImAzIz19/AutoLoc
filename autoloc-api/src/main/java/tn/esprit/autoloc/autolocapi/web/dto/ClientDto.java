package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record ClientDto(
  Long idClient,
  @NotBlank(message = "Le nom du client est obligatoire.") String nom,
  @NotBlank(message = "Le prénom du client est obligatoire.") String prenom,
  @NotBlank(message = "L'adresse e-mail est obligatoire.")
  @Email(message = "L'adresse e-mail doit être valide.") String email,
  @NotBlank(message = "Le numéro de téléphone est obligatoire.") String telephone,
  @NotBlank(message = "Le numéro de permis est obligatoire.") String numPermis,
  LocalDate dateInscription
) {}