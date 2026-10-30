package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.NotBlank;

public record AgenceDto(
  Long idAgence,
  @NotBlank(message = "Le nom de l'agence est obligatoire.") String nom,
  @NotBlank(message = "La ville de l'agence est obligatoire.") String ville,
  @NotBlank(message = "L'adresse de l'agence est obligatoire.") String adresse,
  @NotBlank(message = "Le téléphone de l'agence est obligatoire.") String telephone
) {}