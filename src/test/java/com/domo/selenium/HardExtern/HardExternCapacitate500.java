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

public class HardExternCapacitate500 {
	
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
	 * TestHardExternCapacitate500(): Verifica optiunea de a afisa pe pagina doar produsele avand capacitate 500GB
	 * 
	 */
	
	@Test
	public void TestHardExternCapacitate500() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestHardExternCapacitate500(): Verifica optiunea de a afisa pe pagina doar produsele avand capacitate 500GB");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Hard")==false)
		{
			logger.Log(LOG_FILE, "TestHardExternCapacitate500(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestHardExternCapacitate500", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Hard"));
		
		if (h.isElementPresent(By.id("CB_2_500_Gb"))==false)
		{
			logger.Log(LOG_FILE, "TestHardexternCapacitate500(): Option is not available TEST FAILLED !!!");
			h.screenShooter("TestHardexternCapacitate500", d);
		}
		assertTrue(h.isElementPresent(By.id("CB_2_500_Gb"))==true);
		
		d.findElement(By.id("CB_2_500_Gb")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_details_1_xpath, i,j)))==true)
			{	
				//procesez
				if (d.findElement(By.xpath(String.format(Constants.domo_product_details_1_xpath, i,j))).getText().contains(Constants.CAPACITATE_500)==false)
				{
					logger.Log(LOG_FILE, "TestHardExternCapacitate500(): TEST FAILLED !!!");
					h.screenShooter("TestHardExternCapacitate500", d);
				}
				assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_details_1_xpath, i,j))).getText().contains(Constants.CAPACITATE_500));
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
						logger.Log(LOG_FILE, "Numarul de produse avand capacitatea 500Gb sunt: "+k);
						logger.Log(LOG_FILE, "TestHardExternCapacitate500(): TEST PASSED");
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
