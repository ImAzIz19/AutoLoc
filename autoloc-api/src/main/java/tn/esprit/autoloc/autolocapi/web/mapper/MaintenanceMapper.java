package tn.esprit.autoloc.autolocapi.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.web.dto.MaintenanceDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MaintenanceMapper {
  @Mapping(target = "vehiculeId", source = "vehicule.idVehicule")
  MaintenanceDto toDto(Maintenance maintenance);

  @Mapping(target = "idMaintenance", ignore = true)
  @Mapping(target = "vehicule", expression = "java(toVehicule(dto.vehiculeId()))")
  Maintenance toEntity(MaintenanceDto dto);

  default Vehicule toVehicule(Long id) {
    if (id == null) return null;
    Vehicule vehicule = new Vehicule();
    vehicule.setIdVehicule(id);
    return vehicule;
  }
}