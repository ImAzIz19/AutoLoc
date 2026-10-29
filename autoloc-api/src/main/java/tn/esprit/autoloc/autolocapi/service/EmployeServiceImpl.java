package tn.esprit.autoloc.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IEmployeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

  private final IEmployeRepository employeRepository;

  @Override
  public Employe saveEmploye(Employe employe) {
    if (employe.getNom() == null || employe.getNom().isBlank()
      || employe.getPrenom() == null || employe.getPrenom().isBlank()
      || employe.getRole() == null) {
      throw new IllegalArgumentException("Le nom et le prénom de l'employé sont obligatoires.");
    }
    return employeRepository.save(employe);
  }

  @Override
  public Employe updateEmploye(Long id, Employe employe) {
    Employe existing = getEmployeById(id);
    if (employe.getNom() == null || employe.getNom().isBlank()
      || employe.getPrenom() == null || employe.getPrenom().isBlank()
      || employe.getRole() == null) {
      throw new IllegalArgumentException("Le nom, le prénom et le rôle de l'employé sont obligatoires.");
    }
    existing.setNom(employe.getNom());
    existing.setPrenom(employe.getPrenom());
    existing.setRole(employe.getRole());
    existing.setAgence(employe.getAgence());
    return employeRepository.save(existing);
  }

  @Override
  public Employe getEmployeById(Long id) {
    return employeRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
  }

  @Override
  public List<Employe> getAllEmployes() {
    return employeRepository.findAll();
  }

  @Override
  public void deleteEmploye(Long id) {
    if (!employeRepository.existsById(id)) {
      throw new ResourceNotFoundException("Employe", id);
    }
    employeRepository.deleteById(id);
  }
}
