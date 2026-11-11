package com.example.josevitor.backend.model;

import java.util.List;

public record ResultadoAnalise(
  ResumoLeitura resumo,
  List<ItemRanking> ranking,
  List<Episodio> episodios,
  List<LinhaInvalida> linhasInvalidas
) {
}
