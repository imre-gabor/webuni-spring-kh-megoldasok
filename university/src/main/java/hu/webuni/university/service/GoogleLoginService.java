package hu.webuni.university.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.university.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {
	
	private static final String GOOGLE_BASE_URI = "https://oauth2.googleapis.com";
	
	private final UserRepository userRepository;
	
	@Value("${university.google-client-id}")
	private String googleClientId;
	
	@Getter
	@Setter
	public static class GoogleData {
		private String sub;
		private String email;

		private String aud;

		private String iss;
		private String azp;
		private String email_verified;
		private String at_hash;
		private String name;
		private String picture;
		private String given_name;
		private String family_name;
		private String locale;
		private String iat;
		private String exp;
		private String jti;
		private String alg;
		private String kid;
		private String typ;
	}
	
	@Transactional
	public UserDetails getUserDetailsForToken(String googleToken) {
		return null;
	}
}
