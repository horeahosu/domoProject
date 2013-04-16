package com.domo.selenium.Notebook;

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

public class PretNotebook {
	
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
		d.get(Constants.SITE_NOTEBOOK);
	}
	
	/**
	 * TestPretNotebook(): Verifica pretul produselor Notebook pe baza fisierului: PreturiProduse.xls
	 * 
	 */
	
	@Test
	public void TestPretNotebook() throws InterruptedException, IOException
	{
		
		logger.Log(LOG_FILE, "TestPretNotebook(): Verifica pretul produselor Notebook pe baza fisierului: PreturiProduse.xls");
		//verific daca sunt pe site-ul bun
		h.waitForElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext"), 5);
		if (d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext")).getText().contains("Notebook")==false)
		{
			logger.Log(LOG_FILE, "TestPretNotebook(): Wrong page TEST FAILLED !!!");
			h.screenShooter("TestPretNotebook", d);
		}
		assertTrue(d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/div/h2/cufon/cufontext")).getText().contains("Notebook"));
		Thread.sleep(2000);
		//retin numarul de produse din coloana excelului
		f = new FileUtil();
		c = f.getExcelRowsNumber("src/test/resources/PreturiProduse.xls", "NOTEBOOK");
		System.out.println("Numarul de produse NOTEBOOK: "+c);
		cod = new String [c]; 
		pret = new int [c];
		
		//citesc cod din fisierul excel
		for(int i=0; i<c; i++)
		{
		cod [i]=f.readFromExcel("src/test/resources/PreturiProduse.xls", "NOTEBOOK", i, 0);
		//System.out.println(cod[i]);
		pret[i]= Integer.parseInt(h.SplitString3(f.readFromExcel("src/test/resources/PreturiProduse.xls", "NOTEBOOK", i, 1)));
		//System.out.println(pret[i]);
		}
		
		
		while (b!=true)
		{
			if (h.isElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/a[2]/strong"))==true)
			{	
				//procesez 
				cod_site = d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+i+"]/td["+j+"]/a[2]/strong")).getText();
				logger.Log(LOG_FILE, "TestPretNotebook(): Verify product:"+cod_site);
				for (int ii=0; ii<c; ii++)
				{
					n=5;
					if (cod_site.contains(cod[ii]))
					{
						n=n+i;
						if (h.isElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+n+"]/td["+j+"]/span[2]/strong"))==true)
							pret_site = d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+n+"]/td["+j+"]/span[2]/strong")).getText();
						else 
							pret_site = d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr["+n+"]/td["+j+"]/span/strong")).getText();
					int amount = h.ConvertAmountToInt(pret_site);
					if ((pret[ii]==amount)==false)
					{
						logger.Log(LOG_FILE, "TestPretNotebook(): "+cod_site+" "+amount+" "+"did not match DATABASE -- TEST FAILLED !!!");
						h.screenShooter("TestPretNotebook", d);
					}
					else
						logger.Log(LOG_FILE, "TestPretNotebook(): "+cod_site+" "+amount+" "+"MATCHED DATABASE");
					assertTrue(pret[ii]==amount);
					break;
					}
					//System.out.println(ii+1);
					if (ii+1==c)
						logger.Log(LOG_FILE, "TestPretNotebook(): Product "+cod_site+" does not appear in DATABASE !!");	
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
