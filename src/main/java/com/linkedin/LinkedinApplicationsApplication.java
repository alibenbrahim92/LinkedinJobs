package com.linkedin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.linkedin.email.EmailApplication;

@SpringBootApplication
@EnableScheduling
public class LinkedinApplicationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkedinApplicationsApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(EmailApplication emailApplication) {
		return (args) ->{
			System.out.println(emailApplication.sendEmail("Bonjour,\nCeci est un test de fonctionnement d'envoi d'email."));
			
		};
	}
}
