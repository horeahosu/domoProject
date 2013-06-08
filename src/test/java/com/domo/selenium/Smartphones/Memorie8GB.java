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

public class Memorie8GB {
	
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
	 * TestSmartphonesMemoie8GB(): Verifica optiunea de a afisa pe pagina doar produsele avand memorie interna 8GB
	 * 
	 */
	
	@Test
	public void TestSmartphonesMemoie8GB() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestSmartphonesMemoie8GB(): Verifica optiunea de a afisa pe pagina doar produsele avand memorie interna 8GB");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Smartphones")==false)
		{
			logger.Log(LOG_FILE, "TestSmartphonesMemorie8GB(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestSmartphonesMemoie8GB", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Smartphones"));
		
		if (h.isElementPresent(By.id("CB_4_8_GB"))==false)
		{
			logger.Log(LOG_FILE, "TestSmartphonesMemorie8GB(): Option is not available TEST FAILLED !!!");
			h.screenShooter("TestSmartphonesMemorie8GB", d);
		}
		assertTrue(h.isElementPresent(By.id("CB_4_8_GB"))==true);
		
		d.findElement(By.id("CB_4_8_GB")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_details_3_xpath, i,j)))==true)
			{	
				//procesez
				if (d.findElement(By.xpath(String.format(Constants.domo_product_details_3_xpath, i,j))).getText().contains(Constants.SMARTPHONE_8GB)==false)
				{
					logger.Log(LOG_FILE, "TestSmartphonesMemorie8GB(): TEST FAILLED !!!");
					h.screenShooter("TestSmartphonesMemorie8GB", d);
				}
				assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_details_3_xpath, i,j))).getText().contains(Constants.SMARTPHONE_8GB));
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
						logger.Log(LOG_FILE, "Numarul de produse avand memorie interna de"+Constants.SMARTPHONE_8GB+" sunt: "+k);
						logger.Log(LOG_FILE, "TestSmartphonesMemorie8GB(): TEST PASSED");
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
