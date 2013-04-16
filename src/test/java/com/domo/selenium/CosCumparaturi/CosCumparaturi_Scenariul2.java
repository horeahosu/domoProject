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
		d.get(Constants.SITE);
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
		
		logger.Log(LOG_FILE, "TestScenariul 2: Un user nou pe site adauga in cosul de cumparaturi un produs (televizor). Verific pretul produsului in sectiunea Cos Cumparaturi, atat pe pagina Pasul1 cat si Pasul2");
		h.waitForElementPresent(By.className("logo"), 5);
		d.findElement(By.linkText("Televizoare & Home cinema")).click();
		Thread.sleep(2000);
		if (h.isElementPresent(By.linkText("LED TV"))==false)
		{
			d.navigate().back();
			Thread.sleep(3000);
			d.findElement(By.linkText("Televizoare & Home cinema")).click();
		}
		
		d.findElement(By.linkText("LED TV")).click();
		d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr[4]/td/a/img")).click();
		
		//stochez pretul
		h.waitForElementPresent(By.xpath("/html/body/div/div[3]/form/div[2]/div[3]/table/tbody/tr[2]/td/div/span/strong"), 5);
		String price1 = d.findElement(By.xpath("/html/body/div/div[3]/form/div[2]/div[3]/table/tbody/tr[2]/td/div/span/strong")).getText();
		System.out.println(price1);
		
		//adaug in cos
		d.findElement(By.xpath("/html/body/div/div[3]/form/div[2]/div[3]/table/tbody/tr[3]/td/div/span/input")).click();		                        
		
		//verific pret pe pagina Pasul1
		String price2 = d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td/table/tbody/tr[5]/td[4]/strong")).getText();
		
		//Compar preturile
		if (price1.equals(price2)!=true)
		{
			logger.Log(LOG_FILE, "TestScenariul2: TEST FAILLED !!!");
			h.screenShooter("TestScenariul2", d);
		}
		assertEquals(price1, price2);
		
		//Verific butonul Pasul2
		if (h.isElementPresent(By.name("Pasul2"))!=true)
		{
			logger.Log(LOG_FILE, "TestScenariul2: TEST FAILLED !!!");
			h.screenShooter("TestScenariul2", d);
		}
		assertTrue(h.isElementPresent(By.name("Pasul2")));
		
		//verific pretul pe pagina Pasul2
		d.findElement(By.name("Pasul2")).click();
		Thread.sleep(2000);
		if (h.isElementPresent(By.id("TotalPlata"))==false)
		{
			d.navigate().back();
			Thread.sleep(3000);
			d.findElement(By.name("Pasul2")).click();
		}
		String price3 = d.findElement(By.id("TotalPlata")).getText();
		System.out.println(price3);
		
		String price2_int = h.SplitString1(price2);
		System.out.println(price2_int);
		if (price3.contains(price2_int)!=true)
		{
			logger.Log(LOG_FILE, "TestScenariul2: TEST FAILLED !!!");
			h.screenShooter("TestScenariul2", d);
		}
		else
			logger.Log(LOG_FILE, "TestScenariul2: TEST PASSED");
		assertTrue(price3.contains(price2_int));
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}

}
