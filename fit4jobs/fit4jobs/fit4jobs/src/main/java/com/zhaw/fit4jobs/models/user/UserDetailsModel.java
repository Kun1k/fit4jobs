package com.zhaw.fit4jobs.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(	name = "user_details")
@Data
@NoArgsConstructor
public class UserDetailsModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@OneToOne(fetch = FetchType.LAZY)
	//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinTable(	name = "user_user_detail", 
				joinColumns = @JoinColumn(name = "user_detail_id"), 
				inverseJoinColumns = @JoinColumn(name = "user_id"))
	private UserModel userId;
	
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
	@DateTimeFormat
	private Date creationDate = new Date();

	@DateTimeFormat
	private Date LastUpdateDate ;

	@NotBlank
	@Size(max = 20)
	private String mobile;

	@NotBlank
	@Size(max = 30)
	private String nationality;


	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ECivilStatus civilStatus;

	@NotBlank
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private EGender gender;

	public UserDetailsModel(UserModel userId, String name,
							String surname, String street, String number,
							String postCode, String city, String phone, Date birthDay,
							String mobile, String nationality, ECivilStatus civilStatus,  EGender gender) {
		super();
		this.userId = userId;
		this.name = name;
		this.surname = surname;
		this.street = street;
		this.number = number;
		this.postCode = postCode;
		this.city = city;
		this.phone = phone;
		this.birthDay = birthDay;
		this.mobile = mobile;
		this.nationality = nationality;
		this.civilStatus = civilStatus;
		this.gender=gender;
	}
}
