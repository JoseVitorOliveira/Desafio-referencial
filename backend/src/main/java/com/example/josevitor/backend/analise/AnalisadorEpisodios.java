package com.example.josevitor.backend.analise;

import com.example.josevitor.backend.model.Episodio;
import com.example.josevitor.backend.model.Gravidade;
import com.example.josevitor.backend.model.LinhaLeitura;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class AnalisadorEpisodios {

  private static final int TOLERANCIA_KMH = 5;
  private static final Duration INTERVALO_MAXIMO = Duration.ofMinutes(5);

  public List<Episodio> analisar(List<LinhaLeitura> leituras) {
    Map<String, List<LinhaLeitura>> porPlaca = leituras.stream()
        .collect(Collectors.groupingBy(LinhaLeitura::placa, TreeMap::new, Collectors.toList()));

    List<Episodio> episodios = new ArrayList<>();
    for (List<LinhaLeitura> leiturasDoVeiculo : porPlaca.values()) {
      episodios.addAll(analisarVeiculo(leiturasDoVeiculo));
    }
    return episodios;
  }

  private List<Episodio> analisarVeiculo(List<LinhaLeitura> leituras) {
    List<LinhaLeitura> ordenadas = leituras.stream()
        .sorted(Comparator.comparing(LinhaLeitura::dataHora))
        .toList();

    List<Episodio> episodios = new ArrayList<>();
    List<LinhaLeitura> episodioAtual = new ArrayList<>();
    LinhaLeitura anterior = null;

    for (LinhaLeitura leitura : ordenadas) {
      boolean semSinal = anterior != null
          && Duration.between(anterior.dataHora(), leitura.dataHora()).compareTo(INTERVALO_MAXIMO) > 0;

      if (!episodioAtual.isEmpty() && (!emExcesso(leitura) || semSinal)) {
        episodios.add(criarEpisodio(episodioAtual));
        episodioAtual = new ArrayList<>();
      }

      if (emExcesso(leitura)) {
        episodioAtual.add(leitura);
      }

      anterior = leitura;
    }

    if (!episodioAtual.isEmpty()) {
      episodios.add(criarEpisodio(episodioAtual));
    }

    return episodios;
  }

  private boolean emExcesso(LinhaLeitura leitura) {
    return leitura.velocidadeKmh() > leitura.limiteKmh() + TOLERANCIA_KMH;
  }

  private Episodio criarEpisodio(List<LinhaLeitura> leituras) {
    LinhaLeitura primeira = leituras.get(0);
    LinhaLeitura ultima = leituras.get(leituras.size() - 1);

    int velocidadeMaxima = 0;
    BigDecimal excessoMaximo = BigDecimal.ZERO;
    for (LinhaLeitura leitura : leituras) {
      velocidadeMaxima = Math.max(velocidadeMaxima, leitura.velocidadeKmh());
      excessoMaximo = excessoMaximo.max(calcularExcesso(leitura));
    }

    long duracaoSegundos = Duration.between(primeira.dataHora(), ultima.dataHora()).getSeconds();

    return new Episodio(
        primeira.placa(),
        primeira.dataHora(),
        ultima.dataHora(),
        duracaoSegundos,
        velocidadeMaxima,
        excessoMaximo,
        Gravidade.calcularGravidade(excessoMaximo)
    );
  }

  private BigDecimal calcularExcesso(LinhaLeitura leitura) {
    return BigDecimal.valueOf(leitura.velocidadeKmh() - leitura.limiteKmh())
        .multiply(BigDecimal.valueOf(100))
        .divide(BigDecimal.valueOf(leitura.limiteKmh()), 1, RoundingMode.HALF_UP);
  }
}
