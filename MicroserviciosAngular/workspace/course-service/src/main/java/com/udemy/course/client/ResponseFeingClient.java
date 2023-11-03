package com.udemy.course.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "answer-service")
public interface ResponseFeingClient {
	
	@GetMapping("/answers/alumno/{alumnoId}/examenes-respondidos")
	public Iterable<Long> obtenerExamenesConRespuestasAlumnos(@PathVariable Long alumnoId);

}
