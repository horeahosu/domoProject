package com.domo.selenium.TVLED;

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
import com.domo.selenium.util.logger;

public class TVLEDPretProduse {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	static String LOG_FILE = "src/test/resources/REPORT.log";
	FileUtil f;
	int i=1, j=1, k=0, c, n;
	String cod_site, pret_site, s;
	String []cod;
	int []pret;
	boolean b=false;
	
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE_TVLED);
	}
	
	/**
	 * TestPretLEDTV(): Verifica pretul produselor TV LED pe baza fisierului: PreturiProduse.xls
	 * 
	 */
	
	@Test
	public void TestPretLEDTV() throws InterruptedException, IOException
	{
		logger.Log(LOG_FILE, "TestPretLEDTV(): Verifica pretul produselor Notebook pe baza fisierului: PreturiProduse.xls");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath(Constants.domo_product_first_title_xpath), 5);
		if (d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED")==false)
		{
			logger.Log(LOG_FILE, "TestPretLEDTV(): Wrong page TEST FAILED !!!");
			h.screenShooter("TestPretLEDTV", d);
		}
		assertTrue(d.findElement(By.xpath(Constants.domo_product_first_title_xpath)).getText().contains("LED"));
		Thread.sleep(2000);
		//retin numarul de produse din coloana excelului
		f = new FileUtil();
		c = f.getExcelRowsNumber("src/test/resources/PreturiProduse.xls", "LEDTV");
		System.out.println("Numarul de produe LED TV: "+c);
		cod = new String [c]; 
		pret = new int [c];
		
		//citesc cod din fisierul excel
		for(int i=0; i<c; i++)
		{
		cod [i]=f.readFromExcel("src/test/resources/PreturiProduse.xls", "LEDTV", i, 0);
		//System.out.println(cod[i]);
		pret[i]= Integer.parseInt(h.SplitString3(f.readFromExcel("src/test/resources/PreturiProduse.xls", "LEDTV", i, 1)));
		//System.out.println(pret[i]);
		}
		
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_name_xpath, i,j)))==true)
			{	
				//procesez 
				cod_site = d.findElement(By.xpath(String.format(Constants.domo_product_name_xpath, i,j))).getText();
				logger.Log(LOG_FILE, "TestPretLEDTV(): Verify product:"+cod_site);
				for (int ii=0; ii<c; ii++)
				{
					n=5;
					if (cod_site.contains(cod[ii]))
					{
						n=n+i;
						if (h.isElementPresent(By.xpath(String.format(Constants.domo_product_previous_price_xpath, n,j)))==true)
							pret_site = d.findElement(By.xpath(String.format(Constants.domo_product_previous_price_xpath, n,j))).getText();
						else 
							pret_site = d.findElement(By.xpath(String.format(Constants.domo_product_actual_price_xpath, n,j))).getText();
					int amount = h.ConvertAmountToInt(pret_site);
					if ((pret[ii]==amount)==false)
					{
						logger.Log(LOG_FILE, "TestPretLEDTV(): "+cod_site+" "+amount+" "+"did not match DATABASE -- TEST FAILED !!!");
						h.screenShooter("TestPretLEDTV", d);
					}
					else
						logger.Log(LOG_FILE, "TestPretLEDTV(): "+cod_site+" "+amount+" "+"MATCHED DATABASE");
					assertTrue(pret[ii]==amount);
					break;
					}
					//System.out.println(ii+1);
					if (ii+1==c)
						logger.Log(LOG_FILE, "TestPretLEDTV(): Product "+cod_site+" does not appear in DATABASE !!");	
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
				{ if (h.isElementPresent(By.id("NextPage"))==true && (d.findElement(By.id("NextPage")).isDisplayed()==true))
					{
						d.findElement(By.id("NextPage")).click();
						Thread.sleep(2000);
						i=1;
						j=1;
					}
					else
					{
						b=true;
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
