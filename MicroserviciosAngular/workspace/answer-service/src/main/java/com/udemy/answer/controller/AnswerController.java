package com.udemy.answer.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.answer.entity.Answer;
import com.udemy.answer.service.AnswerService;

@RestController
@RequestMapping(value="/answers")
public class AnswerController {
	
	@Autowired
	private AnswerService service;
	
	@PostMapping
	private ResponseEntity<?> create(@RequestBody Iterable<Answer> answers){
		answers = ((List<Answer>)answers).stream().map(r -> {
			r.setAlumnoId(r.getAlumno().getId());
			r.setQuestionId(r.getQuestion().getId());
			return r;
		}).collect(Collectors.toList());
		
		Iterable<Answer> answersDb = service.saveAll(answers);				
		return ResponseEntity.status(HttpStatus.CREATED).body(answersDb);
	}
	
	@GetMapping("/alumno/{alumnoId}/exam/{examId}")
	public ResponseEntity<?> obtenerRespuestasPorAlumnoExamen(@PathVariable Long alumnoId, @PathVariable Long examId){		
		Iterable<Answer> answres = service.findAnswerByAlumnoByExam(alumnoId, examId);		
		return ResponseEntity.ok(answres);
	}
	
	
	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public ResponseEntity<?> obtenerExamenesConRespuestasAlumnos(@PathVariable Long alumnoId){
		Iterable<Long> examenesIds = service.findExamIdsWithAnswersByAlumno(alumnoId);
		return ResponseEntity.ok(examenesIds);
		
	}
}
