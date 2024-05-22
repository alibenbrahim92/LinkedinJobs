package com.linkedin.service;

import java.util.Map;

public interface RunScrapper {
	public Map<String, Boolean> launchScrap();
	public Map<String,String> launchScrapLinks();
}
