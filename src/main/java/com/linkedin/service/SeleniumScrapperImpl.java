package com.linkedin.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeleniumScrapperImpl implements SeleniumScrapper{
	
	private static WebDriver driver =  new ChromeDriver();

	@Override
	public Map<String,String> scrap(String keyword) {

	    try {
			driver.get(String.format("https://www.linkedin.com/jobs/search/?f_TPR=r3600&keywords=%s&location=Canada&origin=JOB_SEARCH_PAGE_LOCATION_AUTOCOMPLETE&refresh=true&position=1&pageNum=0",keyword));
		    Map<String, String> jobMap = getJobList();
	    	return jobMap;
	    } catch(NoSuchElementException e) {
	    	log.error("Element not found");
	    	return new HashMap<String,String>();
	    } 
	}
    
    private Map<String,String> getJobList() {
    	
    	Map<String,String> jobList = new HashMap<String,String>();
    	WebElement ulElement = driver.findElement(By.cssSelector(".jobs-search__results-list"));
    	List<WebElement> lis = ulElement.findElements(By.tagName("li"));
	    for(WebElement liElement : lis) {
	    	WebElement title = null;
	    	try {
	    		title = liElement.findElement(By.cssSelector(".sr-only"));
	    	}catch(Exception e) {
	    		System.out.println("Element not found, retrying...");
	    		title = liElement.findElement(By.cssSelector(".base-search-card__title"));
	    	}
	    	String text = title!=null? title.getText():"Unknown";
	    	WebElement aElement;
	    	try {
	    		aElement = liElement.findElement(By.cssSelector(".base-card__full-link"));
	    	}catch(Exception e) {
	    		aElement = liElement.findElement(By.tagName("a"));
	    	}
	    	String link = aElement.getAttribute("href");
	    	WebElement companyNameElement = liElement.findElement(By.cssSelector(".base-search-card__subtitle"));
	    	String location = "Unknown";
	    	try {
	    		WebElement locationElement = liElement.findElement(By.cssSelector(".job-search-card__location"));
	    		location = locationElement.getText();
	    	} catch(Exception e) {
	    		System.out.println("Location not found");
	    	}
	    	
	    	String companyName = companyNameElement.getText();
	    	StringBuilder keyBuilder = new StringBuilder(String.format("%s ;;; %s ;;; %s", text, companyName, location));

            
	    	jobList.put(keyBuilder.toString(), String.format("Link ;;; %s", link));
	    }
	    return jobList;
    }

}
