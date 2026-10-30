package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.service.IReservationService;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationDto;
import tn.esprit.autoloc.autolocapi.web.mapper.ReservationMapper;

@RestController @RequestMapping("/reservations") @RequiredArgsConstructor
@Tag(name = "Réservations", description = "Gestion des réservations")
public class ReservationController {
  private final IReservationService reservationService;
  private final ReservationMapper reservationMapper;
  @GetMapping @Operation(summary = "Lister les réservations") @ApiResponse(responseCode = "200", description = "Liste des réservations")
  public List<ReservationDto> findAll() { return reservationService.getAllReservations().stream().map(reservationMapper::toDto).toList(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter une réservation") @ApiResponses({@ApiResponse(responseCode = "200", description = "Réservation trouvée"), @ApiResponse(responseCode = "404", description = "Réservation introuvable")})
  public ReservationDto findById(@PathVariable Long id) { return reservationMapper.toDto(reservationService.getReservationById(id)); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer une réservation") @ApiResponses({@ApiResponse(responseCode = "201", description = "Réservation créée"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public ReservationDto create(@Valid @RequestBody ReservationDto dto) { return reservationMapper.toDto(reservationService.saveReservation(reservationMapper.toEntity(dto))); }
  @PutMapping("/{id}") @Operation(summary = "Modifier une réservation") @ApiResponses({@ApiResponse(responseCode = "200", description = "Réservation modifiée"), @ApiResponse(responseCode = "404", description = "Réservation introuvable"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public ReservationDto update(@PathVariable Long id, @Valid @RequestBody ReservationDto dto) { return reservationMapper.toDto(reservationService.updateReservation(id, reservationMapper.toEntity(dto))); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer une réservation") @ApiResponses({@ApiResponse(responseCode = "204", description = "Réservation supprimée"), @ApiResponse(responseCode = "404", description = "Réservation introuvable")})
  public void delete(@PathVariable Long id) { reservationService.deleteReservation(id); }
}