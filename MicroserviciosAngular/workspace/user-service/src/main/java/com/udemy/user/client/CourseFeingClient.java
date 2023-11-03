package com.udemy.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("course-service")
public interface CourseFeingClient {
	
	@DeleteMapping("/courses/eliminar-alumno-curso/{id}")
	public void deleteCourseAlumnById(@RequestParam Long id);

}
