package com.zhaw.fit4jobs.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@ApiModel(value = "User Model documentation", description = "Model")
@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "email")
		})
@Data
@NoArgsConstructor
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Unique id field of user object")
	private Long id;

	@NotBlank
	@Size(max = 50)
	@Email
	@ApiModelProperty(value = "Email field of user object")
	private String email;

	@NotBlank
	@Size(max = 120)
	@ApiModelProperty(value = "Password field of user object")
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	@ApiModelProperty(value = "Activity field of user object")
	private EUserActivity activity;

	@ManyToMany(fetch = FetchType.LAZY)
	//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	@ApiModelProperty(value = "Roles field of user object")
	private Set<RoleModel> roles = new HashSet<>();

	public UserModel(String email, String password, EUserActivity activity) {
		this.email = email;
		this.password = password;
		this.activity = activity;
	}
}
