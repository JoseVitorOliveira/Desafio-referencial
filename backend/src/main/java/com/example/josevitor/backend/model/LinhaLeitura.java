package com.example.josevitor.backend.model;

import java.time.LocalDateTime;

public record LinhaLeitura(
  String placa,
  LocalDateTime dataHora,
  int velocidadeKmh,
  int limiteKmh
) {
}
