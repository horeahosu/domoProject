package com.domo.selenium.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.apache.commons.io.FileUtils;
//import org.json.simple.JSONObject;
//
//
//import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.HasInputDevices;
import org.openqa.selenium.Mouse;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.seleniumemulation.Click;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
//import org.sikuli.webdriver.SikuliFirefoxDriver;
//import org.testng.Assert;

import javax.swing.text.html.HTML;


public class GenericMethods {
	
	
	
	public void WaitForElementVisible(By by, int TIMEOUT, String LOG_FILE, WebDriver driver) throws Exception {
		logger.Log(LOG_FILE, "WaitForElementVisible: " + by +" ");
		for (int second = 0;; second++) {
			if (second >= TIMEOUT) {
				throw  new Exception("Timeout "+TIMEOUT+" sec. Element ("+ by +") is not Visible!");}
			try {
				if (driver.findElement(by).isDisplayed()) {
					if (second > 1) System.out.println("");
					break;
				}
			} catch (Exception e) {}
			Thread.sleep(1000);
			if (second >= 1) System.out.print(second+ " ");
		}	
	}
//***********************************************************************************************************************		
	
	public void WaitForElementNotVisible(By by, int timeout, String LOG_FILE, WebDriver driver) throws Exception {
		//is_visible = driver.findElement(by).isDisplayed();
		logger.Log(LOG_FILE, "WaitForElementNotVisible: " + by +" ");
		for (int second = 0;; second++) {
			if (second >= timeout) {throw  new Exception("Timeout "+timeout+" sec. Element ("+ by +") is not Visible!");}
			try {
				if (!(driver.findElement(by).isDisplayed())) { 
					if (second > 1) System.out.println("");
					break;
				}
			} catch (Exception e) {}
			Thread.sleep(1000);
			if (second >= 1) System.out.print(second+ " ");
		}	
	}
//***********************************************************************************************************************	
	public boolean isElementPresent(By by, WebDriver driver) {
		try {
			List<WebElement> element = driver.findElements(by);
			if (element.isEmpty()) {
				return false;
			}
			else return true;
		} catch (Exception e) {
			return false;
		}
		
	}
//***********************************************************************************************************************
	public void WaitForElementPresent(By by, int TIMEOUT, String LOG_FILE, WebDriver driver) throws Exception {
		//System.out.println("WaitForElementPresent: " + by+ " ");
		logger.Log(LOG_FILE, "WaitForElementPresent: " + by +" ");
		for (int second = 0;; second++) {
			if (second >= TIMEOUT) {throw  new Exception("Timeout "+TIMEOUT+" sec. Element ("+ by +") is not Present!");}
			try {
				if (isElementPresent(by, driver)){
					if (second > 1) System.out.println("");
					break;
				}
			} catch (Exception e) {}
			Thread.sleep(1000);
			if (second >= 1) System.out.print(second+ " ");
		}
	}
//***********************************************************************************************************************
	public void WaitForElementNotPresent(By by, int timeout, String LOG_FILE, WebDriver driver) throws Exception {
		//System.out.println("WaitForElementNotPresent: " + by+ " ");
		logger.Log(LOG_FILE, "WaitForElementNotPresent: " + by +" ");
		for (int second = 0;; second++) {
			if (second >= timeout) {throw  new Exception("Timeout "+timeout+" sec. Element ("+ by +") IS STILL Present!");}
			try {
				if (isElementPresent(by, driver) == false) {
					if (second > 1) System.out.println("");
					Thread.sleep(500);
					break;
				}
			} catch (Exception e) {}
			Thread.sleep(1000);
			if (second >= 1) System.out.print(second+ " ");
		}
	}	

//***********************************************************************************************************************	
	public String DateString(int year, int month, int day) {
		Calendar calendar = new GregorianCalendar(year, month-1, day);
		String DayOfWeek = null,
				month_string = null,
				date = null;
		int dow = calendar.get(Calendar.DAY_OF_WEEK);
				switch (month){
			case 1: month_string ="Jan"; break;
			case 2: month_string ="Feb"; break;
			case 3: month_string ="Mar"; break;
			case 4: month_string ="Apr"; break;
			case 5: month_string ="May"; break;
			case 6: month_string ="Jun"; break;
			case 7: month_string ="Jul"; break;
			case 8: month_string ="Aug"; break;
			case 9: month_string ="Sep"; break;
			case 10: month_string ="Oct"; break;
			case 11: month_string ="Nov"; break;
			case 12: month_string ="Dec"; break;
		}
		switch (dow){
			case 1: DayOfWeek ="Sun"; break;
			case 2: DayOfWeek ="Mon"; break;
			case 3: DayOfWeek ="Tue"; break;
			case 4: DayOfWeek ="Wed"; break;
			case 5: DayOfWeek ="Thu"; break;
			case 6: DayOfWeek ="Fri"; break;
			case 7: DayOfWeek ="Sat"; break;
		}
			date = DayOfWeek + " " + month_string + " " + day +", " + year;
			return date;
	}
//***********************************************************************************************************************
public void screenShooter(String errorLocation, WebDriver driver){
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			String filename = "src/test/resources/screenshots"+"test_"+errorLocation+"_failed.png";
			FileUtils.copyFile(scrFile, new File(filename));
			//logger.Log(LOG_FILE, "********** Test "+errorLocation+" Failed ************");
			//logger.Log(LOG_FILE," generated test_"+errorLocation+ "_failed.png");
			//logger.Log(LOG_FILE,"**********************************************************************");
		} catch (IOException e) {System.out.println("Couldn't take the screenshot ");}	
	}
//***********************************************************************************************************************
public void logLD(String LOG_FILE, int lineLength, int lineNumber, String str) throws IOException {
		String lineToBeDrawn = str;
		for (int i=0;i<lineLength;i++) lineToBeDrawn=lineToBeDrawn.concat(str);
		for (int i=0; i<lineNumber;i++)	logger.Log(LOG_FILE, lineToBeDrawn);
	}
//***********************************************************************************************************************
public void mouseOverElement(WebElement element, WebDriver driver, String LOG_FILE) throws Exception{

//TBD!!!
	
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		Actions hover = new Actions(driver); 
//		logger.Log(LOG_FILE, "Hovering over "+element.getText());
//		hover.moveToElement(element);
//		Thread.sleep(2000);
//		driver.findElement(By.cssSelector("nav[id*='main-nav']>ul>li>div[id*='course-syllabus-menu']>ul>li>section>h3")).click();
//		logger.Log(LOG_FILE, element.getText()+" link displays << "+ driver.findElement(By.cssSelector("nav[id*='main-nav']>ul>li>div[id*='course-syllabus-menu']>ul>li>section>h3")).getText()+" >> as first header");
	
//	  String windowHandle = driver.getWindowHandle();
//	  Point coordinates = element.getLocation();
//	  Robot robot = new Robot();
//	  robot.mouseMove(coordinates.getX()+20,coordinates.getY()+120);
//	  driver.switchTo().window(windowHandle);
	
	  Actions hover = new Actions(driver); 
	  logger.Log(LOG_FILE, "Hovering over "+element.getText());
	  hover.moveToElement(element);
	 
	  this.WaitForElementVisible(By.cssSelector("nav[id*='main-nav']>ul>li>div[id*='course-syllabus-menu']>ul>li>section>h3"), 120, LOG_FILE, driver);
	  logger.Log(LOG_FILE, element.getText()+" link displays << "+ driver.findElement(By.cssSelector("nav[id*='main-nav']>ul>li>div[id*='course-syllabus-menu']>ul>li>section>h3")).getText()+" >> as first header");
	  
	}
//***********************************************************************************************************************
public WebDriver browserSelector(String browser) throws IOException {
	WebDriver driver=null;
    String platform = System.getProperty("os.name").toLowerCase();
    System.out.println("platform= "+platform);
    if (System.getProperty("bro") == null)
		browser="firefox";
	else 
		browser=System.getProperty("bro");
	
	if (browser.equals("sikuli")){
//       driver = new SikuliFirefoxDriver();
    }

    if (browser.equals("safari")){
		String hub="http://192.168.1.116:4444/wd/hub";
		DesiredCapabilities cap=new DesiredCapabilities();
		cap.setBrowserName("safari");
		cap.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		cap.setJavascriptEnabled(true);
		//cap.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		driver=new RemoteWebDriver(new URL(hub),cap);
		driver=new  Augmenter().augment(driver);
	}
    else if (browser.equals("internetExplorer")) {
        String hub = "http://192.168.1.74:4444/wd/hub";
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName("InternetExplorerDriver");
        try {
            driver = new RemoteWebDriver(new URL(hub), cap);
            driver = new Augmenter().augment(driver);
        } catch (MalformedURLException m) {
            m.printStackTrace();
        }
    }
	else if(browser.equals("internetExplorer10"))
	{
		String hub="http://192.168.1.71:4444/wd/hub";
		DesiredCapabilities cap=new DesiredCapabilities();
		cap.setBrowserName("InternetExplorerDriver ");				
		driver=new RemoteWebDriver(new URL(hub),cap);
		driver=new  Augmenter().augment(driver);
	}
	else if(browser.equals("chrome"))
	{
		//System.setProperty("webdriver.chrome.driver",
		//"src/test/resources/chromedriver_mac"); //for local use
        if ((platform.equals("nix"))||((platform.equals("nux"))))
            System.setProperty("webdriver.chrome.driver", "src/test/resources/utilityFiles/chromedriver_linux"); // for Jenkins
		else if (platform.equals("mac")) System.setProperty("webdriver.chrome.driver", "src/test/resources/utilityFiles/chromedriver_mac_26.0.1383.0");
        else if (platform.equals("xp")) System.setProperty("webdriver.chrome.driver", "src/test/resources/utilityFiles/chromedriver_win.exe");
		driver = new ChromeDriver();
		
	}
	else if(browser.equals("firefox")){
//		File firebug = new File("src/test/resources/utilityFiles/firebug-1.10.6-fx.xpi");
//		   FirefoxProfile profile = new FirefoxProfile();
//		   profile.addExtension(firebug);
//		   profile.setPreference("extensions.firebug.currentVersion", "1.10.6");
//		   profile.setPreference("privacy.popups.showBrowserMessage", "true");
//		   profile.setEnableNativeEvents(true);
//		   driver = new FirefoxDriver(profile);
           driver = new FirefoxDriver();
		}
	return driver;
	}

/**
 * Get the module and env from the maven params and form the BaseURL
 * @param LOG_FILE
 * @return an array testParams[0]=env , testParams[1] = module, testParams[2] = baseURL, testParams[3] = personName
 * @throws IOException 
 */

@SuppressWarnings("null")
public static String[] envModuleSelector(String LOG_FILE) throws IOException {
	String[] testParams = {"init", "init", "init","init", "init"};
	String env = "";
	String module = "";
	String baseURL = "";
    String personName = "";
    String sanityMode = "false";
    int silo = 10;
	
	if (System.getProperty("env")!=null){
         env=System.getProperty("env");
     }
     else{
         env="qa2";
     }
	 
	 if (System.getProperty("moduleName")!=null){
         module=System.getProperty("moduleName");
     }
	 else{
         module="module1";
     }
    if (System.getProperty("personName")!=null){
        personName=System.getProperty("personName");
    }
    else{
        personName="author";
    }

    if (System.getProperty("sanityMode")!=null){
        sanityMode=System.getProperty("sanityMode");
    }

    if (System.getProperty("silo")!=null){
        silo = Integer.parseInt(System.getProperty("silo"));
    }

    logger.Log(LOG_FILE, "Checking validity of combination and making necessary adjustments...");
    if (module.equals("module1")) personName = "author";
    if (module.equals("module2")) personName = "student";
    if (module.equals("module3")) personName = "instructor";
    if ((silo>1)&&(silo!=10)) {
        logger.Log(LOG_FILE, "Only currently working on lb1- and top- links...");
        silo=1;
    }

	 logger.Log(LOG_FILE, "module = "+module);
	 logger.Log(LOG_FILE, "env = "+env);
     logger.Log(LOG_FILE, "personName = "+personName);
	 logger.Log(LOG_FILE, "Now fetching the baseURL... ");
	 
	//--------------------- QA1
	 if ((module.equals("module1"))&&(env.equals("qa"))) baseURL = "http://qam1.h4cloud.com/";
	 
	 if ((module.equals("module2"))&&(env.equals("qa"))) baseURL = "http://qam2.h4cloud.com/";
	 
	 if ((module.equals("module3"))&&(env.equals("qa"))) baseURL = "http://qam3.h4cloud.com/";
	 
	//--------------------- QA2
	 if ((module.equals("module1"))&&(env.equals("qa2"))) baseURL = "http://qa2m1.h4cloud.com/";
	 
	 if ((module.equals("module2"))&&(env.equals("qa2"))) baseURL = "http://qa2m2.h4cloud.com/";
	 
	 if ((module.equals("module3"))&&(env.equals("qa2"))) baseURL = "http://qa2m3.h4cloud.com/";
	 
	 //--------------------- UAT1
	 if ((module.equals("module1"))&&(env.equals("uat"))) baseURL = "http://uat2m1.h4cloud.com/";
	 
	 if ((module.equals("module2"))&&(env.equals("uat"))) baseURL = "http://lb1-uat2m2.h4cloud.com/";
	 
	 if ((module.equals("module3"))&&(env.equals("uat"))) baseURL = "http://lb1-uat2m3.h4cloud.com/";
	 
	 //--------------------- UAT2
	 if ((module.equals("module1"))&&(env.equals("uat2"))) baseURL = "http://uat2m1.h4cloud.com/";
	 
	 if ((module.equals("module2"))&&(env.equals("uat2"))) baseURL = "http://lb1-uat2m2.h4cloud.com/";
	 
	 if ((module.equals("module3"))&&(env.equals("uat2"))) baseURL = "http://lb1-uat2m3.h4cloud.com/";
	 
	//--------------------- ST1   with silos and top level access
	 if ((module.equals("module1"))&&(env.equals("st1"))&&(silo!=10)) baseURL = "http://lb"+silo+"-st1m1.h4cloud.com/";
        else if ((module.equals("module1"))&&(env.equals("st1"))&&(silo==10))  baseURL = "http://top-stm1.h4cloud.com/";
        else if ((module.equals("module1"))&&(env.equals("st")))  baseURL = "http://top-stm1.h4cloud.com/";
	 
	 if ((module.equals("module2"))&&(env.equals("st1"))&&(silo!=10)) baseURL = "http://lb"+silo+"-st1m2.h4cloud.com/";
        else if ((module.equals("module2"))&&(env.equals("st1"))&&(silo==10))  baseURL = "http://top-stm2.h4cloud.com/";
        else if ((module.equals("module2"))&&(env.equals("st")))  baseURL = "http://top-stm2.h4cloud.com/";
	 
	 if ((module.equals("module3"))&&(env.equals("st1"))&&(silo!=10)) baseURL = "http://lb"+silo+"-st1m3.h4cloud.com/";
        else if ((module.equals("module3"))&&(env.equals("st1"))&&(silo==10))  baseURL = "http://top-stm3.h4cloud.com/";
        else if ((module.equals("module3"))&&(env.equals("st")))  baseURL = "http://top-stm3.h4cloud.com/";
	 
	//--------------------- ST2   with silos and top level access
	 if ((module.equals("module1"))&&(env.equals("st2"))&&(silo!=10)) baseURL = "http://lb"+silo+"-st2m1.h4cloud.com/";
        else if ((module.equals("module1"))&&(env.equals("st2"))&&(silo==10))  baseURL = "http://top-stm1.h4cloud.com/";
        else if ((module.equals("module1"))&&(env.equals("st")))  baseURL = "http://top-stm1.h4cloud.com/";
	 
	 if ((module.equals("module2"))&&(env.equals("st2"))&&(silo!=10)) baseURL = "http://lb"+silo+"-st2m2.h4cloud.com/";
        else if ((module.equals("module2"))&&(env.equals("st2"))&&(silo==10))  baseURL = "http://top-stm2.h4cloud.com/";
        else if ((module.equals("module2"))&&(env.equals("st")))  baseURL = "http://top-stm2.h4cloud.com/";
	 
	 if ((module.equals("module3"))&&(env.equals("st2"))&&(silo!=10)) baseURL = "http://lb"+silo+"-st2m3.h4cloud.com/";
        else if ((module.equals("module3"))&&(env.equals("st2"))&&(silo==10))  baseURL = "http://top-stm3.h4cloud.com/";
        else if ((module.equals("module3"))&&(env.equals("st")))  baseURL = "http://top-stm3.h4cloud.com/";

    //--------------------- PROD 1   with silos and top level access
    if ((module.equals("module1"))&&(env.equals("prod1"))&&(silo!=10)) baseURL = "http://lb"+silo+"-prod1m1.h4cloud.com/";
        else if ((module.equals("module1"))&&(env.equals("prod1"))&&(silo==10))  baseURL = "http://top-prodm1.h4cloud.com/";
        else if ((module.equals("module1"))&&(env.equals("prod")))  baseURL = "http://top-prodm1.h4cloud.com/";

    if ((module.equals("module2"))&&(env.equals("prod1"))&&(silo!=10)) baseURL = "http://lb"+silo+"-prod1m2.h4cloud.com/";
        else if ((module.equals("module2"))&&(env.equals("prod1"))&&(silo==10))  baseURL = "http://top-prodm2.h4cloud.com/";
        else if ((module.equals("module2"))&&(env.equals("prod")))  baseURL = "http://top-prodm2.h4cloud.com/";

    if ((module.equals("module3"))&&(env.equals("prod1"))&&(silo!=10)) baseURL = "http://lb"+silo+"-prod1m3.h4cloud.com/";
        else if ((module.equals("module3"))&&(env.equals("prod1"))&&(silo==10))  baseURL = "http://top-prodm3.h4cloud.com/";
        else if ((module.equals("module3"))&&(env.equals("prod")))  baseURL = "http://top-prodm3.h4cloud.com/";

    //--------------------- PROD 2   with silos and top level access
    if ((module.equals("module1"))&&(env.equals("prod2"))&&(silo!=10)) baseURL = "http://lb"+silo+"-prod2m1.h4cloud.com/";
        else if ((module.equals("module1"))&&(env.equals("prod2"))&&(silo==10))  baseURL = "http://top-prodm1.h4cloud.com/";
        else if ((module.equals("module1"))&&(env.equals("prod")))  baseURL = "http://top-prodm1.h4cloud.com/";

    if ((module.equals("module2"))&&(env.equals("prod2"))&&(silo!=10)) baseURL = "http://lb"+silo+"-prod2m2.h4cloud.com/";
        else if ((module.equals("module2"))&&(env.equals("prod2"))&&(silo==10))  baseURL = "http://top-prodm2.h4cloud.com/";
        else if ((module.equals("module2"))&&(env.equals("prod")))  baseURL = "http://top-prodm2.h4cloud.com/";

    if ((module.equals("module3"))&&(env.equals("prod2"))&&(silo!=10)) baseURL = "http://lb"+silo+"-prod2m3.h4cloud.com/";
        else if ((module.equals("module3"))&&(env.equals("prod2"))&&(silo==10))  baseURL = "http://top-prodm3.h4cloud.com/";
        else if ((module.equals("module3"))&&(env.equals("prod")))  baseURL = "http://top-prodm3.h4cloud.com/";

	 logger.Log(LOG_FILE, "baseURL = "+baseURL);
	 
	 testParams[0]= env;
	 testParams[1]= module;
	 testParams[2]= baseURL;
     testParams[3]= personName;
     testParams[4] = sanityMode;
	 
	 return testParams;
	}


//        public JSONObject dbConnect(String query, String LOG_FILE, GenericMethods metoda) throws SQLException {
//
//            Connection conn = null;
//            String url = "jdbc:mysql://qa2db1.h4cloud.com:3306/";
//            String dbName = "core";
//            String driver = "com.mysql.jdbc.Driver";
//            String userName = "aUt0t3sT_r";
//            String password = "w3b_dR1VeR";
//            JSONObject queryResponse = new JSONObject();
//
//            try{
//                Class.forName(driver).newInstance();// create object of Driver
//                conn = DriverManager.getConnection(url + dbName, userName, password);
//                // connection will be established
//                metoda.logLD(LOG_FILE, 40, 1, "-");
//                logger.Log(LOG_FILE, "querying the DB!");
//
//                // *******************Statement******************
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery(query);
//
//                int responseCounter = 0;
//                while(rs.next()){
//                    String queryStringResponse =  rs.getString(1);
//                   // queryResponse = (JSONObject) new JSONParser().parse(queryStringResponse);
//                    logger.Log(LOG_FILE, "Read the json "+queryStringResponse);
//                    queryResponse = (JSONObject) new JSONParser().parse(queryStringResponse);
//                    responseCounter++;
//                }
//                if(responseCounter!=1) {
//                    logger.Log(LOG_FILE, "Query response has either too few or too many lines ");
//                }
//                metoda.logLD(LOG_FILE, 40, 1, "-");
//
//
//
//            }catch(Exception e){
//                e.printStackTrace();
//            }finally{
//                conn.close();
//            }
//
//            return queryResponse;
//        }

    public  String dbConnect(String query, String LOG_FILE, GenericMethods metoda) throws SQLException {

        Connection conn = null;
        String url = "jdbc:mysql://qa2db1.h4cloud.com:3306/";
        String dbName = "core";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "aUt0t3sT_r";
        String password = "w3b_dR1VeR";
//        JSONObject queryResponse = new JSONObject();
        String queryStringResponse = "init";

        try{
            Class.forName(driver).newInstance();// create object of Driver
            conn = DriverManager.getConnection(url + dbName, userName, password);
            // connection will be established
            metoda.logLD(LOG_FILE, 40, 1, "-");
            logger.Log(LOG_FILE, "querying the DB!");

            // *******************Statement******************
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            int responseCounter = 0;
            while(rs.next()&&(responseCounter<1)){
                queryStringResponse =  rs.getString(1);
                // queryResponse = (JSONObject) new JSONParser().parse(queryStringResponse);
                logger.Log(LOG_FILE, "Read the response: "+queryStringResponse);
//                HTML
//                queryResponse = (JSONObject) new HTML().parse(queryStringResponse);
                responseCounter++;
            }
//            do {
//                queryStringResponse =  rs.getString(1);
//                // queryResponse = (JSONObject) new JSONParser().parse(queryStringResponse);
//                logger.Log(LOG_FILE, "Read the response: "+queryStringResponse);
////                HTML
////                queryResponse = (JSONObject) new HTML().parse(queryStringResponse);
//                responseCounter++;
//            }while((rs.next())&&(responseCounter<2));

            if(responseCounter<1) {
                logger.Log(LOG_FILE, "Query response has too few lines");
            }
            if(responseCounter>1) {
                logger.Log(LOG_FILE, "Query response has too many lines");
            }
            logger.Log(LOG_FILE, queryStringResponse);
            metoda.logLD(LOG_FILE, 40, 1, "-");



        }catch(Exception e){
            e.printStackTrace();
        }finally{
            conn.close();
        }

        return queryStringResponse;
    }
    public static int getResponseCode(String urlString) throws MalformedURLException, IOException {
        URL u = new URL(urlString);
        HttpURLConnection huc =  (HttpURLConnection)  u.openConnection();
        huc.setRequestMethod("GET");
        huc.connect();
        return huc.getResponseCode();
    }
    public static boolean Overlap(WebElement e1, WebElement e2, String LOG_FILE, WebDriver driver) {
        try {
            int x1, x2, y1, y2, x3, x4, y3, y4;
            boolean overlap = false;
            logger.Log(LOG_FILE, "Check if elements identified by: " + e1.getAttribute("class") + " and " + e2.getAttribute("class") + " Overlap!");

            x1 = e1.getLocation().x;
            x2 = x1 + e1.getSize().width;
            y1 = e1.getLocation().y;
            y2 = y1 + e1.getSize().height;

            x3 = e2.getLocation().x;
            x4 = x3 + e2.getSize().width;
            y3 = e2.getLocation().y;
            y4 = y3 + e2.getSize().height;

            do {
                // Check to see if vertices of 2nd rectangle are inside 1st
                overlap = VertexIn(x3, y3, x1, x2, y1, y2);
                if (overlap)
                    break;
                overlap = VertexIn(x4, y3, x1, x2, y1, y2);
                if (overlap)
                    break;
                overlap = VertexIn(x3, y4, x1, x2, y1, y2);
                if (overlap)
                    break;
                overlap = VertexIn(x4, y4, x1, x2, y1, y2);
                if (overlap)
                    break;
                // Check to see if vertices of 1st rectangle are inside 2nd
                overlap = VertexIn(x1, y1, x3, x4, y3, y4);
                if (overlap)
                    break;
                overlap = VertexIn(x2, y1, x3, x4, y3, y4);
                if (overlap)
                    break;
                overlap = VertexIn(x1, y2, x3, x4, y3, y4);
                if (overlap)
                    break;
                overlap = VertexIn(x2, y2, x3, x4, y3, y4);
                if (overlap)
                    break;

                overlap = CrossOverlap(x1, x2, y1, y2, x3, x4, y3, y4);
                if (overlap)
                    break;
                overlap = CrossOverlap(x3, x4, y3, y4, x1, x2, y1, y2);
                if (overlap)
                    break;

                break;
            } while (true);
            if (overlap)
                logger.Log(LOG_FILE, "Elements Overlap maybe ther are the same elements");
            else
                logger.Log(LOG_FILE, "Elements don't Overlap! OK!");
            return overlap;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    // ***********************************************************************************************************************
    public static boolean VertexIn(int x, int y, int a1, int a2, int b1, int b2)
            throws Exception {

        if (((a1 <= x) && (x <= a2)) && ((b1 <= y) && (y <= b2))) {
            return true;
        } else
            return false;
    }

    // ***********************************************************************************************************************
    public static boolean CrossOverlap(int x1, int x2, int y1, int y2, int x3,
                                       int x4, int y3, int y4) throws Exception {

        if (((x1 <= x3) && (x4 <= x2)) && ((y3 <= y1) && (y2 <= y4))) {
            return true;
        } else
            return false;

    }

    // ***********************************************************************************************************************
    public static boolean CheckXaligned(By by1, By by2, String LOG_FILE,
                                        WebDriver driver) throws Exception {
        boolean xalligned = false;
        try {
            logger.Log(LOG_FILE, "Verify if elements identified by: " + by1
                    + " and " + by2 + " are X aligned!!");

            if (driver.findElement(by1).getLocation().x == driver.findElement(by2)
                    .getLocation().x) {
                xalligned = true;
                logger.Log(LOG_FILE, "Elements are aligned! OK!");
            } else
                logger.Log(LOG_FILE, "Elements don't align!!!");


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return xalligned;
    }

    public static boolean CheckXaligned(WebElement el1, WebElement el2, String LOG_FILE,
                                        WebDriver driver) throws Exception {
        boolean xalligned = false;
        try{
            logger.Log(LOG_FILE, "Verify if elements identified by: " + el1.getAttribute("class") + " and " + el2.getAttribute("class") + " are X aligned!!");

            if (el1.getLocation().x == el2.getLocation().x) {
                xalligned = true;
                logger.Log(LOG_FILE, "Elements are aligned! OK!");
            } else
                logger.Log(LOG_FILE, "Elements don't align!!!");
        }catch(Exception e){
            logger.Log(LOG_FILE, ">> Exception in Xalignment method");
        }
        return xalligned;

    }


    // ***********************************************************************************************************************
    public static boolean CheckYaligned(By by1, By by2, String LOG_FILE,
                                        WebDriver driver) throws Exception {

        logger.Log(LOG_FILE, "Verify if elements identified by: " + by1
                + " and " + by2 + " are Y aligned!!");
        boolean yalligned = false;
        if (driver.findElement(by1).getLocation().y == driver.findElement(by2)
                .getLocation().y) {
            yalligned = true;
            logger.Log(LOG_FILE, "Elements are aligned! OK!");
        } else
            logger.Log(LOG_FILE, "Elements don't align!!!");

        return yalligned;

    }

    public static boolean CheckYaligned(WebElement el1, WebElement el2, String LOG_FILE,
                                        WebDriver driver) throws Exception {

        logger.Log(LOG_FILE, "Verify if elements identified by: " + el1.getAttribute("id")
                + " and " + el2.getAttribute("id") + " are Y aligned!!");
        boolean yalligned = false;
        if (el1.getLocation().y == el2
                .getLocation().y) {
            yalligned = true;
            logger.Log(LOG_FILE, "Elements are aligned! OK!");
        } else
            logger.Log(LOG_FILE, "Elements don't align!!!");

        return yalligned;
    }

    public static void perfLogger(String log, String name) throws IOException{
        PrintWriter pw = null;
        //logger.Log("output/logs/REPORT.log", log);
        String filename = name;
        pw = new PrintWriter(new FileWriter(filename, true));
        ArrayList<String> perf_log = new ArrayList<String>();
//        total_time =  ((double)(current_date.getTime() - date_prev.getTime())/1000);
//        Performance.logger("HomeToFlights="+ total_time, PERFORMANCE_LOG);
        perf_log.add(log);
        //System.out.println(perf_log);
        //int totalElements = perf_log.size();
        pw.println(log);
	/*if (log.equals("end")){
		for (int i=0;i<totalElements-1;i++){
		pw.println(perf_log.get(i));
	}
	//perf_log = new ArrayList<String>();
	}*/

        if (pw != null)
            pw.close();
    }

}