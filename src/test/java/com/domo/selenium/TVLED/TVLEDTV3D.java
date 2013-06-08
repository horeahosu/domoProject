package com.domo.selenium.TVLED;

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

public class TVLEDTV3D {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	static String LOG_FILE = "src/test/resources/REPORT.log";
	int i=3, j=1, k=0;
	String pret_produs;
	boolean b=false;
	
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE_TVLED);
	}
	
	/**
	 * TestTV3D(): Verifica optiunea de a afisa pe pagina doar produsele 3D
	 * 
	 */
	
	@Test
	public void TestTV3D() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestTV3D: Verifica optiunea de a afisa pe pagina doar produsele 3D");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED")==false)
		{
			logger.Log(LOG_FILE, "TestPretLEDTV(): Wrong page TEST FAILED !!!");
			h.screenShooter("TestPretLEDTV", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED"));
		
		if (h.isElementPresent(By.id("CB_3_Da"))==false)
		{
			logger.Log(LOG_FILE, "TestTV3D(): Option is not available TEST FAILED !!!");
			h.screenShooter("TestTV3D", d);
		}
		assertTrue(h.isElementPresent(By.id("CB_3_Da"))==true);
		
		d.findElement(By.id("CB_3_Da")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_details_2_xpath, i,j)))==true)
			{	
				//procesez
				if (d.findElement(By.xpath(String.format(Constants.domo_product_details_2_xpath, i,j))).getText().contains(Constants.TV3D)==false)
				{
					logger.Log(LOG_FILE, "TestTV3D(): TEST FAILED !!!");
					h.screenShooter("TestTV3D", d);
				}
				assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_details_2_xpath, i,j))).getText().contains(Constants.TV3D));
				//procesez
				
				k++;
				j++;
				if (j==5)
				{
					i=i+9;
					j=1;
				}
			} 
			else 
				{ if (h.isElementPresent(By.id("NextPage2"))==true && (d.findElement(By.id("NextPage")).isDisplayed()==true))
					{
						d.findElement(By.id("NextPage2")).click();
						Thread.sleep(2000);
						i=3;
						j=1;
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse 3D sunt: "+k);
						logger.Log(LOG_FILE, "TestTV3D(): TEST PASSED");
						i=3;
						j=1;
					}
				}
		}
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}

}
