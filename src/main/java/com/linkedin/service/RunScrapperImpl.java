package com.linkedin.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.linkedin.filters.FilterJobs;
import com.linkedin.utils.SimpleTimer;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RunScrapperImpl implements RunScrapper{
	
	@Autowired
	public SeleniumScrapper scrapper;
	public Map<String,Boolean> storedJobs = new HashMap<>();
	public Map<String,String> storedJobLinks = new HashMap<>();
	@Value("${simpletimer.period}")
	public Long timerPeriod;
	public SimpleTimer timer;
	
	@Value("${spring.search.keyword}")
	public String keyword;
	
	// Init timer
	@PostConstruct
    public void init() {
        long periodMillis = timerPeriod * 3600 * 1000L;
        this.timer = new SimpleTimer(periodMillis);
        timer.start();
    }
	
	@Override
	public Map<String,String> launchScrapLinks() {
		Map<String,String> jobMap = scrapper.scrap(keyword);
		Map<String,String> newJobsMap = new HashMap<>();
		// Store new jobs
		for(String key : jobMap.keySet()) {
			if(!storedJobLinks.containsKey(key)) {
				// Eliminate black listed jobs
				if(FilterJobs.filterList.stream().noneMatch(key::contains)) {
					newJobsMap.put(key, jobMap.get(key));
				} else {
					log.error(String.format("Job eliminated : %s", key));
				}
			}
		}
		// Store job for last 24H
		if(timer.isOver()) {
			storedJobLinks = jobMap;
			timer.reset();
		} else {
			storedJobLinks.putAll(jobMap);
		}
		return newJobsMap;
	}

}
