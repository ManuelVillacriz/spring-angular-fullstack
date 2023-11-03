package com.udemy.answer.service;

import com.udemy.answer.entity.Answer;

public interface AnswerService {
	
	public Iterable<Answer> saveAll(Iterable<Answer> answers);
	
	public Iterable<Answer> findAnswerByAlumnoByExam(Long alumnoId, Long examenId);
	
	public Iterable<Long> findExamIdsWithAnswersByAlumno(Long alumnoId);
	
	public Iterable<Answer> findByAlumnoId(Long alumnoId);

}
