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

public class GeneralLogo {

	
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
	 * TestLogo(): Verifica prezenta logo-ului "DOMO" pe pagina principala 
	 * @throws IOException 
	 * 
	 */
	
	@Test
	public void TestLogo() throws IOException
	{
		logger.Log(LOG_FILE, "TestLogo(): Verifica prezenta logo-ului DOMO pe pagina principala");
		h.waitForElementPresent(By.className("logo"), 5);
		
		if (d.findElement(By.className("logo")).getText().contains(Constants.Logo_C1)==false)
		{
			logger.Log(LOG_FILE, "TestCumCumpar(): Logo "+Constants.Logo_C1+"not found TEST FAILLED !!!");
			h.screenShooter("TestCumCumpar", d);
		}
		else
			logger.Log(LOG_FILE, "TestCumCumpar(): TEST PASSED");
		assertTrue(d.findElement(By.className("logo")).getText().contains(Constants.Logo_C1));
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}

}
