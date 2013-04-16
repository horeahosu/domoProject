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

public class MagazineDomo {

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
	 * TestMagazineDomo(): Verifica linkul catre pagina care contine judetele cu magazine DOMO
	 * @throws IOException 
	 * 
	 */
	
	@Test
	public void TestMagazineDomo() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestMagazineDomo(): Verifica linkul catre pagina care contine judetele cu magazine DOMO");
		h.waitForElementPresent(By.className("logo"), 5);
		d.findElement(By.linkText("Magazine DOMO")).click();
		
		if ((d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table/tbody/tr/td")).getText().contains(Constants.MagazineDomo_C1)==false))
		{
			logger.Log(LOG_FILE, "TestMagazineDomo(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestMagazineDomo", d);
		}
		else
			logger.Log(LOG_FILE, "TestMagazineDomo(): TEST PASSED");
		assertTrue(d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td[3]/table/tbody/tr/td")).getText().contains(Constants.MagazineDomo_C1));
		
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}
}
