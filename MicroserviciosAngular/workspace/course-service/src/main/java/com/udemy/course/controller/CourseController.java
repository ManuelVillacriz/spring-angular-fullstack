package com.udemy.course.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.common.controller.CommonController;
import com.udemy.common.entity.Alumno;
import com.udemy.common.entity.exam.Exam;
import com.udemy.course.entity.Course;
import com.udemy.course.entity.CourseAlumn;
import com.udemy.course.service.CourseService;

@RestController
@RequestMapping(value="/courses")
public class CourseController  extends CommonController<Course, CourseService>{
	
	@Value("${config.balanceador.test}")
	private String balanceadorTest;
	
	@DeleteMapping("/eliminar-alumno-curso/{id}")
	public ResponseEntity<?> deleteCourseAlumnById(@PathVariable Long id){
		service.eliminarCursoAlumnoPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	@Override
	public ResponseEntity<?> listAll() {
		List<Course> courses = ((List<Course>) service.findAll()).stream().map(c -> {
			c.getCoursesAlumns().forEach(ca -> {
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				c.addAlumno(alumno);
			});
			return c;
		}).collect(Collectors.toList());

		return ResponseEntity.ok().body(courses);
	}
	
	@GetMapping("/paginable")
	@Override
	public ResponseEntity<?> listAll(Pageable pageable){
		Page<Course> courses = service.findAll(pageable).map(course ->{
		course.getCoursesAlumns().forEach(ca -> {
			Alumno alumno = new Alumno();
			alumno.setId(ca.getAlumnoId());
			course.addAlumno(alumno);
		});
		return course;
		});		
				
		return ResponseEntity.ok().body(courses);
	}
	
	@GetMapping("/{id}")
	@Override
	public ResponseEntity<?> listById(@PathVariable(name = "id") Long id){
		Optional<Course> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Course course = o.get();
		
		if(course.getCoursesAlumns().isEmpty() == false) {
			List<Long> ids = course.getCoursesAlumns().stream().map(ca -> {
				return ca.getAlumnoId();
			}).collect(Collectors.toList());
			
			List<Alumno> alumnos = (List<Alumno>) service.obtenerAlumnosPorCurso(ids);
			course.setAlumnos(alumnos);
		}
		
		return ResponseEntity.ok().body(course);		
	}
	
	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest(){
		Map<String,Object> response = new HashMap<>();
		response.put("balanceadorTest",balanceadorTest);
		response.put("cursos", service.findAll());
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@Validated @RequestBody Course course, BindingResult result,@PathVariable(name = "id") Long id){
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Course> a = service.findById(id);
		
		if(a.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Course courseDb = a.get();
		courseDb.setNombre(course.getNombre());
						
		return  ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDb));
	}
	
	@PutMapping("/{id}/assign-students")
	public ResponseEntity<?> assignStudents(@RequestBody List<Alumno> alumnos, @PathVariable Long id){
		Optional<Course> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Course courseDb = o.get();
		
		alumnos.forEach(a -> {
			
			CourseAlumn courseAlumn = new CourseAlumn();
			courseAlumn.setAlumnoId(a.getId());
			courseAlumn.setCourse(courseDb);
			//courseDb.addAlumno(a);
			courseDb.addCourseAlumn(courseAlumn);
		});
		
		return  ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDb));
		
	}
	
	@PutMapping("/{id}/delete-students")
	public ResponseEntity<?> deleteStudent(@RequestBody Alumno alumno, @PathVariable Long id){
		Optional<Course> o = this.service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Course courseDb = o.get();		
		CourseAlumn courseAlumn = new CourseAlumn();
		System.out.println("alumno.getId()"+alumno.getId());
		courseAlumn.setAlumnoId(alumno.getId());
		
		//courseDb.removeAlumno(alumno);
		courseDb.removeCourseAlumn(courseAlumn);
		
		return  ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDb));
		
	}
	
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> findCourseByAlumnoId(@PathVariable Long id) {
		Course course = service.findCourseByAlumnoId(id);

		if (course != null) {
			List<Long> examnsIds = (List<Long>) service.obtenerExamenesConRespuestasAlumnos(id);

			if (examnsIds != null && examnsIds.size() > 0) {

				List<Exam> examns = course.getExamenes().stream().map(e -> {
					if (examnsIds.contains(e.getId())) {
						e.setAnswered(true);
					}
					return e;
				}).collect(Collectors.toList());
				course.setExamenes(examns);
			}
		}

		return ResponseEntity.ok(course);
	}
	
	@PutMapping("/{id}/assign-exams")
	public ResponseEntity<?> assignExams(@RequestBody List<Exam> exams, @PathVariable Long id){
		Optional<Course> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Course courseDb = o.get();
		
//		exams.forEach(a -> {
//			courseDb.addExam(a);
//		});
		
		exams.forEach(courseDb::addExam);
	
		
		return  ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDb));
		
	}
	
	@PutMapping("/{id}/delete-exams")
	public ResponseEntity<?> deleteExam(@RequestBody Exam exam, @PathVariable Long id){
		Optional<Course> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Course courseDb = o.get();
		
		courseDb.removeExam(exam);		
		
		return  ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDb));
		
	}
	
	
	
}
