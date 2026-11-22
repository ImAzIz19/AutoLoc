package tn.esprit.autoloc.autolocapi.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationWithContratRequestDTO {
  private ReservationDto reservation;
  private ContratDTO contrat;

  private LocalDate dateDebut;
  private LocalDate dateFin;
  private StatutReservation statut;
  private Long vehiculeId;
  private Long clientId;
  private LocalDate dateSignature;
  private BigDecimal montantTotal;
  private Boolean valide;

  public ReservationDto getReservationDto() { return reservation; }
  public void setReservationDto(ReservationDto reservation) { this.reservation = reservation; }
  public ContratDTO getContratDto() { return contrat; }
  public void setContratDto(ContratDTO contrat) { this.contrat = contrat; }
}
