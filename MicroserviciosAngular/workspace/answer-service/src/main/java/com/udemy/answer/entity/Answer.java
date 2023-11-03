package com.udemy.answer.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.udemy.common.entity.Alumno;
import com.udemy.common.entity.exam.Question;

import lombok.Data;

//@Entity
//@Table(name = "respuestas")
@Data
@Document(collection = "respuestas")
public class Answer {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//private Long id;
	@Id
	private String id;
	
	private String texto;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	//@Transient
	//@Transient
	private Alumno alumno;
	
	//@Column(name = "alumno_id")
	private Long alumnoId;
	
	//@OneToOne(fetch = FetchType.LAZY)
	//@Transient
	private Question question;
	
	private Long questionId;
	

}
