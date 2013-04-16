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

public class TelefonComenzi {
	
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
	 * TestTelefonComezi(): Verifica numarul de telefon pentru comenzi pe pagina principala 
	 * @throws IOException 
	 * 
	 */
	
	@Test
	public void TestTelefonComezi() throws IOException
	{
		logger.Log(LOG_FILE, "TestTelefonComezi(): Verifica numarul de telefon pentru comenzi pe pagina principala");
		h.waitForElementPresent(By.className("logo"), 5);
		
		if (d.findElement(By.className("contact")).getText().contains("021.319.81.96"))
			logger.Log(LOG_FILE, "TestTelefonComezi(): TEST PASSED");
			else
			{
				logger.Log(LOG_FILE, "TestTelefonComezi(): TEST FAILLED !!!");
				h.screenShooter("TestTelefonComezi", d);
			}
		assertTrue(d.findElement(By.className("contact")).getText().contains("021.319.81.96"));
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}

}


