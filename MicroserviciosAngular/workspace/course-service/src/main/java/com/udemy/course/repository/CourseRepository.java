package com.udemy.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.udemy.course.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
	
	@Query("select c from Course c join fetch c.coursesAlumns a where a.alumnoId = ?1")
	public Course findCourseByAlumnoId(Long id);
	

	@Modifying
	@Query("delete from CourseAlumn ca where ca.alumnoId = ?1")
	public void eliminarCursoAlumnoPorId(Long id);
}
