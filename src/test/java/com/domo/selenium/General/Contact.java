package com.domo.selenium.General;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.domo.selenium.util.Constants;
import com.domo.selenium.util.WebDriverHelper;
import com.domo.selenium.util.logger;

public class Contact {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	static String LOG_FILE = "src/test/resources/REPORT.log";
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE);
	}
	
	/**
	 * TestContact(): Verifica anumite informatii legate de "Contacte" (telefon, call center, adresa) 
	 * @throws IOException 
	 * 
	 */
	
	@Test
	public void TestContact() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestContact(): Verifica anumite informatii legate de Contacte (telefon, call center, adresa)");
		h.waitForElementPresent(By.className("logo"), 5);
		d.findElement(By.linkText("Contact")).click();
	
		//Contact
		if (d.findElement(By.cssSelector("html.cufon-active body div table tbody tr td table tbody tr td.text_black_mare")).getText().contains(Constants.Contact_C1)==false)
		{
			logger.Log(LOG_FILE, "TestContact(): "+Constants.Contact_C1+"not found  TEST FAILLED !!!");
			h.screenShooter("TestScenariul1", d);
		}
		assertTrue(d.findElement(By.cssSelector("html.cufon-active body div table tbody tr td table tbody tr td.text_black_mare")).getText().contains(Constants.Contact_C1));
		
		//Telefon
		if (h.checkForTextPresentOnThePage(Constants.Contact_C2)==false)
		{
			logger.Log(LOG_FILE, "TestContact():"+Constants.Contact_C2+"not found  TEST FAILLED !!!");
			h.screenShooter("TestScenariul1", d);
		}
		assertTrue(h.checkForTextPresentOnThePage(Constants.Contact_C2));
		
		//CallCenter
		if (h.checkForTextPresentOnThePage(Constants.Contact_C3)==false)
		{
			logger.Log(LOG_FILE, "TestContact(): "+Constants.Contact_C3+"not found  TEST FAILLED !!!");
			h.screenShooter("TestScenariul1", d);
		}
		assertTrue(h.checkForTextPresentOnThePage(Constants.Contact_C3));
		
		//FAX
		if (h.checkForTextPresentOnThePage(Constants.Contact_C4)==false)
		{
			logger.Log(LOG_FILE, "TestContact(): "+Constants.Contact_C4+"not found   TEST FAILLED !!!");
			h.screenShooter("TestScenariul1", d);
		}
		assertTrue(h.checkForTextPresentOnThePage(Constants.Contact_C4));
		
		//Telverde
		if (h.checkForTextPresentOnThePage(Constants.Contact_C5)==false)
		{
			logger.Log(LOG_FILE, "TestContact(): "+Constants.Contact_C5+"not found   TEST FAILLED !!!");
			h.screenShooter("TestScenariul1", d);
		}
		assertTrue(h.checkForTextPresentOnThePage(Constants.Contact_C5));
		
		//Posta
		if (h.checkForTextPresentOnThePage(Constants.Contact_C6)==false)
		{
			logger.Log(LOG_FILE, "TestContact(): "+Constants.Contact_C6+"not found   TEST FAILLED !!!");
			h.screenShooter("TestScenariul1", d);
		}
		else
			logger.Log(LOG_FILE, "TestContact(): TEST PASSED");
		assertTrue(h.checkForTextPresentOnThePage(Constants.Contact_C6));
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}

}
