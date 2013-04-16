package com.domo.selenium.CosCumparaturi;

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

public class CosCumparaturi_Scenariul1 {

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
	 * Scenariul 1: Un user nou pe site va vedea la sectiunea Cos Cumparaturi mesajul: "Cosul Dvs. nu contine nici un produs"
	 * @throws InterruptedException 
	 * @throws IOException 
	 * 
	 */
	
	@Test
	public void TestScenariul1() throws InterruptedException, IOException 
	{
		
		logger.Log(LOG_FILE, "TestScenariul1:Un user nou pe site va vedea la sectiunea Cos Cumparaturi mesajul: Cosul Dvs. nu contine nici un produs");
		h.waitForElementPresent(By.className("logo"), 5);
		Thread.sleep(3000);
		d.findElement(By.className("cart-link")).click();
		if (h.checkForTextPresentOnThePage("Cosul Dvs. nu contine nici un produs"))
		logger.Log(LOG_FILE, "TestScenariul1: TEST PASSED");
		else
		{
			logger.Log(LOG_FILE, "TestScenariul1: TEST FAILLED !!!");
			h.screenShooter("TestScenariul1", d);
		}
		assertTrue(h.checkForTextPresentOnThePage("Cosul Dvs. nu contine nici un produs"));
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}
}
