package tn.esprit.autoloc.autolocapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.service.IReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final IReservationService reservationService;

  @PostMapping
  public Reservation create(@RequestBody Reservation reservation) {
    return reservationService.saveReservation(reservation);
  }

  @GetMapping("/{id}")
  public Reservation getById(@PathVariable Long id) {
    return reservationService.getReservationById(id);
  }

  @GetMapping
  public List<Reservation> getAll() {
    return reservationService.getAllReservations();
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    reservationService.deleteReservation(id);
  }
}
