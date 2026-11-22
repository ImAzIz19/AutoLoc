package tn.esprit.autoloc.autolocapi.exception;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String ressource) {
    super(ressource + " introuvable");
  }
  public ResourceNotFoundException(String ressource, Long id) {
    super(ressource + " introuvable avec l'identifiant " + id);
  }
}
