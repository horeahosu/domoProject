package com.domo.selenium.Smartphones;

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

public class SmartphonesRezolutie3MP {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	static String LOG_FILE = "src/test/resources/REPORT.log";
	int i=3, j=1, k=0;
	boolean b=false;
	
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE_SMARTPHONES);
	}
	
	/**
	 * TestSmartphonesRezolutie3MP(): Verifica optiunea de a afisa pe pagina doar produsele avand rezolutie foto 3MP
	 * 
	 */
	
	@Test
	public void TestSmartphonesRezolutie3MP() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestSmartphonesRezolutie3MP(): Verifica optiunea de a afisa pe pagina doar produsele avand rezolutie foto 3MP");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Smartphones")==false)
		{
			logger.Log(LOG_FILE, "TestSmartphonesRezolutie3MP(): Wrong page TEST FAILED !!!");
			h.screenShooter("TestSmartphonesRezolutie3MP", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Smartphones"));
		
		if (h.isElementPresent(By.id("CB_3_3_Megapixeli"))==false)
		{
			logger.Log(LOG_FILE, "TestSmartphonesRezolutie3MP(): Option is not available TEST FAILED !!!");
			h.screenShooter("TestSmartphonesRezolutie3MP", d);
		}
		assertTrue(h.isElementPresent(By.id("CB_3_3_Megapixeli"))==true);
		
		d.findElement(By.id("CB_3_3_Megapixeli")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_details_2_xpath, i,j)))==true)
			{	
				//procesez
				if (d.findElement(By.xpath(String.format(Constants.domo_product_details_2_xpath, i,j))).getText().contains(Constants.SMARTPHONE_3MP)==false)
				{
					logger.Log(LOG_FILE, "TestSmartphonesRezolutie3MP(): TEST FAILED !!!");
					h.screenShooter("TestSmartphonesRezolutie3MP", d);
				}
				assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_details_2_xpath, i,j))).getText().contains(Constants.SMARTPHONE_3MP));
				//procesez
				
				k++;
				//System.out.println(k);
				j++;
				if (j==5)
				{
					i=i+9;
					j=3;
				}
			} 
			else 
				{ if (h.isElementPresent(By.id("NextPage2"))==true && (d.findElement(By.id("NextPage")).isDisplayed()==true))
					{
						d.findElement(By.id("NextPage2")).click();
						i=1;
						j=1;
						Thread.sleep(2000);
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse avand rezolutie foto de"+Constants.SMARTPHONE_3MP+" sunt: "+k);
						logger.Log(LOG_FILE, "TestSmartphonesRezolutie3MP(): TEST PASSED");
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


