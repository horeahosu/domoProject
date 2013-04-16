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

public class CosCumparaturi_Scenariul3 {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	static String LOG_FILE = "src/test/resources/REPORT.log";
	
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE_TVLED);
	}
	
	/**
	 * Scenariul 3: Un user nou pe site adauga in cosul de cumparaturi un produs (televizor) iar apoi il sterge.
	 * Verific daca cosul de cumparaturi nu contine niciun produs: "Cosul Dvs. nu contine nici un produs"
	 * @throws IOException 
	 * 
	 */
	
	@Test
	public void TestScenariul3() throws InterruptedException, IOException
	{
		
		logger.Log(LOG_FILE, "Scenariul 3: Un user nou pe site adauga in cosul de cumparaturi un produs (televizor) iar apoi il sterge. Verific daca cosul de cumparaturi nu contine niciun produs: Cosul Dvs. nu contine nici un produs");
		d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr[4]/td/a/img")).click();
		System.out.println("dau click pe primu TV");
		Thread.sleep(2000);
		if (h.isElementPresent(By.xpath("/html/body/div/div[3]/form/div[2]/div[3]/table/tbody/tr[3]/td/div/span/input"))!=true)
		{
			d.navigate().back();
			Thread.sleep(3000);
		}
		d.findElement(By.xpath("/html/body/div/div[3]/form/div[2]/div[3]/table/tbody/tr[3]/td/div/span/input")).click();
		
		//Verific butonul Pasul2
		if (h.isElementPresent(By.name("Pasul2"))!=true)
		{
			d.navigate().back();
			Thread.sleep(3000);
		}
		
		if (h.isElementPresent(By.name("Pasul2"))!=true)
		{
			logger.Log(LOG_FILE, "TestScenariul3: TEST FAILLED !!!");
			h.screenShooter("TestScenariul3", d);
		}
		assertTrue(h.isElementPresent(By.name("Pasul2")));
		
		//Sterg Produsul
		d.findElement(By.linkText("Sterge")).click();
		Thread.sleep(2000);
		
		//verific mesajul Cosul Dvs. nu contine nici un produs.
		if (h.checkForTextPresentOnThePage("Cosul Dvs. nu contine nici un produs")!=true)
		{
			d.navigate().back();
			Thread.sleep(3000);
		}
		
		if (h.checkForTextPresentOnThePage("Cosul Dvs. nu contine nici un produs"))
			logger.Log(LOG_FILE, "TestScenariul3: TEST PASSED");
			else
			{
				logger.Log(LOG_FILE, "TestScenariul3: TEST FAILLED !!!");
				h.screenShooter("TestScenariul3", d);
			}
			assertTrue(h.checkForTextPresentOnThePage("Cosul Dvs. nu contine nici un produs"));
		
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}

}
