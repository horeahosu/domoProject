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

public class CumCumpar {
	
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
	 * TestCumCumpar(): Verifica prezenta capitoloelor importante din sectiunea "Cum Cumpar" 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * 
	 */
	
	@Test
	public void TestCumCumpar() throws IOException, InterruptedException
	{
		logger.Log(LOG_FILE, "TestCumCumpar(): Verifica prezenta capitoloelor importante din sectiunea Cum Cumpar");
		h.waitForElementPresent(By.className("logo"), 5);
		d.findElement(By.linkText("Cum cumpar")).click();
		Thread.sleep(2000);
		
		
		if (d.findElement(By.xpath(Constants.domo_cum_cumpar_display_title1_xpath)).getText().contains(Constants.CumCumpar_C1)==false)
		{
			logger.Log(LOG_FILE, "TestCumCumpar():"+Constants.CumCumpar_C1+"not found TEST FAILLED !!!");
			h.screenShooter("TestCumCumpar", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_cum_cumpar_display_title1_xpath)).getText().contains(Constants.CumCumpar_C1));
		
		if (d.findElement(By.xpath(Constants.domo_cum_cumpar_display_title2_xpath)).getText().contains(Constants.CumCumpar_C2)==false)
		{
			logger.Log(LOG_FILE, "TestCumCumpar():"+Constants.CumCumpar_C2+"not found TEST FAILLED !!!");
			h.screenShooter("TestCumCumpar", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_cum_cumpar_display_title2_xpath)).getText().contains(Constants.CumCumpar_C2));
		
		if (d.findElement(By.xpath(Constants.domo_cum_cumpar_display_title3_xpath)).getText().contains(Constants.CumCumpar_C3)==false)
		{
			logger.Log(LOG_FILE, "TestCumCumpar():"+Constants.CumCumpar_C3+"not found TEST FAILLED !!!");
			h.screenShooter("TestCumCumpar", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_cum_cumpar_display_title3_xpath)).getText().contains(Constants.CumCumpar_C3));
		
		if (d.findElement(By.xpath(Constants.domo_cum_cumpar_display_title4_xpath)).getText().contains(Constants.CumCumpar_C4)==false)
		{
			logger.Log(LOG_FILE, "TestCumCumpar():"+Constants.CumCumpar_C4+"not found TEST FAILLED !!!");
			h.screenShooter("TestCumCumpar", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_cum_cumpar_display_title4_xpath)).getText().contains(Constants.CumCumpar_C4));
		
		if (d.findElement(By.xpath(Constants.domo_cum_cumpar_display_title5_xpath)).getText().contains(Constants.CumCumpar_C5)==false)
		{
			logger.Log(LOG_FILE, "TestCumCumpar():"+Constants.CumCumpar_C5+"not found TEST FAILLED !!!");
			h.screenShooter("TestCumCumpar", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_cum_cumpar_display_title5_xpath)).getText().contains(Constants.CumCumpar_C5));
		
		if (d.findElement(By.xpath(Constants.domo_cum_cumpar_display_title6_xpath)).getText().contains(Constants.CumCumpar_C6)==false)
		{
			logger.Log(LOG_FILE, "TestCumCumpar():"+Constants.CumCumpar_C6+"not found TEST FAILLED !!!");
			h.screenShooter("TestCumCumpar", d);
		}
		else
			logger.Log(LOG_FILE, "TestCumCumpar(): TEST PASSED");
		assertTrue(d.findElement(By.xpath(Constants.domo_cum_cumpar_display_title6_xpath)).getText().contains(Constants.CumCumpar_C6));
			
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}

}
