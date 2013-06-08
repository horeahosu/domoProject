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

public class NotebookDiagonala_14 {
	
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
	 * TestNotebookDiagonala14(): Verifica optiunea de a afisa pe pagina doar produsele avand diagonala: 14
	 * 
	 */
	
	@Test
	public void TestNotebookDiagonala14() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestNotebookDiagonala14(): Verifica optiunea de a afisa pe pagina doar produsele avand diagonala: 14");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Notebook")==false)
		{
			logger.Log(LOG_FILE, "TestNotebookDiagonala14(): Wrong page TEST FAILED !!!");
			h.screenShooter("TestNotebookDiagonala14", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("Notebook"));

		d.findElements(By.linkText("altele...")).get(2).click();
		if (h.isElementPresent(By.id("CB_3_14``"))==false)
		{
			logger.Log(LOG_FILE, "TestNotebookDiagonala14(): Option is not available TEST FAILED !!!");
			h.screenShooter("TestNotebookDiagonala14", d);
		}
		assertTrue(h.isElementPresent(By.id("CB_3_14``"))==true);
		
		d.findElement(By.id("CB_3_14``")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_details_2_xpath, i,j)))==true)
			{	
				//procesez
				if (d.findElement(By.xpath(String.format(Constants.domo_product_details_2_xpath, i,j))).getText().contains(Constants.DIAGONALA_14)==false)
				{
					logger.Log(LOG_FILE, "TestNotebookDiagonala14(): TEST FAILED !!!");
					h.screenShooter("TestNotebookDiagonala14", d);
				}
				assertTrue(d.findElement(By.xpath(String.format(Constants.domo_product_details_2_xpath, i,j))).getText().contains(Constants.DIAGONALA_14));
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
						i=3;
						j=1;
						Thread.sleep(2000);
					}
					else
					{
						b=true;
						logger.Log(LOG_FILE, "Numarul de produse avand diagonala "+Constants.DIAGONALA_14+" sunt: "+k);
						logger.Log(LOG_FILE, "TestNotebookDiagonala14(): TEST PASSED");
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
