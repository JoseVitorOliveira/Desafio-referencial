export type Gravidade = 'LEVE' | 'GRAVE' | 'GRAVISSIMA';

export interface Resumo {
  linhasLidas: number;
  linhasValidas: number;
  totalEpisodios: number;
}

export interface ItemRanking {
  placa: string;
  pontos: number;
  velocidadeMaxima: number;
  quantidadeEpisodios: number;
}

export interface Episodio {
  placa: string;
  inicio: string;
  fim: string;
  duracaoSegundos: number;
  velocidadeMaxima: number;
  excessoMaximo: number;
  gravidade: Gravidade;
}

export interface LinhaInvalida {
  linha: number;
  motivo: string;
}

export interface ResultadoAnalise {
  resumo: Resumo;
  ranking: ItemRanking[];
  episodios: Episodio[];
  linhasInvalidas: LinhaInvalida[];
}
