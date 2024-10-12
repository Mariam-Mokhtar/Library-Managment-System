package com.system.Library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LibraryManagementSysemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSysemApplication.class, args);
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String rawPassword = "Admin123";
//		String encodedPassword = encoder.encode(rawPassword);
//
//		// Output encoded password
//		System.out.println("Encoded Password: " + encodedPassword);
	}

}
