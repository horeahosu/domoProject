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

public class Diagonala_56 {
	
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
	 * TestTVLEDDiagonala_56(): Verifica optiunea de a afisa pe pagina doar produsele avand diagonala: 165
	 * 
	 */
	
	@Test
	public void TestDiagonala_56() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestTVLEDDiagonala56(): Verifica optiunea de a afisa pe pagina doar produsele avand diagonala: 56");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED")==false)
		{
			logger.Log(LOG_FILE, "TestTVLEDDiagonala56(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestTVLEDDiagonala56", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED"));
		
		
		d.findElements(By.linkText("altele...")).get(1).click();
		d.findElement(By.id("CB_2_56_cm")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_details_1_xpath, i,j)))==true)
			{	
				//procesez
				if (d.findElement(By.xpath(String.format(Constants.domo_product_details_1_xpath, i,j))).getText().contains(Constants.DIAGONALA_56)==false)
				{
					logger.Log(LOG_FILE, "TestTVLEDDiagonala56(): TEST FAILLED !!!");
					h.screenShooter("TestTVLEDDiagonala56", d);
				}
				assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_details_1_xpath, i,j))).getText().contains(Constants.DIAGONALA_56));
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
				{ if (d.findElement(By.id("NextPage")).isDisplayed()==true)
					{
						d.findElement(By.id("NextPage")).click();
						i=3;
						j=1;
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse avand diagonala "+Constants.DIAGONALA_56+" sunt: "+k);
						logger.Log(LOG_FILE, "TestTVLEDDiagonala56(): TEST PASSED");
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
