package tn.esprit.autoloc.autolocapi.web.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException exception) {
    return error(HttpStatus.NOT_FOUND, exception.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException exception) {
    return error(HttpStatus.BAD_REQUEST, exception.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException exception) {
    Map<String, String> fields = new LinkedHashMap<>();
    exception.getBindingResult().getFieldErrors().forEach(fieldError ->
      fields.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage()));
    return error(HttpStatus.BAD_REQUEST, "Les données fournies sont invalides.", fields);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, Object>> handleUnreadableBody(HttpMessageNotReadableException exception) {
    return error(HttpStatus.BAD_REQUEST, "Le corps JSON est invalide ou contient une valeur non reconnue.");
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, Object>> handleConflict(DataIntegrityViolationException exception) {
    return error(HttpStatus.CONFLICT, "La ressource entre en conflit avec les données existantes.");
  }

  private ResponseEntity<Map<String, Object>> error(HttpStatus status, String message) {
    return error(status, message, Map.of());
  }

  private ResponseEntity<Map<String, Object>> error(HttpStatus status, String message, Map<String, String> fields) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("statut", status.value());
    body.put("message", message);
    body.put("horodatage", LocalDateTime.now().toString());
    if (!fields.isEmpty()) body.put("erreurs", fields);
    return ResponseEntity.status(status).body(body);
  }
}