import { Component, OnInit } from '@angular/core';
import { CommonListarComponent } from '../common-listar.component';
import { Curso } from 'src/app/models/curso';
import { CursoService } from '../../services/curso.service';
import { BASE_ENDPOINT } from 'src/app/config/app';

@Component({
  selector: 'app-cursos',
  templateUrl: './cursos.component.html',
  styleUrls: ['./cursos.component.css']
})
export class CursosComponent extends CommonListarComponent<Curso,CursoService> {

  baseEndPoint = BASE_ENDPOINT + '/cursos';

  constructor(service:CursoService){
    super(service);
    this.titulo = 'Listado de Cursos';
    this.nombreModel = Curso.name;
  }

}
