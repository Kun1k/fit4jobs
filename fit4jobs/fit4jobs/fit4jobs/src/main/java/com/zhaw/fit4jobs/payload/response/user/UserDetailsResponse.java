package com.zhaw.fit4jobs.payload.response.user;

import com.zhaw.fit4jobs.models.user.ECivilStatus;
import com.zhaw.fit4jobs.models.user.EGender;
import lombok.Data;

import java.util.Date;

@Data
public class UserDetailsResponse {
	
	private Long userDetailsId;
	
	private String name;
	
	private String surname;
	
	private String street;
	
	private String number;
	
	private String postCode;
	
	private String city;

	private String phone;

	private Date birthDay;

	private Date creationDate;

	private Date LastUpdateDate ;

	private String mobile;

	private String nationality;

	private ECivilStatus civilStatus;

	private EGender gender;

	private String email;

	private Long userId;

	private boolean isRegistered;

	public UserDetailsResponse(Long userDetailsId, String name, String surname, String street, String number, String postCode,
			String city, String phone, Date birthDay, Date creationDate, Date lastUpdateDate, String mobile, String nationality, ECivilStatus eCivilStatus, EGender eGender, String email, Long userId, Boolean isRegistered) {
		super();
		this.userDetailsId = userDetailsId;
		this.name = name;
		this.surname = surname;
		this.street = street;
		this.number = number;
		this.postCode = postCode;
		this.city = city;
		this.phone = phone;
		this.birthDay=birthDay;
		this.creationDate=creationDate;
		this.LastUpdateDate=lastUpdateDate;
		this.mobile=mobile;
		this.nationality=nationality;
		this.civilStatus=eCivilStatus;
		this.gender=eGender;
		this.email=email;
		this.userId=userId;
		this.isRegistered=isRegistered;

	}
}
