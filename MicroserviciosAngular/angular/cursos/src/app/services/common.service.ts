import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Alumno } from '../models/alumno';
import { map } from 'rxjs/operators'
import { Generic } from '../models/generic';

@Injectable({
  providedIn: 'root'
})
export abstract class CommonService<E extends Generic> {

  protected baseEndPoint: string;

  protected cabeceras: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(protected http: HttpClient) { }

  public listar(): Observable<E[]> {
    return this.http.get<E[]>(this.baseEndPoint);
  }

  public listarPaginas(page: string, size: string): Observable<any>{
    const params = new HttpParams()
    .set('page',page)
    .set('size',size);
    return this.http.get<any>(`${this.baseEndPoint}/paginable`, { params: params });
  }

  public ver(id: number): Observable<E>{
    return this.http.get<E>(`${this.baseEndPoint}/${id}`);
  }

  public crear(e: E): Observable<E> {
    return this.http.post<E>(this.baseEndPoint, e, { headers: this.cabeceras });
  }

  public editar(e: E): Observable<E> {
    return this.http.put<E>(`${this.baseEndPoint}/${e.id}`,e, { headers: this.cabeceras });
  }

  public eliminar(id: number): Observable<void>{
    return this.http.delete<void>(`${this.baseEndPoint}/${id}`);
  }
}
