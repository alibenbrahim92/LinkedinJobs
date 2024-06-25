package com.linkedin.service;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.linkedin.email.EmailApplication;
import static com.linkedin.utils.CitiesNames.*;
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
		
		@Value("${spring.scheduler.interval}")
		private long interval = 0;

	    @Scheduled(fixedRateString = "#{T(java.util.concurrent.TimeUnit).SECONDS.toMillis(${spring.scheduler.interval})}")
	    public void runTask() {
	        Map<String,String> newJobsMap = runScrapper.launchScrapLinks();
			log.info("new Jobs : "+newJobsMap);
			if(newJobsMap.size() != 0) {
				emailApplication.sendEmail(utils.mapToSubject(newJobsMap,Arrays.asList(MONTREAL, QUEBEC_CITY, QUEBEC, CANADA), "Java")
						,utils.mapToString(newJobsMap));
			}
	    }
}
