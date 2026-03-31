package edu.esiea.stellarsystemapi.security.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.esiea.stellarsystemapi.security.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserService userService;
	
	public CustomUserDetailsService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> opt = this.userService.findByLogin(username);
		
		if (opt.isEmpty()) {
			throw new UsernameNotFoundException("Aucun user avec ce login [" + username + "]"); 
		}
		
		User user = opt.get();
				
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getLogin())
				.password(user.getPassword())
				.authorities("ROLE_" + user.getProfile().name())
				.build();
	}
}
