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

public class MarcaWesternDigital {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	static String LOG_FILE = "src/test/resources/REPORT.log";
	int i=1, j=1, k=0;
	boolean b=false;
	
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE_HARDEXTERN);
	}
	
	/**
	 * TestHardExternMarcaWesternDigital(): Verifica optiunea de a afisa pe pagina doar produsele avand marca WesternDigital
	 * 
	 */
	
	@Test
	public void TestHardExternMarcaWesternDigital() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestHardExternMarcaWesternDigital(): Verifica optiunea de a afisa pe pagina doar produsele avand marca WesternDigital");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Hard")==false)
		{
			logger.Log(LOG_FILE, "TestHardExternMarcaWesternDigital(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestHardExternMarcaWesternDigital", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Hard"));
		
		if (h.isElementPresent(By.id("CB_0_Western_Digital"))==false)
		{
			logger.Log(LOG_FILE, "TestHardexternMarcaWesternDigital(): Option is not available TEST FAILLED !!!");
			h.screenShooter("TestHardexternMarcaWesternDigital", d);
		}
		assertTrue(h.isElementPresent(By.id("CB_0_Western_Digital"))==true);
		
		d.findElement(By.id("CB_0_Western_Digital")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_name_xpath, i,j)))==true)
			{	
				//procesez
				if (d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, i,j))).getText().contains(Constants.MARCA_WESTERNDIGITAL2))
				assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, i,j))).getText().contains(Constants.MARCA_WESTERNDIGITAL2));
				else
				{
					if (d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, i,j))).getText().contains(Constants.MARCA_WESTERNDIGITAL)==false)
					{
						logger.Log(LOG_FILE, "TestHardExternMarcaWesternDigital(): TEST FAILLED !!!");
						h.screenShooter("TestHardExternMarcaWesternDigital", d);
					}
					assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, i,j))).getText().contains(Constants.MARCA_WESTERNDIGITAL));
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
						i=1;
						j=1;
						Thread.sleep(2000);
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse "+Constants.MARCA_WESTERNDIGITAL+" sunt: "+k);
						logger.Log(LOG_FILE, "TestHardExternMarcaWesternDigital(): TEST PASSED");
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
