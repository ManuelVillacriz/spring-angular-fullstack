package com.udemy.exam.repository;

import org.springframework.data.repository.CrudRepository;

import com.udemy.common.entity.exam.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

}
