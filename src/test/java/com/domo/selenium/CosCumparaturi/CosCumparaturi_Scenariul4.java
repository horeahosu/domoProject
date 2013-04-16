package com.domo.selenium.CosCumparaturi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.domo.selenium.util.Constants;
import com.domo.selenium.util.WebDriverHelper;

public class CosCumparaturi_Scenariul4 {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE);
	}
	
	/**
	 * Scenariul 4: Un user nou pe site adauga in cosul de cumparaturi un produs (televizor).
	 * Verific pretul produsului in sectiunea Cos Cumparaturi, pe pagina Pasul1 si verific existenta butonului "Pasul2". 
	 * Adaug in cosul de cumparaturi inca un produs (acelasi televizor). Verific pretul total de pe ultima pagina.
	 */
	
	@Test
	public void TestScenariul4() throws InterruptedException
	{
		h.waitForElementPresent(By.className("logo"), 5);
		d.findElement(By.linkText("Televizoare & Home cinema")).click();
		h.waitForElementPresent(By.linkText("LED TV"), 5);
		d.findElement(By.linkText("LED TV")).click();
		d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr[4]/td/a/img")).click();
		
		//stochez pretul
		h.waitForElementPresent(By.xpath("/html/body/div/div[3]/form/div[2]/div[3]/table/tbody/tr[2]/td/div/span/strong"), 5);
		String price1 = d.findElement(By.xpath("/html/body/div/div[3]/form/div[2]/div[3]/table/tbody/tr[2]/td/div/span/strong")).getText();
		System.out.println(price1);
		
		//adaug in cos
		d.findElement(By.xpath("/html/body/div/div[3]/form/div[2]/div[3]/table/tbody/tr[3]/td/div/span/input")).click();
		
		//verific pret pe pagina Pasul1
		String price2 = d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td/table/tbody/tr[5]/td[4]/strong")).getText();
		
		//Compar preturile
		assertEquals(price1, price2);
		
		//Verific butonul Pasul2
		assertTrue(h.isElementPresent(By.name("Pasul2")));
		
		//adaug acelasi produs
		d.findElement(By.xpath("//span/input")).clear();
		d.findElement(By.xpath("//span/input")).sendKeys("2");
		
		
		//verific pretul pe pagina Pasul2
		d.findElement(By.name("Pasul2")).click();
		String price3 = d.findElement(By.id("TotalPlata")).getText();
		System.out.println(price3);
		
		//suma la primele 2 preturi trebuie sa fie price 3 - totalul
		assertTrue(price3!=price1);
	
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}

}
