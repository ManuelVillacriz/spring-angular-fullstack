package com.udemy.exam.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udemy.common.entity.exam.Exam;
import com.udemy.common.service.CommonServiceImpl;
import com.udemy.exam.repository.ExamRepository;

@Service
public class ExamServiceImpl extends CommonServiceImpl<Exam, ExamRepository> implements ExamService{	

	@Override
	@Transactional(readOnly = true)
	public List<Exam> findByNombre(String term) {
		return repository.findByNombre(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Long> findExamsIdsWithAnswersByQuestionIds(Iterable<Long> questionIds) {
		return repository.findExamsIdsWithAnswersByQuestionIds(questionIds);
	}
}
