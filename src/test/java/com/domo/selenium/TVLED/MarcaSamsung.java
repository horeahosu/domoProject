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

public class MarcaSamsung {
	
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
			logger.Log(LOG_FILE, "TestTVLEDMarcaSamsung(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestTVLEDMarcaSamsung", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED"));
		
		
		//sub 1900
		d.findElement(By.id("CB_0_Samsung")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/a[2]/strong"))==true)
			{	
				//procesez
				if (d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/a[2]/strong")).getText().contains(Constants.MARCA_SAMSUNG)==false)
				{
					logger.Log(LOG_FILE, "TestTVLEDMarcaSamsung(): TEST FAILLED !!!");
					h.screenShooter("TestTVLEDMarcaSamsung", d);
				}
				assertTrue(d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/a[2]/strong")).getText().contains(Constants.MARCA_SAMSUNG));
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
