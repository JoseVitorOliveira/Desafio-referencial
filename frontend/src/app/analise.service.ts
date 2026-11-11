import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { ResultadoAnalise } from './analise.model';

@Injectable({ providedIn: 'root' })
export class AnaliseService {
  private readonly http = inject(HttpClient);
  private readonly url = 'http://localhost:8080/api/analises';

  enviar(arquivo: File): Observable<ResultadoAnalise> {
    const formData = new FormData();
    formData.append('arquivo', arquivo);
    return this.http.post<ResultadoAnalise>(this.url, formData);
  }
}
