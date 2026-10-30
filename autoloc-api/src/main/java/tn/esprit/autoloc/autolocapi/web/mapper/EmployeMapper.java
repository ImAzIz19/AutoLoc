package tn.esprit.autoloc.autolocapi.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.web.dto.EmployeDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeMapper {
  @Mapping(target = "agenceId", source = "agence.idAgence")
  EmployeDto toDto(Employe employe);

  @Mapping(target = "idEmploye", ignore = true)
  @Mapping(target = "agence", expression = "java(toAgence(dto.agenceId()))")
  Employe toEntity(EmployeDto dto);

  default Agence toAgence(Long id) {
    if (id == null) return null;
    Agence agence = new Agence();
    agence.setIdAgence(id);
    return agence;
  }
}