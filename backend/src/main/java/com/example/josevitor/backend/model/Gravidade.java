package com.example.josevitor.backend.model;

import java.math.BigDecimal;

public enum Gravidade {
  LEVE(1),
  GRAVE(3),
  GRAVISSIMA(5);

  private final int pontos;

  Gravidade(int pontos) {
    this.pontos = pontos;
  }

  public int getPontos() {
    return pontos;
  }

  public static Gravidade calcularGravidade(BigDecimal excessoPercentual) {
    /* Até 20% = LEVE *** Ccima de 20% até 50% = GRAVE *** Acima de 50% = GRAVISSIMA. */
    if (excessoPercentual.compareTo(BigDecimal.valueOf(20)) <= 0) {
      return LEVE;
    } else if (excessoPercentual.compareTo(BigDecimal.valueOf(50)) <= 0) {
      return GRAVE;
    } else {
      return GRAVISSIMA;
    }
  }

}
