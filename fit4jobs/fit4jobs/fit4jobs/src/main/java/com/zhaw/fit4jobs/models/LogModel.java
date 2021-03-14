package com.zhaw.fit4jobs.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "logs")
@Data
@NoArgsConstructor
public class LogModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@DateTimeFormat
    private Date creationDate = new Date();
	
	@NotBlank
	private Long user_id;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	@NotBlank
	private ELog log_type;
	
	@Size(max = 255)
	private String log_note;

	public LogModel(@NotBlank Long user_id, @NotBlank ELog log_type) {
		this.user_id = user_id;
		this.log_type = log_type;
	}
	
	public LogModel(@NotBlank Long user_id, @NotBlank ELog log_type, String log_note) {
		this.user_id = user_id;
		this.log_type = log_type;
		this.log_note = log_note;
	}
}
