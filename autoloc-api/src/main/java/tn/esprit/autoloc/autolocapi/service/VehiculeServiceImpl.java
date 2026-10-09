package tn.esprit.autoloc.autolocapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculeServiceImpl implements IVehiculeService {

  @Autowired
  private IVehiculeRepository vehiculeRepository;

  @Override
  public Vehicule saveVehicule(Vehicule vehicule) {
    return vehiculeRepository.save(vehicule);
  }

  @Override
  public Optional<Vehicule> getVehiculeById(Long id) {
    return vehiculeRepository.findById(id);
  }

  @Override
  public List<Vehicule> getAllVehicules() {
    return vehiculeRepository.findAll();
  }

  @Override
  public void deleteVehicule(Long id) {
    vehiculeRepository.deleteById(id);
  }
}
