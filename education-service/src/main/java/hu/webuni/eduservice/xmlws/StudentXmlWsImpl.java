package hu.webuni.eduservice.xmlws;

import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentXmlWsImpl implements StudentXmlWs{
	
	private Random random = new Random();

	@Override
	public int getFreeSemesterByStudent(int studentId) {
		int count = random.nextInt(0, 10);
		return count;
	}
	
}
