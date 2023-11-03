import { Injectable } from '@angular/core';
import { Curso } from '../models/curso';
import { CommonService } from './common.service';
import { HttpClient } from '@angular/common/http';
import { BASE_ENDPOINT } from '../config/app';
import { Alumno } from '../models/alumno';
import { Observable } from 'rxjs';
import { Examen } from '../models/examen';

@Injectable({
  providedIn: 'root'
})
export class CursoService extends CommonService<Curso> {

  protected override  baseEndPoint = BASE_ENDPOINT + '/courses';

  constructor(http: HttpClient){
    super(http);
  }

  public asignarAlumnos(curso: Curso, alumnos: Alumno[]): Observable<Curso>{
    return this.http.put<Curso>(`${this.baseEndPoint}/${curso.id}/assign-students`,
    alumnos,
    {headers: this.cabeceras});
  }

  eliminarAlumno(curso: Curso, alumno: Alumno): Observable<Curso> {
    return this.http.put<Curso>(`${this.baseEndPoint}/${curso.id}/delete-students`,
    alumno,
    {headers: this.cabeceras});
  }

  asignarExamenes(curso: Curso, examenes: Examen[]): Observable<Curso>{
    return this.http.put<Curso>(`${this.baseEndPoint}/${curso.id}/assign-exams`,
    examenes,
    {headers: this.cabeceras});
  }

  eliminarExamen(curso: Curso, examen: Examen):Observable<Curso>{
    return this.http.put<Curso>(`${this.baseEndPoint}/${curso.id}/delete-exams`,
    examen,
    {headers: this.cabeceras});
  }

  obtenerCursoPorAlumnoId(alumno: Alumno): Observable<Curso> {
    return this.http.get<Curso>(`${this.baseEndPoint}/alumno/${alumno.id}`);
  }
}
