package com.domo.selenium.CosCumparaturi;

import static org.junit.Assert.assertEquals;
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

public class CosCumparaturi_Scenariul2 {
	
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
	 * Scenariul 2: Un user nou pe site adauga in cosul de cumparaturi un produs (televizor).
	 * Verific pretul produsului in sectiunea Cos Cumparaturi, atat pe pagina Pasul1 cat si Pasul2
	 * @throws IOException 
	 * 
	 */
	
	@Test
	public void TestScenariul2() throws InterruptedException, IOException
	{
		
		logger.Log(LOG_FILE, "TestScenariul2: Un user nou pe site adauga in cosul de cumparaturi un produs (televizor). Verific pretul produsului in sectiunea Cos Cumparaturi, atat pe pagina Pasul1 cat si Pasul2");
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED")==false)
		{
			logger.Log(LOG_FILE, "TestScenariul2(): Wrong page TEST FAILED !!!");
			h.screenShooter("TestScenariul2", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED"));
		
		d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, 1,1))).click();
		
		//stochez pretul
		h.waitForElementPresent(By.xpath("/html/body/div/div[3]/div[2]/div[3]/table/tbody/tr[2]/td/div/span/strong"), 5);
		String price1 = d.findElement(By.xpath("/html/body/div/div[3]/div[2]/div[3]/table/tbody/tr[2]/td/div/span/strong")).getText();
		//adaug in cos
		d.findElement(By.xpath("/html/body/div/div[3]/div[2]/div[3]/table/tbody/tr[3]/td/div/div/input")).click();		                        
		Thread.sleep(2000);
		//verific pret pe pagina Pasul1
		String price2 = d.findElement(By.xpath("/html/body/div[4]/div/div/div/div/div/form/div/div/table/tbody/tr/td/span")).getText();
		//Compar preturile
		if (price2.contains(price1)!=true)
		{
			logger.Log(LOG_FILE, "TestScenariul2: TEST FAILED !!!");
			h.screenShooter("TestScenariul2", d);
		}
		assertTrue(price2.contains(price1));
		
		//Verific optiunea de a trece la Pasul2
		if (h.isElementPresent(By.xpath("/html/body/div[4]/div/div/div/div/div/form/div/div[2]/a"))!=true)
		{								
			logger.Log(LOG_FILE, "TestScenariul2: TEST FAILED !!!");
			h.screenShooter("TestScenariul2", d);
		}
		assertTrue(h.isElementPresent(By.xpath("/html/body/div[4]/div/div/div/div/div/form/div/div[2]/a")));
		
		//verific pretul pe pagina Pasul2
		d.findElement(By.xpath("/html/body/div[4]/div/div/div/div/div/form/div/div[2]/a")).click();
		Thread.sleep(2000);
		if (h.isElementPresent(By.id("TotalPlata"))==false)
		{
			d.navigate().back();
			Thread.sleep(3000);
			d.findElement(By.name("Pasul2")).click();
		}
		String price3 = d.findElement(By.id("TotalPlata")).getText();
		String price1_int = h.SplitString1(price1);
		if (price3.contains(price1_int)!=true)
		{
			logger.Log(LOG_FILE, "TestScenariul2: TEST FAILED !!!");
			h.screenShooter("TestScenariul2", d);
		}
		else
			logger.Log(LOG_FILE, "TestScenariul2: TEST PASSED");
		assertTrue(price3.contains(price1_int));
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}

}
