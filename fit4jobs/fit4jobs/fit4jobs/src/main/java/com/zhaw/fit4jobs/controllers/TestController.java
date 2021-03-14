package com.zhaw.fit4jobs.controllers;

import com.zhaw.fit4jobs.security.services.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "TEST Control documentation")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	@GetMapping("/all")
	@ApiOperation(value = "Test Function For All Users")
	public String allAccess() {
		return "{\"message\": \"Public Content.\"}";
	}
	
	@GetMapping("/user")
	@ApiOperation(value = "Test Function For All Authenticated Users", authorizations = { @Authorization(value="JWT_LOGIN") })
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return "User Content " + userDetails.getEmail();
	}

	@GetMapping("/mod")
	@ApiOperation(value = "Test Function For Moderator Users", authorizations = { @Authorization(value="JWT_LOGIN") })
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Content.";
	}

	@GetMapping("/admin")
	@ApiOperation(value = "Test Function For Admin Users", authorizations = { @Authorization(value="JWT_LOGIN") })
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Content.";
	}
}
