package com.example.josevitor.backend.leitor;

import com.example.josevitor.backend.model.LinhaInvalida;
import com.example.josevitor.backend.model.LinhaLeitura;

import java.util.List;

public record ResultadoLeitura(
  int linhasLidas,
  List<LinhaLeitura> validas,
  List<LinhaInvalida> invalidas
) {
}
