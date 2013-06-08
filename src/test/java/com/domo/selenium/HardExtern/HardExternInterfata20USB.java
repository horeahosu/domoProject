package com.domo.selenium.HardExtern;

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

public class HardExternInterfata20USB {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	static String LOG_FILE = "src/test/resources/REPORT.log";
	int i=3, j=1, k=0;
	boolean b=false;
	
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE_HARDEXTERN);
	}
	
	/**
	 * TestHardExternInterfata20USB(): Verifica optiunea de a afisa pe pagina doar produsele avand Interfata 2.0USB
	 * 
	 */
	
	@Test
	public void TestHardExternInterfata20USB() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestHardExternInterfata20USB(): Verifica optiunea de a afisa pe pagina doar produsele avand Interfata 2.0USB");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Hard")==false)
		{
			logger.Log(LOG_FILE, "TestHardExternInterfata20USB(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestHardExternInterfata20USB", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Hard"));
		
		if (h.isElementPresent(By.id("CB_3_USB_2.0"))==false)
		{
			logger.Log(LOG_FILE, "TestHardexternInterfata20USB(): Option is not available TEST FAILLED !!!");
			h.screenShooter("TestHardexternInterfata20USB", d);
		}
		assertTrue(h.isElementPresent(By.id("CB_3_USB_2.0"))==true);
		
		
		d.findElement(By.id("CB_3_USB_2.0")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_details_2_xpath, i,j)))==true)
			{	
				//procesez
				if (d.findElement(By.xpath(String.format(Constants.domo_product_details_2_xpath, i,j))).getText().contains(Constants.INTERFATA_20))	
				assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_details_2_xpath, i,j))).getText().contains(Constants.INTERFATA_20));
				else
				{
					if (d.findElement(By.xpath(String.format(Constants.domo_product_details_3_xpath, i,j))).getText().contains(Constants.INTERFATA_20)==false)
					{
						logger.Log(LOG_FILE, "TestHardExternInterfata20USB(): TEST FAILLED !!!");
						h.screenShooter("TestHardExternInterfata20USB", d);
					}
					assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_details_3_xpath, i,j))).getText().contains(Constants.INTERFATA_20));
				}
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
				{ if (h.isElementPresent(By.id("NextPage2"))==true && (d.findElement(By.id("NextPage")).isDisplayed()==true))
					{
						d.findElement(By.id("NextPage2")).click();
						i=3;
						j=1;
						Thread.sleep(2000);
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse avand interfata 2.0USB: "+k);
						logger.Log(LOG_FILE, "TestHardExternInterfata20USB(): TEST PASSED");
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