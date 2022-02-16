package hu.webuni.university.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import hu.webuni.university.model.Student;
import hu.webuni.university.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {
	
	private final StudentRepository studentRepository;
	private final CentralEducationService centralEducationService;

	@Scheduled(cron="${university.freeSemesterUpdater.cron}")
	public void updateFreeSemesters() {
		List<Student> students = studentRepository.findAll();
		
		students.forEach(student -> {
			System.out.format("Get number of free semesters of student %s%n", student.getName());
	
			try {
				Integer eduId = student.getEduId();
				if(eduId != null) {
					int numFreeSemesters = centralEducationService.getNumFreeSemestersForStudent(eduId);
					student.setNumFreeSemesters(numFreeSemesters);
					studentRepository.save(student);
				}
			} catch (Exception e) {
				log.error("Error calling central education service.", e);
			}
		});
	}
}
