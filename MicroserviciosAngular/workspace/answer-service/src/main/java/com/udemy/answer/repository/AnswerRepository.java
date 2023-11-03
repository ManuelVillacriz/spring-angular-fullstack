package com.udemy.answer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.udemy.answer.entity.Answer;

//public interface AnswerRepository extends JpaRepository<Answer, Long>{
@Repository
public interface AnswerRepository extends MongoRepository<Answer, String>{
	
	@Query("{'alumnoId' : ?0, 'questionId' : {$in : ?1}}")
	public Iterable<Answer> findAnswerByAlumnoByQuestionIds(Long alumnoId, Iterable<Long> questionIds);
	
	@Query("{'alumnoId': ?0}")
	public Iterable<Answer> findByAlumnoId(Long alumnoId);
	
	//@Query("select r from Answer r join fetch r.alumno a join fetch r.question q join fetch q.exam e where a.id = ?1 and e.id = ?2" )
	//@Query("select r from Answer r join fetch r.question q join fetch q.exam e where r.alumnoId = ?1 and e.id = ?2" )	
	//public Iterable<Answer> findAnswerByAlumnoByExam(Long alumnoId, Long examenId);
	
	//@Query("select e.id from Answer r join r.alumno a join r.question p join p.exam e where a.id = ?1 group by e.id ")
	//@Query("select e.id from Answer r join r.question p join p.exam e where r.alumnoId = ?1 group by e.id ")
	//public Iterable<Long> findExamIdsWithAnswersByAlumno(Long alumnoId);
	
	@Query("{'alumnoId': ?0, 'question.exam.id': ?1}")
	public Iterable<Answer> findAnswerByAlumnByExam(Long alumnoId, Long examenId);
	
	@Query(value = "{'alumnoId': ?0}", fields = "{'question.exam.id': 1}")
	public Iterable<Answer> findExamsIdsWithAnswersByAlumn(Long alumnoId);

}
