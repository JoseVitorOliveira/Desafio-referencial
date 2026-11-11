package com.example.josevitor.backend.analise;

import com.example.josevitor.backend.model.Episodio;
import com.example.josevitor.backend.model.ItemRanking;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CalculadoraRanking {

  public List<ItemRanking> calcular(List<Episodio> episodios) {
    Map<String, List<Episodio>> porPlaca = episodios.stream()
        .collect(Collectors.groupingBy(Episodio::placa));

    List<ItemRanking> ranking = new ArrayList<>();
    for (var entrada : porPlaca.entrySet()) {
      int pontos = 0;
      int velocidadeMaxima = 0;
      for (Episodio episodio : entrada.getValue()) {
        pontos += episodio.gravidade().getPontos();
        velocidadeMaxima = Math.max(velocidadeMaxima, episodio.velocidadeMaxima());
      }
      ranking.add(new ItemRanking(entrada.getKey(), pontos, velocidadeMaxima, entrada.getValue().size()));
    }

    ranking.sort(Comparator.comparingInt(ItemRanking::pontos).reversed()
        .thenComparing(Comparator.comparingInt(ItemRanking::velocidadeMaxima).reversed())
        .thenComparing(ItemRanking::placa));

    return ranking;
  }
}
