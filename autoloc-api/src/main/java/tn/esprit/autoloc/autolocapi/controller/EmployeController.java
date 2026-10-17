package tn.esprit.autoloc.autolocapi.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.service.IEmployeService;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

  private final IEmployeService employeService;

  @PostMapping
  public Employe create(@RequestBody Employe employe) {
    return employeService.saveEmploye(employe);
  }

  @GetMapping("/{id}")
  public Employe getById(@PathVariable Long id) {
    return employeService.getEmployeById(id);
  }

  @GetMapping
  public List<Employe> getAll() {
    return employeService.getAllEmployes();
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    employeService.deleteEmploye(id);
  }
}
