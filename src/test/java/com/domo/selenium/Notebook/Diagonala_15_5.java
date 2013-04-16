package com.domo.selenium.Notebook;

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

public class Diagonala_15_5 {
	
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
		d.get(Constants.SITE_NOTEBOOK);
	}
	
	/**
	 * TestNotebookDiagonala15_5(): Verifica optiunea de a afisa pe pagina doar produsele avand diagonala: 15,5
	 * 
	 */
	
	@Test
	public void TestNotebookDiagonala15_5() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestNotebookDiagonala15_5(): Verifica optiunea de a afisa pe pagina doar produsele avand diagonala: 15,5");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext"), 5);
		if (d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext")).getText().contains("Notebook")==false)
		{
			logger.Log(LOG_FILE, "TestNotebookDiagonala15_5(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestNotebookDiagonala15_5", d);
		}
		assertTrue(d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext")).getText().contains("Notebook"));
		
		d.findElements(By.linkText("altele...")).get(2).click();
		d.findElement(By.id("CB_3_15.5``")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath("html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/table/tbody/tr[2]/td[2]"))==true)
			{	
				//procesez
				if (d.findElement(By.xpath("html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/table/tbody/tr[2]/td[2]")).getText().contains(Constants.DIAGONALA_15_5)==false)
				{
					logger.Log(LOG_FILE, "TestNotebookDiagonala15_5(): TEST FAILLED !!!");
					h.screenShooter("TestNotebookDiagonala15_5", d);
				}
				assertTrue(d.findElement(By.xpath("html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/table/tbody/tr[2]/td[2]")).getText().contains(Constants.DIAGONALA_15_5));
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
						Thread.sleep(2000);
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse avand diagonala "+Constants.DIAGONALA_15_5+" sunt: "+k);
						logger.Log(LOG_FILE, "TestNotebookDiagonala15_5(): TEST PASSED");
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
