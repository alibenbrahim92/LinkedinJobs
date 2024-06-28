package com.linkedin.email;

public interface EmailApplication {

	void sendEmail(String body);

	void sendEmail(String subject, String body);
	
}
