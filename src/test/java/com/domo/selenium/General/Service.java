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

public class Service {

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
	 * TestService(): Verifica informatii legate de Service-ul oferit de DOMO pentru clientii siteului
	 * @throws IOException 
	 * 
	 */
	
	@Test
	public void TestService() throws IOException
	{
		logger.Log(LOG_FILE, "TestService(): Verifica informatii legate de Service-ul oferit de DOMO pentru clientii siteului");
		h.waitForElementPresent(By.className("logo"), 5);
		d.findElement(By.linkText("Service")).click();
		
		if (d.findElement(By.cssSelector("html.cufon-active body div table tbody tr td table tbody tr td.text_black_mare")).getText().contains(Constants.Service_C1)==false)
		{
			logger.Log(LOG_FILE, "TestService(): "+Constants.Service_C1+"not found TEST FAILLED !!!");
			h.screenShooter("TestService", d);
		}
		assertTrue(d.findElement(By.cssSelector("html.cufon-active body div table tbody tr td table tbody tr td.text_black_mare")).getText().contains(Constants.Service_C1));
		
		if (d.findElement(By.cssSelector("html.cufon-active body div table tbody tr td table tbody tr td table tbody tr td.tabel_alb_complet span.text_blue_mare")).getText().contains(Constants.Service_C2)==false)
		{
			logger.Log(LOG_FILE, "TestService(): "+Constants.Service_C2+"not found TEST FAILLED !!!");
			h.screenShooter("TestService", d);
		}
		assertTrue(d.findElement(By.cssSelector("html.cufon-active body div table tbody tr td table tbody tr td table tbody tr td.tabel_alb_complet span.text_blue_mare")).getText().contains(Constants.Service_C2));
		
		if (h.isElementPresent(By.linkText("informatii@domo.ro"))==false)
		{
			logger.Log(LOG_FILE, "TestService(): informatii@domo.ro not found TEST FAILLED !!!");
			h.screenShooter("TestService", d);
		}
		assertTrue(h.isElementPresent(By.linkText("informatii@domo.ro")));
		
		if (h.isElementPresent(By.linkText("service@domo.ro"))==false)
		{
			logger.Log(LOG_FILE, "TestService(): service@domo.ro not found TEST FAILLED !!!");
			h.screenShooter("TestService", d);
		}
		assertTrue(h.isElementPresent(By.linkText("service@domo.ro")));
		
		//Click "Service Online" button
		d.findElement(By.cssSelector("html.cufon-active body div table tbody tr td table tbody tr td table tbody tr td.tabel_alb_complet div a img")).click();
		
		//Switch window - http://service.domo.ro
		h.selectWindowByTitle("Domo Service");
		if (d.findElement(By.cssSelector("html body form#form1 div.login_MainContainer div.login_FormContainer div p.MsoNormal b span")).getText().contains(Constants.Service_C3)==false)
		{
			logger.Log(LOG_FILE, "TestService(): "+Constants.Service_C3+"not found TEST FAILLED !!!");
			h.screenShooter("TestService", d);
		}
		assertTrue(d.findElement(By.cssSelector("html body form#form1 div.login_MainContainer div.login_FormContainer div p.MsoNormal b span")).getText().contains(Constants.Service_C3));
		
		
		d.findElement(By.id("ImageButton1")).click();
		h.waitForElementPresent(By.cssSelector("html body form#form1 div.login_MainContainer div.login_FormContainer div p.MsoNormal b span") ,8);
		if (d.findElement(By.cssSelector("html body form#form1 div.login_MainContainer div.login_FormContainer div p.MsoNormal b span")).getText().contains(Constants.Service_C4)==false)
		{
			logger.Log(LOG_FILE, "TestService(): "+Constants.Service_C4+"not found TEST FAILLED !!!");
			h.screenShooter("TestService", d);
		}
		assertTrue(d.findElement(By.cssSelector("html body form#form1 div.login_MainContainer div.login_FormContainer div p.MsoNormal b span")).getText().contains(Constants.Service_C4));
		
		d.navigate().back();
		h.waitForElementPresent(By.id("ImageButton2"),8);
		d.findElement(By.id("ImageButton2")).click();
		h.waitForElementPresent(By.cssSelector("html body form#form1 div.login_MainContainer div.login_FormContainer p.MsoNormal b span") ,8);
		if (d.findElement(By.cssSelector("html body form#form1 div.login_MainContainer div.login_FormContainer p.MsoNormal b span")).getText().contains(Constants.Service_C5)==false)
		{
			logger.Log(LOG_FILE, "TestService(): "+Constants.Service_C5+"not found TEST FAILLED !!!");
			h.screenShooter("TestService", d);
		}
		else
			logger.Log(LOG_FILE, "TestService(): TEST PASSED");
		assertTrue(d.findElement(By.cssSelector("html body form#form1 div.login_MainContainer div.login_FormContainer p.MsoNormal b span")).getText().contains(Constants.Service_C5));
		
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}
	
}
