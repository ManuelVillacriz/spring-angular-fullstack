import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Alumno } from 'src/app/models/alumno';
import { Curso } from 'src/app/models/curso';
import { AlumnoService } from 'src/app/services/alumno.service';
import { CursoService } from 'src/app/services/curso.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-asignar-alumnos',
  templateUrl: './asignar-alumnos.component.html',
  styleUrls: ['./asignar-alumnos.component.css']
})
export class AsignarAlumnosComponent implements OnInit{

  curso: Curso;
  alumnosAsignar: Alumno[]=[];
  alumnos: Alumno[]=[];
  mostrarColumnas: string[]=['nombre','apellido','seleccion'];
  mostrarColumnasAlumnos: string[]=['id','nombre','apellido','email','eliminar'];
  seleccion: SelectionModel<Alumno> = new SelectionModel<Alumno>(true,[]);
  tabIndex=0;

  dataSource: MatTableDataSource<Alumno>;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  pageSizeOptions: number[] = [3, 5, 10, 20, 50];

  constructor(private route: ActivatedRoute,
    private cursoService: CursoService, private alumnoService: AlumnoService){}

    ngOnInit(): void {
      this.route.paramMap.subscribe(params =>{
        const id: number = +params.get('id');
        this.cursoService.ver(id).subscribe(c => {
          this.curso = c;
          this.alumnos= this.curso.alumnos;
          this.iniciarPaginador();
        });
      });
    }

    private iniciarPaginador(): void{
      this.dataSource = new MatTableDataSource<Alumno>(this.alumnos);
      this.dataSource.paginator = this.paginator;
      this.paginator._intl.itemsPerPageLabel = 'Registros por página';
    }

    public filtrar(nombre: string):void{
      nombre = nombre !== undefined? nombre.trim():'';

      if (nombre !== ''){
        this.alumnoService.filtrarPorNombre(nombre).subscribe(alumnos =>{
          this.alumnosAsignar = alumnos.filter(a =>{
            let filtrar = true;
            this.alumnos.forEach(ca => {
              if(a.id === ca.id){
                filtrar = false;
              }
            });
            return filtrar;
          });
        });
      }
    }

    public seleccionarDeseleccionarTodos():void{
      this.estanTodosSeleccionados()?
      this.seleccion.clear():
      this.alumnosAsignar.forEach(a => this.seleccion.select(a));
    }

  public estanTodosSeleccionados(): boolean {
    const seleccionados = this.seleccion.selected.length;
    const numeroAlumnos = this.alumnosAsignar.length;

    return (seleccionados === numeroAlumnos);
  }

  public asignar():void{
    this.cursoService.asignarAlumnos(this.curso, this.seleccion.selected).
    subscribe(c => {
      this.tabIndex = 2;
      Swal.fire('Asignados',`Alumnos asignados con exito al curso ${this.curso.nombre}`,
      'success');
      this.alumnos = this.alumnos.concat(this.seleccion.selected);
      this.iniciarPaginador();
      this.alumnosAsignar =[];
      this.seleccion.clear();
    },
    e => {
      console.log(e);
      if(e.status === 500){
        //const mensaje = e.error.message as string;
        const mensajeTrace =e.error.trace as string;
        if( mensajeTrace.indexOf('ConstraintViolationException') > -1){
          Swal.fire(
            'Cuidado:',
            'No se puede asignar el alumno ya está asociado a otro curso.',
            'error'
          );
        }
      }
    });
  }

  public eliminarAlumno(alumno: Alumno): void {
    console.log(alumno);
    Swal.fire({
      title: 'Cuidado:',
      text: `¿Seguro que desea eliminar a ${alumno.nombre} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!'
    }).then((result) => {
      if (result.value) {

        this.cursoService.eliminarAlumno(this.curso, alumno)
        .subscribe(curso => {
          this.alumnos = this.alumnos.filter(a => a.id !== alumno.id);
          this.iniciarPaginador();
          Swal.fire(
            'Eliminado:',
            `Alumno ${alumno.nombre} eliminado con éxito del curso ${curso.nombre}.`,
            'success'
          );
        });

      }
    });


  }

}
