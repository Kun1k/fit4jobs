package com.zhaw.fit4jobs.payload.request.user;

import com.zhaw.fit4jobs.models.user.ECivilStatus;
import com.zhaw.fit4jobs.models.user.EGender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UpdateUserDetailsRequest {
	
	@NotBlank
	@Size(max = 10)
	private String name;
	
	@NotBlank
	@Size(max = 10)
	private String surname;
	
	@NotBlank
	@Size(max = 30)
	private String street;
	
	@NotBlank
	@Size(max = 10)
	private String number;
	
	@NotBlank
	@Size(max = 10)
	private String postCode;
	
	@NotBlank
	@Size(max = 20)
	private String city;
	
	@NotBlank
	@Size(max = 20)
	private String phone;

	@NotBlank
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date birthDay;

	@NotBlank
	@Size(max = 20)
	private String mobile;

	@NotBlank
	@Size(max = 30)
	private String nationality;

	@Size(max = 20)
	private ECivilStatus civilStatus;

	@NotBlank
	@Size(max = 20)
	private EGender gender;
}
