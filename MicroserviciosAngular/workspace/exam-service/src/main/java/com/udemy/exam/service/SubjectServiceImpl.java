package com.udemy.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udemy.common.entity.exam.Subject;
import com.udemy.exam.repository.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService{

	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Subject> findAllAsignaturas() {
		return subjectRepository.findAll();
	}
}
