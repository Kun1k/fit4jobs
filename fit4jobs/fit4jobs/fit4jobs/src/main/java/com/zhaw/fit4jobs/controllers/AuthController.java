package com.zhaw.fit4jobs.controllers;

import com.zhaw.fit4jobs.models.ELog;
import com.zhaw.fit4jobs.models.LogModel;
import com.zhaw.fit4jobs.models.user.*;
import com.zhaw.fit4jobs.payload.request.LoginRequest;
import com.zhaw.fit4jobs.payload.request.PasswordChangeRequest;
import com.zhaw.fit4jobs.payload.request.SignupRequest;
import com.zhaw.fit4jobs.payload.response.JwtResponse;
import com.zhaw.fit4jobs.payload.response.MessageResponse;
import com.zhaw.fit4jobs.repository.LogRepository;
import com.zhaw.fit4jobs.repository.user.RoleRepository;
import com.zhaw.fit4jobs.repository.user.UserDetailsRepository;
import com.zhaw.fit4jobs.repository.user.UserRepository;
import com.zhaw.fit4jobs.security.jwt.JwtUtils;
import com.zhaw.fit4jobs.security.services.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api(value = "AUTH documentation")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;

	private final UserRepository userRepository;

	private final UserDetailsRepository userDetailsRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder encoder;

	private final JwtUtils jwtUtils;

	private final LogRepository logger;

	@PostMapping("/login")
	@ApiOperation(value = "AUTH Sign in method")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		logger.save(new LogModel(userRepository.findByEmail(loginRequest.getEmail()).getId(), ELog.LOG_SIGNIN));

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), roles));
	}

	@PostMapping("/register")
	@ApiOperation(value = "AUTH Sign Up method")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		UserModel user = new UserModel(signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()), EUserActivity.ACTIVE);

		// Only ROLE_USER Allowed.
		Set<RoleModel> roles = new HashSet<>();
		
		RoleModel userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);

		user.setRoles(roles);
		userRepository.save(user);
		
		//Create User Details
		userDetailsRepository.save(new UserDetailsModel(user,
				signUpRequest.getName(),
				signUpRequest.getSurname(),
				signUpRequest.getStreet(),
				signUpRequest.getNumber(),
				signUpRequest.getPostCode(),
				signUpRequest.getCity(),
				signUpRequest.getPhone(),
				signUpRequest.getBirthday(),
				signUpRequest.getMobile(),
				signUpRequest.getNationality(),
				signUpRequest.getCivilStatus(),
				signUpRequest.getGender()));


		logger.save(new LogModel(user.getId(), ELog.LOG_SIGNUP));
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/changePassword")
	@ApiOperation(value = "AUTH change password method", authorizations = { @Authorization(value="JWT_LOGIN") })
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
		try {
			Authentication authenticationTest = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(passwordChangeRequest.getEmail(), passwordChangeRequest.getOldPassword()));
		}catch (Exception e){
			throw new AuthenticationCredentialsNotFoundException("Credentials couldn't be updated");
		}

		UserModel user = userRepository.findByEmail(passwordChangeRequest.getEmail());
		user.setPassword(encoder.encode(passwordChangeRequest.getNewPassword()));
		userRepository.save(user);

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(passwordChangeRequest.getEmail(), passwordChangeRequest.getNewPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		logger.save(new LogModel(userRepository.findByEmail(passwordChangeRequest.getEmail()).getId(), ELog.LOG_PASSWORD_UPDATED));

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), roles));
	}
}
