package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.domain.Employe;
import java.util.List;

public interface IEmployeService {
  Employe saveEmploye(Employe employe);
  Employe getEmployeById(Long id);
  List<Employe> getAllEmployes();
  void deleteEmploye(Long id);
}
