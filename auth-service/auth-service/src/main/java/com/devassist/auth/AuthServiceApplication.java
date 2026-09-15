package com.devassist.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
        // Must be executed before SpringApplication.run() initializes beans
        System.setProperty("user.timezone", "Asia/Kolkata");
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
