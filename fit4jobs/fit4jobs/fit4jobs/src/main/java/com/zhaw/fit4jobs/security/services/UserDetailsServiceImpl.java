package com.zhaw.fit4jobs.security.services;

import com.zhaw.fit4jobs.models.user.UserModel;
import com.zhaw.fit4jobs.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserModel user = userRepository.findByEmail(email);
		if (user == null)
			new UsernameNotFoundException("User Not Found with email: " + email);

		return UserDetailsImpl.build(user);
	}

}
