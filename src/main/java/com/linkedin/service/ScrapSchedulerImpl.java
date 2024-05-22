package com.linkedin.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.linkedin.email.EmailApplication;
import com.linkedin.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScrapSchedulerImpl implements ScrapScheduler{
		
		@Autowired
		public RunScrapper runScrapper;
		
		@Autowired
		public Utils utils;
		
		@Autowired
		EmailApplication emailApplication;

	    @Scheduled(fixedRate = 60000) // 60000 milliseconds = 1 minute
	    public void runTask() {
	        Map<String,String> newJobsMap = runScrapper.launchScrapLinks();
			log.info("new Jobs : "+newJobsMap);
			if(newJobsMap.size() != 0) {
				emailApplication.sendEmail(utils.mapToString(newJobsMap));
			}
	    }
}
