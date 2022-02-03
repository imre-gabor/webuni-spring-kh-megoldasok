package hu.webuni.university.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.university.dto.CourseDto;
import hu.webuni.university.model.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {

	CourseDto courseToDto(Course course);

	Course dtoToCourse(CourseDto courseDto);

	List<CourseDto> coursesToDtos(Iterable<Course> courses);
}
