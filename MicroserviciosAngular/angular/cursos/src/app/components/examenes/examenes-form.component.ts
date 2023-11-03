import { Asignatura } from './../../models/asignatura';
import { Component } from '@angular/core';
import { CommonFormComponent } from '../common-form.component';
import { Examen } from 'src/app/models/examen';
import { ExamenService } from 'src/app/services/examen.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Pregunta } from 'src/app/models/pregunta';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-examenes-form',
  templateUrl: './examenes-form.component.html',
  styleUrls: ['./examenes-form.component.css']
})
export class ExamenesFormComponent extends CommonFormComponent<Examen, ExamenService>{

asignaturasPadre: Asignatura[]=[];
asignaturasHija: Asignatura[]=[];
errorPreguntas: string;

  constructor(service: ExamenService,
    router: Router,
    route: ActivatedRoute) {
    super(service, router, route);
    this.titulo = "Crear Examenes";
    this.model = new Examen();
    this.redirect = '/examenes';
    this.nombreModel = Examen.name;
  }

  override ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = +params.get('id');
      if(id){
        this.service.ver(id).subscribe(model => {
          this.model = model;
          this.titulo = 'Editar ' + this.nombreModel;
          this.cargarHijos();

          /*
          this.service.findAllAsignaturas().subscribe(asignaturas =>
            this.asignaturasHija = asignaturas
              .filter(a => a.padre && a.padre.id === this.model.subjectPadre.id));
          */

        });
      }
    });

    this.service.findAllAsignaturas()
    .subscribe(asignaturas => this.asignaturasPadre = asignaturas.filter(a => !a.padre));
  }

  public override crear(): void{
    if(this.model.questions.length === 0){
      this.errorPreguntas = 'Examen debe tener preguntas';
      //Swal.fire('Error Preguntas','Examen debe tener preguntas','error');
      return;
    }
    this.errorPreguntas = undefined;
    this.eliminarPreguntasVacias();
    super.crear();
  }

  public override editar(): void{
    if(this.model.questions.length === 0){
      this.errorPreguntas = 'Examen debe tener preguntas';
      //Swal.fire('Error Preguntas','Examen debe tener preguntas','error');
      return;
    }
    this.errorPreguntas = undefined;
    this.eliminarPreguntasVacias();
    super.editar();
  }

  public cargarHijos():void{
    this.asignaturasHija=this.model.subjectPadre? this.model.subjectPadre.hijos: [];
  }

  public compararAsignatura(a1:Asignatura, a2:Asignatura ):boolean{
    if(a1 === undefined && a2 === undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined) ? false : (a1.id === a2.id)
  }

  public agregarPregunta():void{
    this.model.questions.push(new Pregunta());
  }

  public asignarTexto(pregunta: Pregunta, event: any):void{
    pregunta.texto = event.target.value as string;
    console.log(this.model);
  }

  public eliminarPregunta(pregunta: Pregunta):void{
    this.model.questions = this.model.questions.filter(p => pregunta.texto !== p.texto);
  }

  public eliminarPreguntasVacias():void{
    this.model.questions = this.model.questions.filter(p => p.texto != null && p.texto.length > 0);
  }
}
