import { Component, OnInit } from '@angular/core';
import { CommonListarComponent } from '../common-listar.component';
import { Examen } from 'src/app/models/examen';
import { ExamenService } from 'src/app/services/examen.service';
import { BASE_ENDPOINT } from 'src/app/config/app';

@Component({
  selector: 'app-examenes',
  templateUrl: './examenes.component.html',
  styleUrls: ['./examenes.component.css']
})
export class ExamenesComponent extends CommonListarComponent<Examen,ExamenService> {

  baseEndPoint = BASE_ENDPOINT + '/exams';

  constructor(service: ExamenService){
    super(service);
    this.titulo = 'Listado de Examenes';
    this.nombreModel = Examen.name;
  }

}
