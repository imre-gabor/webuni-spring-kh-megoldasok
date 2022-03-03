package hu.webuni.university.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.university.model.Course;
import hu.webuni.university.model.Semester;
import hu.webuni.university.model.Semester.SemesterType;
import hu.webuni.university.model.SpecialDay;
import hu.webuni.university.model.Student;
import hu.webuni.university.model.Teacher;
import hu.webuni.university.model.TimeTableItem;
import hu.webuni.university.repository.CourseRepository;
import hu.webuni.university.repository.SpecialDayRepository;
import hu.webuni.university.repository.StudentRepository;
import hu.webuni.university.repository.TeacherRepository;
import hu.webuni.university.repository.TimeTableItemRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InitDbService {

	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	private final JdbcTemplate jdbcTemplate;
	private final TimeTableItemRepository timeTableItemRepository;
	private final SpecialDayRepository specialDayRepository;
	
	@Transactional
	public void deleteDb() {
		timeTableItemRepository.deleteAll();
		courseRepository.deleteAll();
		studentRepository.deleteAll();
		teacherRepository.deleteAll();
		specialDayRepository.deleteAll();
	}
	
	@Transactional
	public void deleteAudTables() {
		jdbcTemplate.update("DELETE FROM time_table_item_aud");
		jdbcTemplate.update("DELETE FROM special_day_aud");
		jdbcTemplate.update("DELETE FROM teacher_aud");
		jdbcTemplate.update("DELETE FROM course_aud");
		jdbcTemplate.update("DELETE FROM student_aud");
		jdbcTemplate.update("DELETE FROM course_students_aud");
		jdbcTemplate.update("DELETE FROM course_teachers_aud");
		jdbcTemplate.update("DELETE FROM revinfo");
	}

	@Transactional
	public void addInitData() {
		Student student1 = saveNewStudent("student1", LocalDate.of(2000, 10, 10), 1, 111);
		Student student2 = saveNewStudent("student2", LocalDate.of(2000, 10, 10), 2, 222);
		Student student3 = saveNewStudent("student3", LocalDate.of(2000, 10, 10), 3, 333);

		Teacher teacher1 = saveNewTeacher("teacher1", LocalDate.of(2000, 10, 10));
		Teacher teacher2 = saveNewTeacher("teacher2", LocalDate.of(2000, 10, 10));
		Teacher teacher3 = saveNewTeacher("teacher3", LocalDate.of(2000, 10, 10));
		
		Course course1 = createCourse("course1", Arrays.asList(teacher1, teacher2), Arrays.asList(student1, student2, student3), 2022, SemesterType.SPRING);
		Course course2 = createCourse("course2", Arrays.asList(teacher2), Arrays.asList(student1, student3), 2022, SemesterType.SPRING);
		Course course3 = createCourse("course3", Arrays.asList(teacher1, teacher3), Arrays.asList(student2, student3), 2022, SemesterType.SPRING);
		
		addNewTimeTableItem(course1, 1, "10:15", "11:45");
		addNewTimeTableItem(course1, 3, "10:15", "11:45");
		addNewTimeTableItem(course2, 2, "12:15", "13:45");
		addNewTimeTableItem(course2, 4, "10:15", "11:45");
		addNewTimeTableItem(course3, 3, "08:15", "09:45");
		addNewTimeTableItem(course3, 5, "08:15", "09:45");
		
		saveSpecialDay("2022-04-18", null);
		saveSpecialDay("2022-03-15", null);
		saveSpecialDay("2022-03-14", "2022-03-26");
		
		System.out.format("Student ids: %d, %d, %d%n", student1.getId(), student2.getId(), student3.getId());
	}

	private void saveSpecialDay(String sourceDay, String targetDay) {
		specialDayRepository.save(
				SpecialDay.builder()
				.sourceDay(LocalDate.parse(sourceDay))
				.targetDay(targetDay == null ? null : LocalDate.parse(targetDay))
				.build());
			
	}

	private Course createCourse(String name, List<Teacher> teachers, List<Student> students, int year, SemesterType semesterType) {
		return courseRepository.save(				
			Course.builder()
			.name(name)
			.teachers(new HashSet<>(teachers))
			.students(new HashSet<>(students))
			.semester(
				Semester.builder()
					.year(year)
					.semesterType(semesterType)
					.build())
			.build());
	}

	@Transactional
	public void modifyCourse() {
		Course course1 = courseRepository.findByName("course1").get(0);
		course1.setName("course1_mod");
		System.out.println(course1.getId());
	}
	
	private Student saveNewStudent(String name, LocalDate birthdate, int semester, int eduId) {
		return studentRepository.save(
				Student.builder()
					.name(name)
					.birthdate(birthdate)
					.semester(semester)
					.eduId(eduId)
					.build());
	}
	
	private Teacher saveNewTeacher(String name, LocalDate birthdate) {
		return teacherRepository.save(
				Teacher.builder()
					.name(name)
					.birthdate(birthdate)
					.build());
	}
	
	private void addNewTimeTableItem(Course course, int dayOfWeek, String startLession, String endLession) {
		course.addTimeTableItem(timeTableItemRepository.save(
			TimeTableItem.builder()
			.dayOfWeek(dayOfWeek)
			.startLesson(LocalTime.parse(startLession))
			.endLesson(LocalTime.parse(endLession))
			.build()
			));
	}
}
