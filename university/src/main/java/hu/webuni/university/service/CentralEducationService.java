package hu.webuni.university.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import hu.webuni.university.aspect.Retry;

@Service
public class CentralEducationService {

	private Random random = new Random();

	@Retry(times = 5, waitTime = 500)
	public int getNumFreeSemestersForStudent(int eduId) {
		int rnd = random.nextInt(0, 2);
		if (rnd == 0) {
			throw new RuntimeException("Central Education Service timed out.");
		} else {
			return random.nextInt(0, 10);
		}
	}
}
