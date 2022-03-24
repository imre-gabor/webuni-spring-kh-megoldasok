package hu.webuni.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.tokenlib.JwtAuthFilter;

@SpringBootApplication(scanBasePackageClasses = {
	JwtAuthFilter.class,
	UserServiceApplication.class
})
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
