package hu.webuni.university.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import hu.webuni.university.model.Student;
import hu.webuni.university.model.UniversityUser;
import hu.webuni.university.repository.UserRepository;
import hu.webuni.university.security.UniversityUserDetailsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class FacebookLoginService {
	
	private static final String FB_BASE_URI = "https://graph.facebook.com/v13.0";
	
	private final UserRepository userRepository;
	

	@Getter
	@Setter
	public static class FacebookData {
		private long id;
		private String email;
	}
	
	@Transactional
	public UserDetails getUserDetailsForToken(String fbToken) {
		
		FacebookData facebookData = getFacebookDataForToken(fbToken);
		UniversityUser universityUser = findOrCreateUser(facebookData);
		return UniversityUserDetailsService.createUserDetails(universityUser);
	}

	private UniversityUser findOrCreateUser(FacebookData facebookData) {
		String fbId = String.valueOf(facebookData.getId());
		Optional<UniversityUser> optionalExistingUser = userRepository.findByFacebookId(fbId);
		if(optionalExistingUser.isEmpty()) {
			
			return userRepository.save(Student.builder()
			.facebookId(fbId)
			.username(facebookData.getEmail())
			.password("dummy")
			.courses(Collections.emptySet())
			.build());
		}
		
		return optionalExistingUser.get();
	}

	private FacebookData getFacebookDataForToken(String fbToken) {
		
		return WebClient.create(FB_BASE_URI)
				.get()
				.uri(uriBuilder -> uriBuilder
						.path("/me")
						.queryParam("fields", "email,name")
						.build())
				.headers(headers -> headers.setBearerAuth(fbToken))
				.retrieve()
				.bodyToMono(FacebookData.class)
				.block();
	}
}
