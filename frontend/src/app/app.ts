import { Component, computed, inject, signal } from '@angular/core';
import { DatePipe, DecimalPipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';

import { AnaliseService } from './analise.service';
import { Gravidade, ResultadoAnalise } from './analise.model';

@Component({
  selector: 'app-root',
  imports: [DatePipe, DecimalPipe],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  private readonly analiseService = inject(AnaliseService);

  protected readonly arquivo = signal<File | null>(null);
  protected readonly resultado = signal<ResultadoAnalise | null>(null);
  protected readonly erro = signal<string | null>(null);
  protected readonly filtro = signal<Gravidade | ''>('');

  protected readonly episodiosFiltrados = computed(() => {
    const episodios = this.resultado()?.episodios ?? [];
    const filtro = this.filtro();
    return filtro ? episodios.filter((episodio) => episodio.gravidade === filtro) : episodios;
  });

  protected selecionarArquivo(event: Event) {
    const input = event.target as HTMLInputElement;
    this.arquivo.set(input.files?.[0] ?? null);
  }

  protected mudarFiltro(event: Event) {
    const select = event.target as HTMLSelectElement;
    this.filtro.set(select.value as Gravidade | '');
  }

  protected enviar() {
    const arquivo = this.arquivo();
    if (!arquivo) {
      return;
    }

    this.erro.set(null);
    this.resultado.set(null);
    this.filtro.set('');

    this.analiseService.enviar(arquivo).subscribe({
      next: (resultado) => this.resultado.set(resultado),
      error: (erro: HttpErrorResponse) =>
        this.erro.set(
          erro.error?.erro ??
            'Não foi possível processar o arquivo. Verifique se o backend está rodando.',
        ),
    });
  }
}
