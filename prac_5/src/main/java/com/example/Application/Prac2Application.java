package com.example.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
//
//@EnableCaching
public class Prac2Application {

	public static void main(String[] args) {
		SpringApplication.run(Prac2Application.class, args);
	}

}
