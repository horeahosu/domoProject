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
		Thread.sleep(2000);
		d.findElement(By.className("cart-link")).click();
		Thread.sleep(2000);
		if (d.findElement(By.id(Constants.cos_cumparauri_mesaj_cos_gol_id)).getText().contains("Cosul Dvs. nu contine nici un produs")==true)
		logger.Log(LOG_FILE, "TestScenariul1: TEST PASSED");
		else
		{
			logger.Log(LOG_FILE, "TestScenariul1: TEST FAILED !!!");
			h.screenShooter("TestScenariul1", d);
		}
		assertTrue(d.findElement(By.id(Constants.cos_cumparauri_mesaj_cos_gol_id)).getText().contains("Cosul Dvs. nu contine nici un produs"));
	}
	//h.isElementPresent(By.xpath(Constants.cos_cumparauri_mesaj_cos_gol_xpath))==true
	@After
	public void quitDriver()
	{
		d.quit();
	}
}
