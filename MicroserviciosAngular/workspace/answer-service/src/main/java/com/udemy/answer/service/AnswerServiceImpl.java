package com.udemy.answer.service;

//import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.udemy.answer.client.ExamFeingClient;
import com.udemy.answer.entity.Answer;
import com.udemy.answer.repository.AnswerRepository;
//import com.udemy.common.entity.exam.Exam;
//import com.udemy.common.entity.exam.Question;

@Service
public class AnswerServiceImpl implements AnswerService{
	
	@Autowired
	private AnswerRepository answerRepository;
	
//	@Autowired
//	private ExamFeingClient examFeingClient;

	@Override
	@Transactional
	public Iterable<Answer> saveAll(Iterable<Answer> answers) {
		return answerRepository.saveAll(answers);
	}

	@Override
	//@Transactional(readOnly = true)
	public Iterable<Answer> findAnswerByAlumnoByExam(Long alumnoId, Long examenId) {
		/*
		Exam exam = examFeingClient.getExamById(examenId);
		List<Question> questions = exam.getQuestions();
		
		List<Long> questiosId = questions.stream().map(p -> p.getId()).collect(Collectors.toList());
		List<Answer> answers = (List<Answer>)answerRepository.findAnswerByAlumnoByQuestionIds(alumnoId, questiosId);
		System.out.println("answers"+answers.size());
		
		answers = answers.stream().map(r -> {
			questions.forEach(p -> {
				if(r.getQuestionId() == p.getId()) {
					r.setQuestion(p);
				}
			});
			return r;
		}).collect(Collectors.toList());
		*/
		List<Answer> answers = (List<Answer>)answerRepository.findAnswerByAlumnByExam(alumnoId, examenId);
		
		return answers;
		//return answerRepository.findAnswerByAlumnoByExam(alumnoId, examenId);
	}

	@Override
	//@Transactional(readOnly = true)
	public Iterable<Long> findExamIdsWithAnswersByAlumno(Long alumnoId) {
		//return answerRepository.findExamIdsWithAnswersByAlumno(alumnoId);
		
		/*List<Answer> respuestasAlumno = (List<Answer>) answerRepository.findByAlumnoId(alumnoId);
		List<Long> examenIds = Collections.emptyList();
		
		if(respuestasAlumno.size() > 0) {
		  List<Long> preguntaIds = respuestasAlumno.stream().map(r -> r.getQuestionId()).collect(Collectors.toList());
		  examenIds = examFeingClient.obtenerExamenesIdsPorPreguntasIdRespondidas(preguntaIds);
		}
		*/
		
		List<Answer> respuestasAlumno = (List<Answer>) answerRepository.findExamsIdsWithAnswersByAlumn(alumnoId);
		List<Long> examenIds = respuestasAlumno.stream().map(r -> r.getQuestion().getExam().getId()).distinct()
				.collect(Collectors.toList());
		return examenIds;
	}

	@Override
	public Iterable<Answer> findByAlumnoId(Long alumnoId) {
		return answerRepository.findByAlumnoId(alumnoId);
	}

}
