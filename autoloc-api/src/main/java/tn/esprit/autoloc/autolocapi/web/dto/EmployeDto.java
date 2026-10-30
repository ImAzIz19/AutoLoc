package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tn.esprit.autoloc.autolocapi.domain.RoleEmploye;

public record EmployeDto(
  Long idEmploye,
  @NotBlank(message = "Le nom de l'employé est obligatoire.") String nom,
  @NotBlank(message = "Le prénom de l'employé est obligatoire.") String prenom,
  @NotNull(message = "Le rôle de l'employé est obligatoire.") RoleEmploye role,
  Long agenceId
) {}