package com.zhaw.fit4jobs.controllers;

import com.zhaw.fit4jobs.models.ELog;
import com.zhaw.fit4jobs.models.LogModel;
import com.zhaw.fit4jobs.models.user.UserDetailsModel;
import com.zhaw.fit4jobs.payload.request.user.UpdateUserDetailsRequest;
import com.zhaw.fit4jobs.payload.response.MessageResponse;
import com.zhaw.fit4jobs.payload.response.user.UserDetailsResponse;
import com.zhaw.fit4jobs.repository.LogRepository;
import com.zhaw.fit4jobs.repository.user.UserDetailsRepository;
import com.zhaw.fit4jobs.repository.user.UserRepository;
import com.zhaw.fit4jobs.security.services.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Api(value = "User Control documentation")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserDetailsRepository userDetailsRepository;

	private final UserRepository userRepository;

	private final LogRepository logger;
	
	@GetMapping("/getDetails")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@ApiOperation(value = "Get Authenticated User Details", authorizations = { @Authorization(value="JWT_LOGIN") })
	public UserDetailsResponse getUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		UserDetailsModel userDetail = userDetailsRepository.findByUserId(userRepository.findByEmail(userDetails.getEmail()));
		
		return new UserDetailsResponse(
				userDetail.getId(),
				userDetail.getName(),
				userDetail.getSurname(),
				userDetail.getStreet(),
				userDetail.getNumber(),
				userDetail.getPostCode(),
				userDetail.getCity(),
				userDetail.getPhone(),
				userDetail.getBirthDay(),
				userDetail.getCreationDate(),
				userDetail.getLastUpdateDate(),
				userDetail.getMobile(),
				userDetail.getNationality(),
				userDetail.getCivilStatus(),
				userDetail.getGender(),
				userDetail.getUserId().getEmail(),
				userDetail.getUserId().getId(),
				Boolean.TRUE);
	}
	
	@PostMapping("/updateDetails")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@ApiOperation(value = "Update Details For Authenticated User", authorizations = { @Authorization(value="JWT_LOGIN") })
	public ResponseEntity<?> updateUserDetails(@Valid @RequestBody UpdateUserDetailsRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		UserDetailsModel userDetail = userDetailsRepository.findByUserId(userRepository.findByEmail(userDetails.getEmail()));
		
		userDetail.setName(request.getName());
		userDetail.setSurname(request.getSurname());
		userDetail.setStreet(request.getStreet());
		userDetail.setNumber(request.getNumber());
		userDetail.setPostCode(request.getPostCode());
		userDetail.setCity(request.getCity());
		userDetail.setPhone(request.getPhone());
		userDetail.setBirthDay(request.getBirthDay());
		userDetail.setLastUpdateDate(new Date());
		userDetail.setMobile(request.getMobile());
		userDetail.setNationality(request.getNationality());
		userDetail.setCivilStatus(request.getCivilStatus());
		userDetail.setGender(request.getGender());
		
		userDetailsRepository.save(userDetail);
		logger.save(new LogModel(userDetail.getId(), ELog.LOG_USER_DATA_UPDATED));
		
		return ResponseEntity.ok(new MessageResponse("User Details updated successfully."));
	}

}
