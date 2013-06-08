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

public class HardExternPret_270_360 {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	static String LOG_FILE = "src/test/resources/REPORT.log";
	int i=6, j=1, k=0;
	String pret_produs;
	boolean b=false;
	
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE_HARDEXTERN);
	}
	
	/**
	 * TestHardexternPret_270_360(): Verifica optiunea de a afisa pe pagina doar produsele avand pretul intre 270 si 360
	 * 
	 */
	
	@Test
	public void TestHardexternPret_270_360() throws InterruptedException, IOException
	{
		
		//verific daca sunt pe site-ul bun
		logger.Log(LOG_FILE, "TestHardexternPret_270_360(): Verifica optiunea de a afisa pe pagina doar produsele avand pretul intre 270 si 360");
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Hard")==false)
		{
			logger.Log(LOG_FILE, "TestHardexternPret_270_360(): Wrong page TEST FAILED !!!");
			h.screenShooter("TestHardexternPret_270_360", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Hard"));
		
		if (h.isElementPresent(By.id("CB_1_Intre_270_-_360_lei"))==false)
		{
			logger.Log(LOG_FILE, "TestHardexternPret_270_360(): Option is not available TEST FAILED !!!");
			h.screenShooter("TestHardexternPret_270_360", d);
		}
		assertTrue(h.isElementPresent(By.id("CB_1_Intre_270_-_360_lei"))==true);			
				
		d.findElement(By.id("CB_1_Intre_270_-_360_lei")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if ((h.isElementPresent(By.xpath(String.format(Constants.domo_product_cut_price_xpath, i,j)))==true) || (h.isElementPresent(By.xpath(String.format(Constants.domo_product_actual_price_xpath, i,j)))==true))
			{	
				//procesez
				if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_cut_price_xpath, i,j)))==true)
					pret_produs = d.findElement(By.xpath(String.format(Constants.domo_product_actual_price_xpath, i,j))).getText();
				else 
					pret_produs = d.findElement(By.xpath(String.format(Constants.domo_product_previous_price_xpath, i,j))).getText();
			
				int amount = h.ConvertAmountToInt(pret_produs);
				
				if (((amount>Constants.HARD_EXTERN_PRET_270)==false) || (amount<Constants.HARD_EXTERN_PRET_360)==false)
				{
					logger.Log(LOG_FILE, "TestHardexternPret_270_360(): A product with non coresponding price was found TEST FAILED !!!");
					h.screenShooter("TestHardexternPret_270_360", d);
				}
				assertTrue(amount>Constants.HARD_EXTERN_PRET_270);
				assertTrue(amount<Constants.HARD_EXTERN_PRET_360);				
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
						i=6;
						j=1;
						Thread.sleep(2000);
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse avand pretul intre 270 si 360 sunt: "+k);
						logger.Log(LOG_FILE, "TestHardexternPret_270_360(): TEST PASSED");
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
