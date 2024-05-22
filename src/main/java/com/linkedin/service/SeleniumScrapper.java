package com.linkedin.service;

import java.util.Map;

public interface SeleniumScrapper {

	public Map<String,Boolean> scrap(String keyword);
	
	public Map<String,String> scrap();
}
