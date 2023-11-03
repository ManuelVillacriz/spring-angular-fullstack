package com.udemy.common.entity.exam;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "subjects")
@Data
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String nombre;
	
	@JsonIgnoreProperties(value = {"hijos","handler","hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Subject padre;
	
	@JsonIgnoreProperties(value = {"padre","handler","hibernateLazyInitializer"}, allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "padre", cascade = CascadeType.ALL)
	private List<Subject> hijos;
	
	public Subject() {
		this.hijos= new ArrayList<>();
	}
	
}
