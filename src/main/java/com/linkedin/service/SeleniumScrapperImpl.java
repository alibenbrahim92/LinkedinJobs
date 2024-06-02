package com.linkedin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeleniumScrapperImpl implements SeleniumScrapper{
	
	private static WebDriver driver =  new ChromeDriver();

	@Override
	public Map<String,String> scrap(String keyword) {

	    try {
	    	driverGetLink(keyword);
		    Map<String, String> jobMap = getJobList();
	    	return jobMap;
	    } catch(NoSuchElementException e) {
	    	log.error("No job found matching : "+keyword);
	    	return new HashMap<String,String>();
	    } 
	}
    
    private Map<String,String> getJobList() {
    	String unknowId  = UUID.randomUUID().toString();
    	Map<String,String> jobList = new HashMap<String,String>();
    	WebElement ulElement = driver.findElement(By.cssSelector(".jobs-search__results-list"));
    	List<WebElement> lis = ulElement.findElements(By.tagName("li"));
	    for(WebElement liElement : lis) {
	    	WebElement titleElement;
	    	String title = "Unknown Title "+unknowId;
	    	try {
	    		titleElement = liElement.findElement(By.cssSelector(".sr-only"));
	    		title = titleElement.getText();
	    	}catch(Exception e) {
	    		System.out.println("Title not found, retrying...");
	    		try {
	    			titleElement = liElement.findElement(By.cssSelector(".base-search-card__title"));
	    			title = titleElement.getText();
	    		} catch(Exception f) {
	    			System.out.println("Title not found !");
	    		}
	    	}
	    	
	    	WebElement aElement;
	    	String link = "Unknow link "+unknowId;
	    	try {
	    		aElement = liElement.findElement(By.cssSelector(".base-card__full-link"));
	    		link = aElement.getAttribute("href");
	    	}catch(Exception e) {
	    		try {
		    		aElement = liElement.findElement(By.tagName("a"));
		    		link = aElement.getAttribute("href");
	    		} catch(Exception f) {
	    			log.error("Link not found");
	    		}
	    	}
	    	
	    	String companyName = "Unknown company "+unknowId;
	    	try {
	    		WebElement companyNameElement = liElement.findElement(By.cssSelector(".base-search-card__subtitle"));
	    		companyName = companyNameElement.getText();
	    	} catch(Exception e) {
	    		log.error("Company Name not found");
	    	}
	    	
	    	String location = "Unknown Location "+unknowId;
	    	try {
	    		WebElement locationElement = liElement.findElement(By.cssSelector(".job-search-card__location"));
	    		location = locationElement.getText();
	    	} catch(Exception e) {
	    		log.error("Location not found");
	    	}
	    	
	    	
	    	StringBuilder keyBuilder = new StringBuilder(String.format("%s ;;; %s ;;; %s", title, companyName, location));

            
	    	jobList.put(keyBuilder.toString(), String.format("Link ;;; %s", link));
	    }
	    return jobList;
    }
    
    private void driverGetLink(String keyword) {
    	Integer maxRetryCount = 5;
    	Integer retryCount = 0;
    	while(retryCount < maxRetryCount) {
	    	try {
				driver.get(String.format("https://www.linkedin.com/jobs/search/?f_TPR=r3600&keywords=%s&location=Canada&origin=JOB_SEARCH_PAGE_LOCATION_AUTOCOMPLETE&refresh=true&position=1&pageNum=0",keyword));
				break;
	    	} catch(WebDriverException e) {
	    		try {
	    			driver.close();
	    		} catch(WebDriverException wde) {
	    			log.error("WebDriver may be already closed ...");
	    		}
	    		retryCount++;
	    		driver = new ChromeDriver();
	    		if(retryCount == maxRetryCount) {
	    			throw e;
	    		} else {
	    			try {
	    				Thread.sleep(5000);
	    			}catch(InterruptedException ie) {
	    				e.printStackTrace();
	    			}
	    		}
	    	}
    	}
    }

}
