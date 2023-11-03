package com.udemy.user.service;

import java.util.List;

import com.udemy.common.entity.Alumno;
import com.udemy.common.service.CommonService;

public interface AlumnoService extends CommonService<Alumno> {

	public List<Alumno> findByNombreOrApellido(String term);

	public Iterable<Alumno> findAllById(Iterable<Long> ids);

	public void deleteCourseAlumnById(Long id);
}
