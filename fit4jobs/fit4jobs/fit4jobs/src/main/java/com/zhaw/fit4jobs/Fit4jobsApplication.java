package com.zhaw.fit4jobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Fit4jobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Fit4jobsApplication.class, args);
	}

}
