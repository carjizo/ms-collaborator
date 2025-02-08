package com.colaborator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ColaboratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ColaboratorApplication.class, args);
	}

}
