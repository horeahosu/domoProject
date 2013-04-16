package com.domo.selenium.HardExtern;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.domo.selenium.util.Constants;
import com.domo.selenium.util.FileUtil;
import com.domo.selenium.util.WebDriverHelper;

public class PretMaxim270 {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	FileUtil f;
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
	 * TestHardexternPretMaxim270(): Verifica optiunea de a afisa pe pagina doar produsele avand pretul mai mic de 270
	 * 
	 */
	
	@Test
	public void TestHardexternPretMaxim270() throws InterruptedException, IOException
	{
		
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext"), 5);
		assertTrue(d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext")).getText().contains("Hard"));
		
		
		//sub 1900
		d.findElement(By.id("CB_1_Sub_270_lei")).click();
		Thread.sleep(2000);
		
		while (b!=true)
		{
			if ((h.isElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/span/strong"))==true) || (h.isElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/span[2]/strong"))==true))
			{	
				//procesez
				if (h.isElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/span[2]/strong"))==true)
					pret_produs = d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/span[2]/strong")).getText();
				else 
					pret_produs = d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/span/strong")).getText();
				System.out.println(pret_produs);
			
				int amount = h.ConvertAmountToInt(pret_produs);
				
				assertTrue(amount<Constants.PRET_270);
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
						i=6;
						j=1;
					}
					else
					{
						b=true;
						System.out.println("Numarul de produse avand pretul mai mic ca 270 sunt: "+k);
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
