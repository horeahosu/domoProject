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

public class MarcaAllview {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	static String LOG_FILE = "src/test/resources/REPORT.log";
	int i=1, j=1, k=0;
	boolean b=false;
	
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE_SMARTPHONES);
	}
	
	/**
	 * TestSmartphonesMarcaAllview(): Verifica optiunea de a afisa pe pagina doar produsele avand marca Apple
	 * 
	 */
	
	@Test
	public void TestSmartphonesMarcaAllview() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestSmartphonesMarcaAllview(): Verifica optiunea de a afisa pe pagina doar produsele avand marca Allview");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Smartphones")==false)
		{
			logger.Log(LOG_FILE, "TestSmartphonesMarcaAllview(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestSmartphonesMarcaAllview()", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Smartphones"));
		d.findElements(By.linkText("altele...")).get(0).click();
		d.findElement(By.id("CB_0_Allview")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_name_xpath, i,j)))==true)
			{	
				//procesez
				if (d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, i,j))).getText().contains(Constants.SMARTPHONE_ALLVIEW)==false)
				{
					logger.Log(LOG_FILE, "TestSmartphonesMarcaAllview(): TEST FAILLED !!!");
					h.screenShooter("TestSmartphonesMarcaAllview", d);
				}
				assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, i,j))).getText().contains(Constants.SMARTPHONE_ALLVIEW));
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
						Thread.sleep(2000);
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse "+Constants.SMARTPHONE_ALLVIEW+" sunt: "+k);
						logger.Log(LOG_FILE, "TestSmartphonesMarcaAllview: TEST PASSED");
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


