package com.udemy.common.entity.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "examenes")
public class Exam {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min = 4, max=20)
	private String nombre;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@JsonIgnoreProperties(value ={"exam"}, allowSetters = true)
	@OneToMany(mappedBy = "exam", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Question> questions;
	
	@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Subject subjectPadre;
	
	@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Subject subjectHija;
	
	@Transient
	private boolean answered;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	/**
	 * @return the answered
	 */
	public boolean isAnswered() {
		return answered;
	}

	/**
	 * @param answered the answered to set
	 */
	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	public Exam() {
		this.questions = new ArrayList<>();
	}
	
	public void addQuestion(Question question) {
		this.questions.add(question);
		question.setExam(this);
	}
	
	public void removeQuestion(Question question) {
		this.questions.remove(question);
		question.setExam(null);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the createAt
	 */
	public Date getCreateAt() {
		return createAt;
	}

	/**
	 * @param createAt the createAt to set
	 */
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	/**
	 * @return the questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<Question> questions) {
		this.questions.clear();
		questions.forEach(this::addQuestion);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj instanceof Exam)
			return false;		
		
		Exam a = (Exam) obj;
		
		return this.id != null && this.id.equals(a.getId());
	}

	/**
	 * @return the subjectPadre
	 */
	public Subject getSubjectPadre() {
		return subjectPadre;
	}

	/**
	 * @param subjectPadre the subjectPadre to set
	 */
	public void setSubjectPadre(Subject subjectPadre) {
		this.subjectPadre = subjectPadre;
	}

	/**
	 * @return the subjectHija
	 */
	public Subject getSubjectHija() {
		return subjectHija;
	}

	/**
	 * @param subjectHija the subjectHija to set
	 */
	public void setSubjectHija(Subject subjectHija) {
		this.subjectHija = subjectHija;
	}	
}
