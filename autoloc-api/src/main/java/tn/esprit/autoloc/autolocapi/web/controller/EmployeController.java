package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.service.IEmployeService;

@RestController @RequestMapping("/employes") @RequiredArgsConstructor
@Tag(name = "Employés", description = "Gestion des employés")
public class EmployeController {
  private final IEmployeService employeService;
  @GetMapping @Operation(summary = "Lister les employés") @ApiResponse(responseCode = "200", description = "Liste des employés")
  public List<Employe> findAll() { return employeService.getAllEmployes(); }
  @GetMapping("/{id}") @Operation(summary = "Consulter un employé") @ApiResponses({@ApiResponse(responseCode = "200", description = "Employé trouvé"), @ApiResponse(responseCode = "404", description = "Employé introuvable")})
  public Employe findById(@PathVariable Long id) { return employeService.getEmployeById(id); }
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un employé") @ApiResponses({@ApiResponse(responseCode = "201", description = "Employé créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public Employe create(@RequestBody Employe employe) { return employeService.saveEmploye(employe); }
  @PutMapping("/{id}") @Operation(summary = "Modifier un employé") @ApiResponses({@ApiResponse(responseCode = "200", description = "Employé modifié"), @ApiResponse(responseCode = "404", description = "Employé introuvable"), @ApiResponse(responseCode = "400", description = "Données invalides")})
  public Employe update(@PathVariable Long id, @RequestBody Employe employe) { return employeService.updateEmploye(id, employe); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un employé") @ApiResponses({@ApiResponse(responseCode = "204", description = "Employé supprimé"), @ApiResponse(responseCode = "404", description = "Employé introuvable")})
  public void delete(@PathVariable Long id) { employeService.deleteEmploye(id); }
}