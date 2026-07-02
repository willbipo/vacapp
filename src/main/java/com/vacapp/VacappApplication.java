package com.vacapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VacappApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacappApplication.class, args);
	}

}
