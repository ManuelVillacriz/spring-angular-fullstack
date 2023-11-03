package com.udemy.course.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.udemy.common.entity.Alumno;

@FeignClient(name = "user-service")
public interface AlumnoFeingClient {
	
	@GetMapping("/alumnos/alumnos-por-curso")
	public List<Alumno> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);

}
