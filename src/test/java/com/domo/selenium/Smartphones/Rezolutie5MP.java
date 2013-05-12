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

public class Rezolutie5MP {
	
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
	 * TestSmartphonesRezolutie5MP(): Verifica optiunea de a afisa pe pagina doar produsele avand rezolutie foto 5MP
	 * 
	 */
	
	@Test
	public void TestSmartphonesMarcaApple() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestSmartphonesRezolutie5MP(): Verifica optiunea de a afisa pe pagina doar produsele avand rezolutie foto 5MP");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Smartphones")==false)
		{
			logger.Log(LOG_FILE, "TestSmartphonesRezolutie5MP(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestSmartphonesRezolutie5MP", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Smartphones"));
		d.findElement(By.id("CB_3_5_Megapixeli")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/table/tbody/tr[2]/td[2]"))==true)
			{	
				//procesez
				if (d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/table/tbody/tr[2]/td[2]")).getText().contains(Constants.SMARTPHONE_5MP)==false)
				{
					logger.Log(LOG_FILE, "TestSmartphonesRezolutie5MP(): TEST FAILLED !!!");
					h.screenShooter("TestSmartphonesRezolutie5MP", d);
				}
				assertTrue(d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/table/tbody/tr[2]/td[2]")).getText().contains(Constants.SMARTPHONE_5MP));
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
				{ if (d.findElement(By.id("NextPage")).isDisplayed()==true)
					{
						d.findElement(By.id("NextPage")).click();
						i=1;
						j=1;
						Thread.sleep(2000);
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse avand rezolutie foto de"+Constants.SMARTPHONE_5MP+" sunt: "+k);
						logger.Log(LOG_FILE, "TestSmartphonesRezolutie5MP(): TEST PASSED");
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



