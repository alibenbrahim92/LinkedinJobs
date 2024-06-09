package com.linkedin.email;

public interface EmailApplication {

	public String sendEmail(String body);

	String sendEmail(String subject, String body);
	
}
