package com.example.josevitor.backend.leitor;

import com.example.josevitor.backend.model.LinhaInvalida;
import com.example.josevitor.backend.model.LinhaLeitura;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LeitorCsv {

  private static final String CABECALHO = "placa;dataHora;velocidadeKmh;limiteKmh";

  public ResultadoLeitura ler(String conteudo) {
    if (conteudo == null || conteudo.isBlank()) {
      throw new ArquivoInvalidoException("Arquivo vazio");
    }

    List<String> linhas = conteudo.lines().toList();

    if (!linhas.get(0).trim().equals(CABECALHO)) {
      throw new ArquivoInvalidoException("Cabeçalho inválido. Esperado: " + CABECALHO);
    }

    List<LinhaLeitura> validas = new ArrayList<>();
    List<LinhaInvalida> invalidas = new ArrayList<>();
    Map<String, Integer> jaVistas = new HashMap<>();

    for (int i = 1; i < linhas.size(); i++) {
      int numeroLinha = i + 1;
      try {
        LinhaLeitura leitura = converter(linhas.get(i));
        String chave = leitura.placa() + "|" + leitura.dataHora();

        if (jaVistas.containsKey(chave)) {
          invalidas.add(new LinhaInvalida(numeroLinha, "Leitura duplicada da linha " + jaVistas.get(chave)));
        } else {
          jaVistas.put(chave, numeroLinha);
          validas.add(leitura);
        }
      } catch (IllegalArgumentException e) {
        invalidas.add(new LinhaInvalida(numeroLinha, e.getMessage()));
      }
    }

    return new ResultadoLeitura(linhas.size() - 1, validas, invalidas);
  }

  private LinhaLeitura converter(String linha) {
    if (linha.isBlank()) {
      throw new IllegalArgumentException("Linha vazia");
    }

    String[] campos = linha.split(";", -1);
    if (campos.length != 4) {
      throw new IllegalArgumentException("Quantidade de colunas inválida");
    }

    String placa = campos[0].trim().toUpperCase();
    if (placa.isEmpty()) {
      throw new IllegalArgumentException("Placa vazia");
    }

    LocalDateTime dataHora;
    try {
      dataHora = LocalDateTime.parse(campos[1].trim());
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Data inválida");
    }

    int velocidade = lerInteiro(campos[2], "Velocidade");
    int limite = lerInteiro(campos[3], "Limite");

    if (velocidade < 0) {
      throw new IllegalArgumentException("Velocidade negativa");
    }
    if (limite <= 0) {
      throw new IllegalArgumentException("Limite deve ser maior que zero");
    }

    return new LinhaLeitura(placa, dataHora, velocidade, limite);
  }

  private int lerInteiro(String valor, String campo) {
    try {
      return Integer.parseInt(valor.trim());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(campo + " não é um número inteiro");
    }
  }
}
