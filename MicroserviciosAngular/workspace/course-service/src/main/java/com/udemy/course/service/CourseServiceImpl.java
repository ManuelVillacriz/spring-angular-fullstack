package com.udemy.course.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udemy.common.entity.Alumno;
import com.udemy.common.service.CommonServiceImpl;
import com.udemy.course.client.AlumnoFeingClient;
import com.udemy.course.client.ResponseFeingClient;
import com.udemy.course.entity.Course;
import com.udemy.course.repository.CourseRepository;

@Service
public class CourseServiceImpl extends CommonServiceImpl<Course,CourseRepository > implements CourseService {

	@Autowired
	private ResponseFeingClient client;
	
	@Autowired
	private AlumnoFeingClient alumnoFeingClient;
	
	@Override
	@Transactional(readOnly = true)
	public Course findCourseByAlumnoId(Long id) {
		return repository.findCourseByAlumnoId(id);
	}

	@Override
	public Iterable<Long> obtenerExamenesConRespuestasAlumnos(Long alumnoId) {
		return client.obtenerExamenesConRespuestasAlumnos(alumnoId);
	}

	@Override
	public List<Alumno> obtenerAlumnosPorCurso(List<Long> ids) {
		return alumnoFeingClient.obtenerAlumnosPorCurso(ids);
	}

	@Override
	@Transactional
	public void eliminarCursoAlumnoPorId(Long id) {
		repository.eliminarCursoAlumnoPorId(id); 
	}
}
