package com.example.josevitor.backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Episodio(
  String placa,
  LocalDateTime inicio,
  LocalDateTime fim,
  long duracaoSegundos,
  int velocidadeMaxima,
  BigDecimal excessoMaximo,
  Gravidade gravidade
) {
}
