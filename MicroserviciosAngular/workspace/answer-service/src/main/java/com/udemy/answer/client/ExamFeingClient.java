package com.udemy.answer.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.udemy.common.entity.exam.Exam;

@FeignClient(name = "exam-service")
public interface ExamFeingClient {

	@GetMapping("/exams/{id}")
	public Exam getExamById(@PathVariable Long id);
	
	@GetMapping("/exams/respondidos-por-preguntas")
	public List<Long> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> questionIds);
}
