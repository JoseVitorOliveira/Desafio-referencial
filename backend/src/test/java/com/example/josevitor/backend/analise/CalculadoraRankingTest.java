package com.example.josevitor.backend.analise;

import com.example.josevitor.backend.model.Episodio;
import com.example.josevitor.backend.model.Gravidade;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadoraRankingTest {

  private final CalculadoraRanking calculadora = new CalculadoraRanking();

  @Test
  void somaOsPontosDosEpisodiosDoVeiculo() {
    var ranking = calculadora.calcular(List.of(episodio("ABC1D23", 80, Gravidade.GRAVE), episodio("ABC1D23", 70, Gravidade.LEVE)));

    assertEquals(4, ranking.get(0).pontos());
  }

  @Test
  void empateDePontosGanhaMaiorVelocidade() {
    var ranking = calculadora.calcular(List.of(episodio("AAA1A11", 70, Gravidade.LEVE), episodio("BBB2B22", 80, Gravidade.LEVE)));

    assertEquals("BBB2B22", ranking.get(0).placa());
  }

  private Episodio episodio(String placa, int velocidade, Gravidade gravidade) {
    return new Episodio(placa, null, null, 0, velocidade, BigDecimal.ZERO, gravidade);
  }
}
