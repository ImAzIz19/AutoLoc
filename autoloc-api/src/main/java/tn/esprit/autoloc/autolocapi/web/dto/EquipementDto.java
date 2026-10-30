package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.NotBlank;

public record EquipementDto(
	Long idEquipement,
	@NotBlank(message = "Le libellé de l'équipement est obligatoire.") String libelle
) {}