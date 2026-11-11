package com.example.josevitor.backend.leitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LeitorCsvTest { 

  private final LeitorCsv leitor = new LeitorCsv();

  @Test
  void linhaInvalidaNaoDerrubaProcessamento() {
    String csv = """
        placa;dataHora;velocidadeKmh;limiteKmh
        ABC1D23;2026-10-05T08:00:00;72;60
        ABC1D23;2026-10-05T08:01:00;abc;60
        ABC1D23;2026-10-05T08:02:00;70;60
        """;

    ResultadoLeitura resultado = leitor.ler(csv);

    assertEquals(3, resultado.linhasLidas());
    assertEquals(2, resultado.validas().size());
    assertEquals(1, resultado.invalidas().size());
    assertEquals(3, resultado.invalidas().get(0).linha());
  }

  @Test
  void leituraDuplicadaVaiParaInvalidas() {
    String csv = """
        placa;dataHora;velocidadeKmh;limiteKmh
        ABC1D23;2026-10-05T08:00:00;72;60
        ABC1D23;2026-10-05T08:00:00;72;60
        """;

    ResultadoLeitura resultado = leitor.ler(csv);

    assertEquals(1, resultado.validas().size());
    assertEquals(1, resultado.invalidas().size());
    assertEquals("Leitura duplicada da linha 2", resultado.invalidas().get(0).motivo());
  }

  @Test
  void placaEhNormalizada() {
    String csv = """
        placa;dataHora;velocidadeKmh;limiteKmh
         vwx8y90 ;2026-10-05T15:01:00;74;60
        """;

    ResultadoLeitura resultado = leitor.ler(csv);

    assertEquals("VWX8Y90", resultado.validas().get(0).placa());
  }

  @Test
  void arquivoSemCabecalhoLancaExcecao() {
    String csv = """
        ABC1D23;2026-10-05T08:00:00;72;60
        """;

    assertThrows(ArquivoInvalidoException.class, () -> leitor.ler(csv));
  }
}
