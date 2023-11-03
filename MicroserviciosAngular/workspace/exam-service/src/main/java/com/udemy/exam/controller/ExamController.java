package com.udemy.exam.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.common.controller.CommonController;
import com.udemy.common.entity.exam.Exam;
import com.udemy.common.entity.exam.Question;
import com.udemy.exam.service.ExamService;
import com.udemy.exam.service.SubjectService;

@RestController
@RequestMapping(value="/exams")
public class ExamController extends CommonController<Exam, ExamService>{
	
	@Autowired
	private SubjectService subjectService;
	
	@GetMapping("/respondidos-por-preguntas")
	public ResponseEntity<?> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> questionIds){
		return ResponseEntity.ok().body(service.findExamsIdsWithAnswersByQuestionIds(questionIds));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@Validated @RequestBody Exam exam, BindingResult result, @PathVariable Long id){
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		
		Optional<Exam> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();			
		}
		
		Exam examDb = o.get();
		examDb.setNombre(exam.getNombre());	
		
//		List<Question> eliminadas = new ArrayList<>();
//		
//		examDb.getQuestions().forEach(p -> {
//			if(!exam.getQuestions().contains(p)) {
//				eliminadas.add(p);
//			}
//		});
		
//		eliminadas.forEach(e -> {
//			examDb.removeQuestion(e);
//		})
		
		List<Question> eliminadas = examDb.getQuestions()
		.stream()
		.filter(p -> !exam.getQuestions().contains(p))
		.collect(Collectors.toList());
		
		eliminadas.forEach(examDb::removeQuestion);
		

		//eliminadas.forEach(examDb::removeQuestion);
		
		examDb.setQuestions(exam.getQuestions());
		examDb.setSubjectPadre(exam.getSubjectPadre());
		examDb.setSubjectHija(exam.getSubjectHija());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examDb));
	}
	
	@GetMapping("/filter/{term}")
	public ResponseEntity<?> filter(@PathVariable String term){
		return ResponseEntity.ok(service.findByNombre(term));
	}
	

	@GetMapping("/subjects")
	public ResponseEntity<?> findAllSubjects(){
		return ResponseEntity.ok(subjectService.findAllAsignaturas());
	}

}
