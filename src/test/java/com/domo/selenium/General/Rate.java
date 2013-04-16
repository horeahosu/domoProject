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

public class Rate {
	
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
	 * TestRate(): Verifica informatii legate de tipurile de rate oferite de DOMO
	 * @throws IOException 
	 * 
	 */
	
	@Test
	public void TestRate() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestRate(): Verifica informatii legate de tipurile de rate oferite de DOMO");
		h.waitForElementPresent(By.className("logo"), 5);
		d.findElement(By.linkText("Rate DOMO")).click();
		
		if (d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table/tbody/tr/td")).getText().contains(Constants.Rate_C1)==false)
		{
			logger.Log(LOG_FILE, "TestRate(): "+Constants.Rate_C1+"not found TEST FAILLED !!!");
			h.screenShooter("TestRate", d);
		}
		assertTrue(d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table/tbody/tr/td")).getText().contains(Constants.Rate_C1));
		
		if (d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table[2]/tbody/tr/td")).getText().contains(Constants.Rate_C2)==false)
		{
			logger.Log(LOG_FILE, "TestRate():"+Constants.Rate_C2+" not found TEST FAILLED !!!");
			h.screenShooter("TestRate", d);
		}
		assertTrue(d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table[2]/tbody/tr/td")).getText().contains(Constants.Rate_C2));
		
		//Credit Potrivit
		d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table[2]/tbody/tr[3]/td/table/tbody/tr/td/table[2]/tbody/tr[2]/td/table/tbody/tr/td/a/img")).click();
		Thread.sleep(2000);
		
		if (h.checkForTextPresentOnThePage(Constants.Rate_C3)==false)
		{
			logger.Log(LOG_FILE, "TestRate():"+Constants.Rate_C3+" not found TEST FAILLED !!!");
			h.screenShooter("TestRate", d);
		}
		assertTrue(h.checkForTextPresentOnThePage(Constants.Rate_C3));
		d.navigate().back();
		Thread.sleep(2000);
		
		
		//Credit Comod
		h.waitForElementPresent(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table[2]/tbody/tr[3]/td/table/tbody/tr/td/table[2]/tbody/tr[2]/td[2]/table/tbody/tr/td/a/img"),3);
		d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table[2]/tbody/tr[3]/td/table/tbody/tr/td/table[2]/tbody/tr[2]/td[2]/table/tbody/tr/td/a/img")).click();
		Thread.sleep(2000);
		
		if (h.checkForTextPresentOnThePage(Constants.Rate_C4)==false)
		{
			logger.Log(LOG_FILE, "TestRate():"+Constants.Rate_C4+" not found TEST FAILLED !!!");
			h.screenShooter("TestRate", d);
		}
		assertTrue(h.checkForTextPresentOnThePage(Constants.Rate_C4));
		d.navigate().back();
		Thread.sleep(2000);
		
		//Credit Pentru Tine
		h.waitForElementPresent(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table[2]/tbody/tr[3]/td/table/tbody/tr/td/table[2]/tbody/tr[2]/td[3]/table/tbody/tr/td/a/img"),3);
		d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table[2]/tbody/tr[3]/td/table/tbody/tr/td/table[2]/tbody/tr[2]/td[3]/table/tbody/tr/td/a/img")).click();
		Thread.sleep(2000);
		
		if (h.checkForTextPresentOnThePage(Constants.Rate_C5)==false)
		{
			logger.Log(LOG_FILE, "TestRate():"+Constants.Rate_C5+" not found TEST FAILLED !!!");
			h.screenShooter("TestRate", d);
		}
		assertTrue(h.checkForTextPresentOnThePage(Constants.Rate_C5));
		d.navigate().back();
		Thread.sleep(2000);
		
		//Credit in 10 rate
		h.waitForElementPresent(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table[2]/tbody/tr[3]/td/table/tbody/tr/td/table[2]/tbody/tr[2]/td[4]/table/tbody/tr/td/a/img"),3);
		d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table[2]/tbody/tr[3]/td/table/tbody/tr/td/table[2]/tbody/tr[2]/td[4]/table/tbody/tr/td/a/img")).click();
		                     
		Thread.sleep(2000);
		if (h.checkForTextPresentOnThePage(Constants.Rate_C6)==false)
		{
			logger.Log(LOG_FILE, "TestRate():"+Constants.Rate_C6+" not found TEST FAILLED !!!");
			h.screenShooter("TestRate", d);
		}
		else
			logger.Log(LOG_FILE, "TestRate(): TEST PASSED");
		assertTrue(h.checkForTextPresentOnThePage(Constants.Rate_C6));
				
		
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}

}
