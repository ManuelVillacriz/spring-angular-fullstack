package com.udemy.course.service;

import java.util.List;

import com.udemy.common.entity.Alumno;
import com.udemy.common.service.CommonService;
import com.udemy.course.entity.Course;

public interface CourseService extends CommonService<Course> {

	public Course findCourseByAlumnoId(Long id);

	public Iterable<Long> obtenerExamenesConRespuestasAlumnos(Long alumnoId);

	public List<Alumno> obtenerAlumnosPorCurso(List<Long> ids);
	
	public void eliminarCursoAlumnoPorId(Long id);
}
