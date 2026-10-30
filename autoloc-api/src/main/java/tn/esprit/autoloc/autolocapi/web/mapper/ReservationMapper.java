package tn.esprit.autoloc.autolocapi.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {
  @Mapping(target = "clientId", source = "client.idClient")
  @Mapping(target = "vehiculeId", source = "vehicule.idVehicule")
  ReservationDto toDto(Reservation reservation);

  @Mapping(target = "idReservation", ignore = true)
  @Mapping(target = "client", expression = "java(toClient(dto.clientId()))")
  @Mapping(target = "vehicule", expression = "java(toVehicule(dto.vehiculeId()))")
  Reservation toEntity(ReservationDto dto);

  default Client toClient(Long id) {
    if (id == null) return null;
    Client client = new Client();
    client.setIdClient(id);
    return client;
  }

  default Vehicule toVehicule(Long id) {
    if (id == null) return null;
    Vehicule vehicule = new Vehicule();
    vehicule.setIdVehicule(id);
    return vehicule;
  }
}