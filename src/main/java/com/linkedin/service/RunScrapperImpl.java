package com.linkedin.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkedin.filters.FilterJobs;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RunScrapperImpl implements RunScrapper{
	
	@Autowired
	public SeleniumScrapper scrapper;
	
	public Map<String,Boolean> storedJobs = new HashMap<>();
	public Map<String,String> storedJobLinks = new HashMap<>();
	
	@Override
	public Map<String,Boolean> launchScrap() {
		Map<String,Boolean> jobMap = scrapper.scrap("java");
		Map<String,Boolean> newJobsMap = new HashMap<>();
		for(String key : storedJobs.keySet()) {
			if(!jobMap.containsKey(key)) {
				newJobsMap.put(key, jobMap.get(key));
			}
		}
		
		storedJobs = jobMap;
		return newJobsMap;
	}
	
	@Override
	public Map<String,String> launchScrapLinks() {
		Map<String,String> jobMap = scrapper.scrap();
		Map<String,String> newJobsMap = new HashMap<>();
		for(String key : jobMap.keySet()) {
			if(!storedJobLinks.containsKey(key)) {
				if(FilterJobs.filterList.stream().noneMatch(e -> key.contains(e))) {
					newJobsMap.put(key, jobMap.get(key));
				} else {
					log.error(String.format("Job eliminated : %s", key));
				}
			}
		}
		
		storedJobLinks = jobMap;
		return newJobsMap;
	}

}
