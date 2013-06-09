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
	 * Scenariul 3: Un user nou pe site adauga in cosul de cumparaturi un produs (televizor) iar apoi sterge produsul din cos.
	 * Verific mesajul: "Cosul Dvs. nu contine nici un produs"
	 * @throws IOException 
	 * 
	 */
	
	@Test
	public void TestScenariul3() throws InterruptedException, IOException
	{
		
		logger.Log(LOG_FILE, "TestScenariul 3: Un user nou pe site adauga in cosul de cumparaturi un produs (televizor) iar apoi sterge produsul din cos. Verific mesajul: Cosul Dvs. nu contine nici un produs");
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED")==false)
		{
			logger.Log(LOG_FILE, "TestScenariul3(): Wrong page TEST FAILED !!!");
			h.screenShooter("TestScenariul3", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED"));
		
		d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, 1,1))).click();
		
		//adaug in cos
		d.findElement(By.xpath("/html/body/div/div[3]/div[2]/div[3]/table/tbody/tr[3]/td/div/div/input")).click();		                        
		Thread.sleep(2000);
		
		//verific ca produsul exista in cosul de cumparaturi pe pagina Pasul1
		if (h.isElementPresent(By.xpath("/html/body/div[4]/div/div/div/div/div/form/div/table/tbody/tr/td[3]"))==false)
		{
			logger.Log(LOG_FILE, "TestScenariul3(): No product found in shopper basket TEST FAILED !!!");
			h.screenShooter("TestScenariul3", d);
		}
		assertTrue(h.isElementPresent(By.xpath("/html/body/div[4]/div/div/div/div/div/form/div/table/tbody/tr/td[3]")));
		
		//Sterg produsul
		d.findElement(By.className("removeproduct")).click();
		
		//Verific mesajul "Cosul Dvs. nu contine nici un produs"
		if (h.checkForTextPresentOnThePage("Cosul Dvs. nu contine nici un produs"))
			logger.Log(LOG_FILE, "TestScenariul1: TEST PASSED");
			else
			{
				logger.Log(LOG_FILE, "TestScenariul1: TEST FAILED !!!");
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
