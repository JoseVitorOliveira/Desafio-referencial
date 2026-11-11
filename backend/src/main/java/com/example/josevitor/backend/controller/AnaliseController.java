package com.example.josevitor.backend.controller;

import com.example.josevitor.backend.analise.AnalisadorEpisodios;
import com.example.josevitor.backend.analise.CalculadoraRanking;
import com.example.josevitor.backend.leitor.LeitorCsv;
import com.example.josevitor.backend.leitor.ResultadoLeitura;
import com.example.josevitor.backend.model.Episodio;
import com.example.josevitor.backend.model.ItemRanking;
import com.example.josevitor.backend.model.ResultadoAnalise;
import com.example.josevitor.backend.model.ResumoLeitura;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/analises")
@CrossOrigin(origins = "http://localhost:4200")
public class AnaliseController {

  private final LeitorCsv leitorCsv;
  private final AnalisadorEpisodios analisadorEpisodios;
  private final CalculadoraRanking calculadoraRanking;

  public AnaliseController(LeitorCsv leitorCsv, AnalisadorEpisodios analisadorEpisodios, CalculadoraRanking calculadoraRanking) {
    this.leitorCsv = leitorCsv;
    this.analisadorEpisodios = analisadorEpisodios;
    this.calculadoraRanking = calculadoraRanking;
  }

  @PostMapping
  public ResultadoAnalise analisar(@RequestParam("arquivo") MultipartFile arquivo) throws IOException {
    String conteudo = new String(arquivo.getBytes(), StandardCharsets.UTF_8);

    ResultadoLeitura leitura = leitorCsv.ler(conteudo);
    List<Episodio> episodios = analisadorEpisodios.analisar(leitura.validas());
    List<ItemRanking> ranking = calculadoraRanking.calcular(episodios);
    ResumoLeitura resumo = new ResumoLeitura(leitura.linhasLidas(), leitura.validas().size(), episodios.size());

    return new ResultadoAnalise(resumo, ranking, episodios, leitura.invalidas());
  }
}
