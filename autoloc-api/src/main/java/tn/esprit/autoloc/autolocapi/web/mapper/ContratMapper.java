package tn.esprit.autoloc.autolocapi.web.mapper;

import java.util.ArrayList;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.web.dto.ContratDto;

@Mapper(componentModel = "spring", uses = PaiementMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContratMapper {
  @Mapping(target = "reservationId", source = "reservation.idReservation")
  ContratDto toDto(Contrat contrat);

  @Mapping(target = "reservation", expression = "java(toReservation(dto.reservationId()))")
  Contrat toEntity(ContratDto dto);

  default Reservation toReservation(Long id) {
    if (id == null) return null;
    Reservation reservation = new Reservation();
    reservation.setIdReservation(id);
    return reservation;
  }

  @AfterMapping
  default void linkPayments(@MappingTarget Contrat contrat) {
    if (contrat.getPaiements() == null) contrat.setPaiements(new ArrayList<>());
    for (Paiement paiement : contrat.getPaiements()) paiement.setContrat(contrat);
  }
}