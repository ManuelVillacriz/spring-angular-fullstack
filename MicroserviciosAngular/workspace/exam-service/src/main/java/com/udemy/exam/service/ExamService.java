package com.udemy.exam.service;

import java.util.List;

import com.udemy.common.entity.exam.Exam;
import com.udemy.common.service.CommonService;

public interface ExamService extends CommonService<Exam> {
	
	public List<Exam> findByNombre(String term);
	
	public Iterable<Long> findExamsIdsWithAnswersByQuestionIds(Iterable<Long> questionIds);

}
