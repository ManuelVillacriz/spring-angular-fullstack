package com.udemy.course.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.udemy.common.entity.Alumno;
import com.udemy.common.entity.exam.Exam;

@Entity
@Table(name = "cursos")
@Data
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nombre;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@JsonIgnoreProperties(value = {"course", "handler", "hibernateLazyInitializer"},allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseAlumn> coursesAlumns;
	
	//@OneToMany(fetch = FetchType.LAZY)
	@Transient
	private List<Alumno> alumnos;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Exam> examenes;
	
	public Course() {
		this.alumnos = new ArrayList<>();
		this.examenes = new ArrayList<>();
		this.coursesAlumns = new ArrayList<>();
	}	
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	public void addAlumno(Alumno alumno) {
		this.alumnos.add(alumno);
	}
	
	public void removeAlumno(Alumno alumno) {
		this.alumnos.remove(alumno);
	}
	
	public void addExam(Exam exam) {
		this.examenes.add(exam);
	}
	
	public void removeExam(Exam exam) {
		this.examenes.remove(exam);
	}
	
	public void addCourseAlumn(CourseAlumn courseAlumn) {
		this.coursesAlumns.add(courseAlumn);
	}
	
	public void removeCourseAlumn(CourseAlumn courseAlumn) {
		this.coursesAlumns.remove(courseAlumn);
	}
}
