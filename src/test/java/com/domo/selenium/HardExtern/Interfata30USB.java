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

public class Interfata30USB {
	
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
	 * TestHardExternInterfata30USB(): Verifica optiunea de a afisa pe pagina doar produsele avand Interfata 3.0USB
	 * 
	 */
	
	@Test
	public void TestHardExternInterfata30USB() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestHardExternInterfata30USB(): Verifica optiunea de a afisa pe pagina doar produsele avand Interfata 3.0USB");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext"), 5);
		if (d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext")).getText().contains("Hard")==false)
		{
			logger.Log(LOG_FILE, "TestHardExternInterfata30USB(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestHardExternInterfata30USB", d);
		}
		assertTrue(d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext")).getText().contains("Hard"));
		
		
		//sub 1900
		d.findElement(By.id("CB_3_USB_3.0")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/table/tbody/tr[2]/td[2]"))==true)
			{	
				//procesez
				if (d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/table/tbody/tr[2]/td[2]")).getText().contains(Constants.INTERFATA_30))
				assertTrue(d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/table/tbody/tr[2]/td[2]")).getText().contains(Constants.INTERFATA_30));
				else
				{
					if (d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/table/tbody/tr[3]/td[2]")).getText().contains(Constants.INTERFATA_30)==false)
					{
						logger.Log(LOG_FILE, "TestHardExternInterfata30USB(): TEST FAILLED !!!");
						h.screenShooter("TestHardExternInterfata30USB", d);
					}
					assertTrue(d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/table/tbody/tr[3]/td[2]")).getText().contains(Constants.INTERFATA_30));
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
				{ if (d.findElement(By.id("NextPage")).isDisplayed()==true)
					{
						d.findElement(By.id("NextPage")).click();
						i=3;
						j=1;
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse avand interfata 3.0USB: "+k);
						logger.Log(LOG_FILE, "TestHardExternInterfata30USB: TEST PASSED");
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
