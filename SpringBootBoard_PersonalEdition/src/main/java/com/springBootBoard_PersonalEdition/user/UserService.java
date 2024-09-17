package com.springBootBoard_PersonalEdition.user;

import java.security.Principal;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springBootBoard_PersonalEdition.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public SiteUser create(String username, String email, String password) {

		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));

		this.userRepository.save(user);

		return user;
	}

	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByusername(username);

		if (siteUser.isPresent()) {
			return siteUser.get();
		} else {
			throw new DataNotFoundException("User not found.");
		}
	}

	public boolean isPasswordMatching(Principal principal, String password) {
		if (principal == null || principal.getName() == null) {
	        throw new IllegalArgumentException("Invalid principal");
	    }
		
		Optional<SiteUser> userOptional  = this.userRepository.findByusername(principal.getName());

		if (!userOptional .isPresent()) {
			throw new DataNotFoundException("User not found.");
		}

		SiteUser user = userOptional.get();
		String storedPassword = user.getPassword();
		
		return passwordEncoder.matches(password, storedPassword);
	}

	public void updatePassword(Principal principal, String password) {
		Optional<SiteUser> user = this.userRepository.findByusername(principal.getName());
		
		user.get().setPassword(passwordEncoder.encode(password));
	}
}
