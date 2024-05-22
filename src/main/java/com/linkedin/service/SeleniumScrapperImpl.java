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
	
	private static final String COOKIE_FILE_PATH = "C:\\Users\\benbr\\Selenium\\cookies\\linkedin_cookies.txt";
//	private static ChromeOptions options = new ChromeOptions();
//	static {
//      options.addArguments("--headless");
//      driver = new ChromeDriver(options);
//	}
	private static WebDriver driver =  new ChromeDriver();

	@Override
	public Map<String,String> scrap() {
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("jobs-search__results-list")));
	    try {
			driver.get("https://www.linkedin.com/jobs/search/?f_TPR=r3600&keywords=java%20developer&location=Canada&origin=JOB_SEARCH_PAGE_LOCATION_AUTOCOMPLETE&refresh=true&position=1&pageNum=0");
//			driver.get("https://www.linkedin.com/jobs/search/?f_TPR=r3600&keywords=java&location=Canada&origin=JOB_SEARCH_PAGE_LOCATION_AUTOCOMPLETE&refresh=true&position=1&pageNum=0");

			// Attendre que les éléments de la liste des offres d'emploi soient chargés
	//        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(5));
	//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("jobs-search__results-list")));
		    Map<String, String> jobMap = getJobList();
	//    	System.out.println(jobMap);
	    	return jobMap;
	    } catch(NoSuchElementException e) {
	    	log.error("Element not found");
	    	return new HashMap<String,String>();
	    } 
	}
	
	@Override
	public Map<String,Boolean> scrap(String keyword) {
		// Spécifiez le chemin vers votre driver Chrome
	    System.setProperty("webdriver.chrome.driver", "C:\\Users\\benbr\\ChromeDriver_Selenium\\chromedriver.exe");
	
//	 // Activer le mode headless
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // Exécution sans interface graphique
//        
//	    // Instanciez le driver Chrome
//	    WebDriver driver = new ChromeDriver(options);
	    
//	    WebDriver driver = new ChromeDriver();
	    
	    // Si les cookies n'existent pas, connectez-vous à LinkedIn
//        if (driver.manage().getCookies().isEmpty()) {
//            loginToLinkedIn(driver);
//            // Enregistrez les cookies après la connexion réussie
//            saveCookies(driver);
//        }
//        else {
//        	// Chargez les cookies s'ils existent
//            loadCookies(driver);
//        }
        

       

	    
//	    driver.get("https://www.linkedin.com/jobs/search/?currentJobId=3901964348&f_TPR=r3600&geoId=107111588&keywords=java%20developer&location=Greater%20Montreal%2C%20Quebec%2C%20Canada&origin=JOB_SEARCH_PAGE_LOCATION_AUTOCOMPLETE&refresh=true");
//	    
//	
//	 // Trouvez l'élément ul avec la classe "scaffold-layout__list-container"
//	    WebElement ulElement = driver.findElement(By.cssSelector("ul.scaffold-layout__list-container"));
//	
//	    // Faites quelque chose avec l'élément ul récupéré, par exemple, imprimez son contenu
//	    System.out.println("Contenu de l'élément ul avec la classe scaffold-layout__list-container :");
//	    System.out.println(ulElement.getAttribute("outerHTML"));
//	
//	    // Fermez le navigateur
//	    driver.quit();
	    
	    
//	    driver.get("https://www.linkedin.com/login");
//
//        // Entrez votre adresse e-mail ou votre numéro de téléphone
//        WebElement emailField = driver.findElement(By.id("username"));
//        emailField.sendKeys("alibenbrahim92@gmail.com");
//
//        // Entrez votre mot de passe
//        WebElement passwordField = driver.findElement(By.id("password"));
//        passwordField.sendKeys("Azertyuiop123?");
//
//        // Cliquez sur le bouton de connexion
//        WebElement signInButton = driver.findElement(By.xpath("//button[@type='submit']"));
//        signInButton.click();

        // Attendre que la page se charge
        // Attendez que la page de recherche d'emploi soit chargée
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.urlToBe("https://www.linkedin.com/feed/"));
//        try {
//            Thread.sleep(5000); // Attendre 5 secondes pour la page de chargement
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // Une fois connecté, naviguez vers la page de recherche d'emploi
	    driver.get("https://www.linkedin.com/jobs/search/?f_TPR=r3600&geoId=107111588&keywords=java%20developer&location=Greater%20Montreal%2C%20Quebec%2C%20Canada&origin=JOB_SEARCH_PAGE_LOCATION_AUTOCOMPLETE&refresh=true");

		 // Trouvez l'élément ul avec la classe "scaffold-layout__list-container"
	    WebElement ulElement = driver.findElement(By.cssSelector("ul.scaffold-layout__list-container"));
	    List<WebElement> lis = ulElement.findElements(By.xpath(".//li[starts-with(@id,'ember')]"));
	    
	    // Faites quelque chose avec l'élément ul récupéré, par exemple, imprimez son contenu
//	    System.out.println("Contenu de l'élément ul avec la classe scaffold-layout__list-container :");
//	    System.out.println(ulElement.getAttribute("outerHTML"));
	    System.out.println("job count : "+lis.size());
	    Map<String,Boolean> jobMap = new HashMap<String, Boolean>();
	    
	    for(WebElement liElement : lis) {
	    	
	    	wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Définir une attente de 5 secondes
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body"))); // Attendre que le corps de la page soit visible

	    	
	    	// Trouver l'enfant de l'élément div ayant l'attribut aria-label
//	    	WebElement childElement = liElement.findElement(By.xpath(".//*[@aria-label]"));
	    	WebElement titleElement = null;
	    	while(titleElement == null) {
	    		try {
	    			Thread.sleep(100);
	    			// Trouver l'élément <a> enfant de liElement
	    			titleElement = liElement.findElement(By.tagName("a"));
	    			
//	    			titleElement = liElement.findElement(By.xpath(".//*[contains(@class, 'artdeco-entity-lockup__title')]"));
	    			
	    		}catch(Exception e) {
	    			System.out.println("Title Not found");
	    		}
	    	}


	    	// Obtenir la valeur de l'attribut aria-label de l'enfant
	    	String jobLabel = titleElement.getText();

	   	 // Cliquer sur l'élément div
	    	liElement.click();

		    // Attendre un court instant pour permettre à la page de se mettre à jour si nécessaire
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Attendre 2 secondes (ce n'est pas la meilleure pratique, mais cela fonctionne pour un exemple)

		    // Récupérer le texte de l'élément ayant l'ID "job-details"
		    WebElement jobDetailsElement = driver.findElement(By.xpath(".//*[@id='job-details']"));
		    String jobDetailsText = jobDetailsElement.getText();
		    Boolean containsKeyword = jobDetailsText.matches("(?i).*\\bjava\\b.*");
		    // Faire quelque chose avec le texte récupéré, par exemple l'afficher
		    System.out.println(jobLabel+" contains java : " + containsKeyword);
		    jobMap.put(jobLabel, containsKeyword);
	    }


	
	    // Fermez le navigateur
//	    driver.quit();
	    return jobMap;
	}
	

    private static void loginToLinkedIn(WebDriver driver) {
    	driver.get("https://www.linkedin.com/login");

        // Entrez votre adresse e-mail ou votre numéro de téléphone
        WebElement emailField = driver.findElement(By.id("username"));
        emailField.sendKeys("${SPRING_LINKEDIN_EMAIL}");

        // Entrez votre mot de passe
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("${SPRING_LINKEDIN_PASSWORD}");

        // Cliquez sur le bouton de connexion
        WebElement signInButton = driver.findElement(By.xpath("//button[@type='submit']"));
        signInButton.click();
    }

    private static void loadCookies(WebDriver driver) {
//        try {
//            File file = new File(COOKIE_FILE_PATH);
//            if (file.exists()) {
//            	driver.get("https://www.linkedin.com/login");
//                FileReader fileReader = new FileReader(file);
//                BufferedReader bufferedReader = new BufferedReader(fileReader);
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    String[] cookieParts = line.split(";");
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
//                    Date expiryDate = cookieParts[4]!= null ? dateFormat.parse(cookieParts[4]) : null;
//                    Cookie cookie = new Cookie.Builder(cookieParts[0], cookieParts[1])
//                            .domain(cookieParts[2])
//                            .path(cookieParts[3])
//                            .expiresOn(expiryDate)
//                            .isSecure(Boolean.parseBoolean(cookieParts[5]))
//                            .build();
//                    driver.manage().addCookie(cookie);
//                }
//                bufferedReader.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
//    	driver.get("https://www.linkedin.com/login");
//    	driver.manage().deleteAllCookies();

        // Chargez les cookies
        try {
            File file = new File(COOKIE_FILE_PATH);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ";");
                while (tokenizer.hasMoreTokens()) {
                    String name = tokenizer.nextToken();
                    String value = tokenizer.nextToken();
                    String domain = tokenizer.nextToken();
                    String path = tokenizer.nextToken();
                    Date expiry = null;
                    String dt;
                    if (!(dt = tokenizer.nextToken()).equals("null")) {
                        expiry = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(dt);
                    }
                    boolean isSecure = new Boolean(tokenizer.nextToken()).booleanValue();
                    Cookie cookie = new Cookie(name, value, domain, path, expiry, isSecure);
                    driver.manage().addCookie(cookie);
                }
            }
            bufferedReader.close();

            // Rechargez la page pour appliquer les cookies
//            driver.get("https://www.linkedin.com/login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveCookies(WebDriver driver) {
        try {
            FileWriter fileWriter = new FileWriter(COOKIE_FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            Set<Cookie> cookies = driver.manage().getCookies();
            for (Cookie cookie : cookies) {
                bufferedWriter.write(cookie.getName() + ";" + cookie.getValue() + ";" + cookie.getDomain() + ";" + cookie.getPath() + ";" + cookie.getExpiry() + ";" + cookie.isSecure());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Map<String,String> getJobList() {
    	Map<String,String> jobList = new HashMap<String,String>();
    	WebElement ulElement = driver.findElement(By.cssSelector(".jobs-search__results-list"));
	   
//    	WebElement ulElement = driver.findElement(By.xpath("//*[@id='main-content']/section[2]/ul/li"));

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
//	    	String companyName = aElement.getText();
	    	WebElement companyNameElement = liElement.findElement(By.cssSelector(".base-search-card__subtitle"));
	    	String companyName = companyNameElement.getText();
	    	StringBuilder keyBuilder = new StringBuilder(String.format("%s ;;; %s", text, companyName));
//	    	WebElement aElementClick = liElement.findElement(By.cssSelector(".base-card__full-link"));
//	    	// Crée une instance de la classe Actions
//            Actions newTab = new Actions(driver);
//
//            // Clique sur le lien tout en maintenant la touche de modification enfoncée pour ouvrir dans un nouvel onglet
//            newTab.keyDown(org.openqa.selenium.Keys.CONTROL).click(aElementClick).keyUp(org.openqa.selenium.Keys.CONTROL).build().perform();
//            // Basculer vers le nouvel onglet
//            String currentHandle = driver.getWindowHandle();
//            for (String handle : driver.getWindowHandles()) {
//                if (!handle.equals(currentHandle)) {
//                    driver.switchTo().window(handle);
//                }
//            }
//            // Ferme le nouvel onglet
//            driver.close();
            
//            WebElement divElement = driver.findElement(By.cssSelector(".show-more-less-html__markup"));
//            String description = divElement.getText();
//            // Retourne à l'onglet précédent
//            driver.switchTo().window(currentHandle);
            
	    	jobList.put(keyBuilder.toString(), String.format("Link ;;; %s", link));
	    }
	    return jobList;
    }

}
