package hu.webuni.university.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import hu.webuni.university.model.Course;
import hu.webuni.university.model.QCourse;
import hu.webuni.university.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
	
	private final CourseRepository courseRepository;
	
	@Transactional
	public List<Course> searchCourses(Predicate predicate, Pageable pageable) {
		//with no paging
//		List<Course> courses = courseRepository.findAll(predicate, "Course.students");
//		courses = courseRepository.findAll(QCourse.course.in(courses), "Course.teachers");
		
		Page<Course> coursePage = courseRepository.findAll(predicate, pageable);
		BooleanExpression inPredicate = QCourse.course.in(coursePage.getContent());
		List<Course> courses = courseRepository.findAll(inPredicate, "Course.teachers");
		courses = courseRepository.findAll(inPredicate, "Course.students");
		return courses;
	}
}
