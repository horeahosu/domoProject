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

public class TVLEDMarcaSamsung {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	static String LOG_FILE = "src/test/resources/REPORT.log";
	int i=1, j=1, k=0;
	String pret_produs;
	boolean b=false;
	
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE_TVLED);
	}
	
	/**
	 * TestTVLEDMarcaSamsung(): Verifica optiunea de a afisa pe pagina doar produsele avand marca Samsung
	 * 
	 */
	
	@Test
	public void TestTVLEDMarcaSamsung() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestTVLEDMarcaSamsung(): Verifica optiunea de a afisa pe pagina doar produsele avand marca Samsung");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED")==false)
		{
			logger.Log(LOG_FILE, "TestTVLEDMarcaSamsung(): Wrong page TEST FAILED !!!");
			h.screenShooter("TestTVLEDMarcaSamsung", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED"));
		
		d.findElement(By.linkText("altele...")).click();
		if (h.isElementPresent(By.id("CB_0_Samsung"))==false)
		{
			logger.Log(LOG_FILE, "TestTVLEDMarcaSamsung(): Option is not available TEST FAILED !!!");
			h.screenShooter("TestTVLEDMarcaSamsung", d);
		}
		assertTrue(h.isElementPresent(By.id("CB_0_Samsung"))==true);
		
		d.findElement(By.id("CB_0_Samsung")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_name_xpath, i,j)))==true)
			{	
				//procesez
				if (d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, i,j))).getText().contains(Constants.MARCA_SAMSUNG)==false)
				{
					logger.Log(LOG_FILE, "TestTVLEDMarcaSamsung(): TEST FAILED !!!");
					h.screenShooter("TestTVLEDMarcaSamsung", d);
				}
				assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, i,j))).getText().contains(Constants.MARCA_SAMSUNG));
				//procesez
				
				k++;
				//System.out.println(k);
				j++;
				if (j==5)
				{
					i=i+9;
					j=1;
				}
			} 
			else 
				{ if (h.isElementPresent(By.id("NextPage"))==true && (d.findElement(By.id("NextPage")).isDisplayed()==true))
					{
						d.findElement(By.id("NextPage")).click();
						Thread.sleep(2000);
						i=1;
						j=1;
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse "+Constants.MARCA_SAMSUNG+" sunt: "+k);
						logger.Log(LOG_FILE, "TestTVLEDMarcaSamsung(): TEST PASSED");
						i=1;
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
