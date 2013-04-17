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

public class MarcaPanasonic {
	
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
	 * TestTVLEDMarcaPanasonic(): Verifica optiunea de a afisa pe pagina doar produsele avand marca Panasonic
	 * 
	 */
	
	@Test
	public void TestTVLEDMarcaPanasonic() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestTVLEDMarcaPanasonic(): Verifica optiunea de a afisa pe pagina doar produsele avand marca Panasonic");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext"), 5);
		if (d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext")).getText().contains("LED")==false)
		{
			logger.Log(LOG_FILE, "TestTVLEDMarcaPanasonic(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestTVLEDMarcaPanasonic", d);
		}
		assertTrue(d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext")).getText().contains("LED"));
		
		
		//sub 1900
		d.findElement(By.id("CB_0_Panasonic")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/a[2]/strong"))==true)
			{	
				//procesez
				if (d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/a[2]/strong")).getText().contains(Constants.MARCA_PANASONIC)==false)
				{
					logger.Log(LOG_FILE, "TestTVLEDMarcaPanasonic(): TEST FAILLED !!!");
					h.screenShooter("TestTVLEDMarcaPanasonic", d);
				}
				assertTrue(d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/a[2]/strong")).getText().contains(Constants.MARCA_PANASONIC));
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
						logger.Log(LOG_FILE, "Numarul de produse "+Constants.MARCA_PANASONIC+" sunt: "+k);
						logger.Log(LOG_FILE, "TestTVLEDMarcaPanasonic(): TEST PASSED");
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
