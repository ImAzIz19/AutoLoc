package tn.esprit.autoloc.autolocapi.web.mapper;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.web.dto.VehiculeDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VehiculeMapper {
  @Mapping(target = "agenceId", source = "agence.idAgence")
  @Mapping(target = "equipementIds", source = "equipements")
  VehiculeDto toDto(Vehicule vehicule);

  @Mapping(target = "idVehicule", ignore = true)
  @Mapping(target = "agence", expression = "java(toAgence(dto.agenceId()))")
  @Mapping(target = "equipements", source = "equipementIds")
  Vehicule toEntity(VehiculeDto dto);

  default Agence toAgence(Long id) {
    if (id == null) return null;
    Agence agence = new Agence();
    agence.setIdAgence(id);
    return agence;
  }

  default List<Equipement> toEquipements(List<Long> ids) {
    if (ids == null) return null;
    List<Equipement> equipements = new ArrayList<>();
    for (Long id : ids) {
      Equipement equipement = new Equipement();
      equipement.setIdEquipement(id);
      equipements.add(equipement);
    }
    return equipements;
  }

  default List<Long> toEquipementIds(List<Equipement> equipements) {
    if (equipements == null) return null;
    List<Long> ids = new ArrayList<>();
    for (Equipement equipement : equipements) ids.add(equipement.getIdEquipement());
    return ids;
  }
}