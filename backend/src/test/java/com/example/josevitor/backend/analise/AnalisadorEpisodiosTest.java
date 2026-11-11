package com.example.josevitor.backend.analise;

import com.example.josevitor.backend.model.LinhaLeitura;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalisadorEpisodiosTest {

  private final AnalisadorEpisodios analisador = new AnalisadorEpisodios();

  @Test
  void exatamente5KmhAcimaNaoEhExcesso() {
    var episodios = analisador.analisar(List.of(leitura("08:00:00", 65, 60)));

    assertEquals(0, episodios.size());
  }

  @Test
  void intervaloDe5MinutosContinuaEpisodio() {
    var episodios = analisador.analisar(List.of(leitura("08:00:00", 75, 60), leitura("08:05:00", 75, 60)));

    assertEquals(1, episodios.size());
  }

  @Test
  void intervaloMaiorQue5MinutosAbreOutroEpisodio() {
    var episodios = analisador.analisar(List.of(leitura("08:00:00", 75, 60), leitura("08:05:01", 75, 60)));

    assertEquals(2, episodios.size());
  }

  @Test
  void excessoMaximoEhOMaiorPercentual() {
    var episodios = analisador.analisar(List.of(leitura("08:00:00", 80, 60), leitura("08:01:00", 62, 40)));

    assertEquals(new BigDecimal("55.0"), episodios.get(0).excessoMaximo());
  }

  private LinhaLeitura leitura(String hora, int velocidade, int limite) {
    return new LinhaLeitura("ABC1D23", LocalDateTime.parse("2026-10-05T" + hora), velocidade, limite);
  }
}
