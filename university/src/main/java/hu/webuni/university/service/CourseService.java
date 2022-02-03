package hu.webuni.university.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import hu.webuni.university.model.Course;
import hu.webuni.university.model.QCourse;
import hu.webuni.university.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
	
	private final CourseRepository courseRepository;
	
	
	@Transactional
	public List<Course> searchCourses(Predicate predicate){
		List<Course> courses = courseRepository.findAll(predicate, "Course.students");
		courses =courseRepository.findAll(QCourse.course.in(courses), "Course.teachers");
		return courses;
	}
}
