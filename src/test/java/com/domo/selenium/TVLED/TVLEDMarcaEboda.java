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

public class TVLEDMarcaEboda {
	
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
	 * TestTVLEDMarcaEboda(): Verifica optiunea de a afisa pe pagina doar produsele avand marca: Eboda
	 * 
	 */
	
	@Test
	public void TestTVLEDMarcaEboda() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestTVLEDMarcaEboda(): Verifica optiunea de a afisa pe pagina doar produsele avand marca: Eboda");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED")==false)
		{
			logger.Log(LOG_FILE, "TestTVLEDMarcaEboda(): Wrong page TEST FAILED !!!");
			h.screenShooter("TestTVLEDMarcaEboda", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED"));
		
		d.findElement(By.linkText("altele...")).click();
		if (h.isElementPresent(By.id("CB_0_E-Boda"))==false)
		{
			logger.Log(LOG_FILE, "TestTVLEDMarcaEboda(): Option is not available TEST FAILED !!!");
			h.screenShooter("TestTVLEDMarcaEboda", d);
		}
		assertTrue(h.isElementPresent(By.id("CB_0_E-Boda"))==true);
		
		d.findElement(By.id("CB_0_E-Boda")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_name_xpath, i,j)))==true)
			{	
				//procesez
				if (d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, i,j))).getText().contains(Constants.MARCA_EBODA)==false)
				{
					logger.Log(LOG_FILE, "TestTVLEDMarcaEboda(): TEST FAILED !!!");
					h.screenShooter("TestTVLEDMarcaEboda", d);
				}
				assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, i,j))).getText().contains(Constants.MARCA_EBODA));
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
						i=1;
						j=1;
						Thread.sleep(2000);
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse "+Constants.MARCA_EBODA+" sunt: "+k);
						logger.Log(LOG_FILE, "TestTVLEDMarcaEboda(): TEST PASSED");
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
