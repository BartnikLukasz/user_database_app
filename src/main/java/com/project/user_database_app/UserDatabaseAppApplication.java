package com.project.user_database_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//This application connects to local MySQL database and handles all request
@SpringBootApplication
public class UserDatabaseAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserDatabaseAppApplication.class, args);
	}

}
