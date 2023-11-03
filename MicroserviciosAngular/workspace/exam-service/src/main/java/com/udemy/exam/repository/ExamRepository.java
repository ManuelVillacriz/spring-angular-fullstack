package com.udemy.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.udemy.common.entity.exam.Exam;


public interface ExamRepository extends JpaRepository<Exam, Long>{
	
	@Query("select e from Exam e where e.nombre like %?1%")
	public List<Exam> findByNombre(String term);
	
	@Query("select e.id from Question p join p.exam e where p.id in ?1 group by e.id")
	public Iterable<Long> findExamsIdsWithAnswersByQuestionIds(Iterable<Long> questionIds);

}
