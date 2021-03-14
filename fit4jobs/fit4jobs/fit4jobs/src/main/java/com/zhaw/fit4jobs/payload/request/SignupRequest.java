package com.zhaw.fit4jobs.payload.request;

import com.zhaw.fit4jobs.models.user.ECivilStatus;
import com.zhaw.fit4jobs.models.user.EGender;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
    
    @NotBlank
    @Size(min = 3, max = 20)
    private String surname;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
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
	@Size(max = 20)
	private String mobile;

	@NotBlank
	@Size(max = 30)
	private String nationality;

	@Size(max = 20)
	private ECivilStatus civilStatus;

	@NotBlank
	private Date birthday;

	@NotBlank
	@Size(max = 10)
	private EGender gender;
}
