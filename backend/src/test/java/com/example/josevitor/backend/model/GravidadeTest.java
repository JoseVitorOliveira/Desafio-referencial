package com.example.josevitor.backend.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GravidadeTest {

  @Test
  void excessoBaixoELeve() {
    assertEquals(Gravidade.LEVE, Gravidade.calcularGravidade(new BigDecimal("5.0")));
  }

  @Test
  void exatamente20PorCentoAindaELeve() {
    assertEquals(Gravidade.LEVE, Gravidade.calcularGravidade(new BigDecimal("20.0")));
  }

  @Test
  void acimaDe20PorCentoEGrave() {
    assertEquals(Gravidade.GRAVE, Gravidade.calcularGravidade(new BigDecimal("20.1")));
  }

  @Test
  void exatamente50PorCentoAindaEGrave() {
    assertEquals(Gravidade.GRAVE, Gravidade.calcularGravidade(new BigDecimal("50.0")));
  }

  @Test
  void acimaDe50PorCentoEGravissima() {
    assertEquals(Gravidade.GRAVISSIMA, Gravidade.calcularGravidade(new BigDecimal("50.1")));
  }
}
