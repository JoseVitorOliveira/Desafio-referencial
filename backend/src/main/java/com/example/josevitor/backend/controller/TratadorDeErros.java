package com.example.josevitor.backend.controller;

import com.example.josevitor.backend.leitor.ArquivoInvalidoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros {

  @ExceptionHandler(ArquivoInvalidoException.class)
  public ResponseEntity<Map<String, String>> arquivoInvalido(ArquivoInvalidoException e) {
    return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
  }
}
