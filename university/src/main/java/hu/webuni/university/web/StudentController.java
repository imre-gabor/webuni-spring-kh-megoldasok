package hu.webuni.university.web;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hu.webuni.airport.api.StudentControllerApi;
import hu.webuni.university.service.StudentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentControllerApi {

	private final StudentService studentService;

	@Override
	public ResponseEntity<Resource> getProfilePicture(Integer id) {

		return ResponseEntity.ok(studentService.getProfilePicture(id));
	}

	@Override
	public ResponseEntity<Void> uploadProfilePicture(Integer id, @Valid MultipartFile content) {
		try {
			studentService.saveProfilePicture(id, content.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok().build();
	}
	
	
}
