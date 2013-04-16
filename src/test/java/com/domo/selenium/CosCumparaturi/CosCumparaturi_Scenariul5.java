package com.domo.selenium.CosCumparaturi;

import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.domo.selenium.util.Constants;
import com.domo.selenium.util.WebDriverHelper;

public class CosCumparaturi_Scenariul5 {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE);
	}
	
	/**
	 * Scenariul 5: Un user nou pe site adauga in cosul de cumparaturi un produs (televizor). 
	 * Adaug in cosul de cumparaturi inca un produs (alt televizor). Verific pretul total de pe ultima pagina.
	 * Verific pretul celui de al doilea produsului in sectiunea Cos Cumparaturi, pe pagina Pasul1 si verific existenta
	 * butonului "Pasul2". Verific de asemenea Pretul total si apoi sterg ambele produse si verific mesajul:
	 * "Cosul Dvs. nu contine nici un produs"
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
		
		//go to prevoius page (2)
		d.navigate().back();
		d.navigate().back();
		
		//cumpar televizor 2
		h.waitForElementPresent(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr[4]/td[2]/a/img"), 5);
		d.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/form/span[2]/table/tbody/tr/td/table/tbody/tr[4]/td[2]/a/img")).click();
		d.findElement(By.xpath("/html/body/div/div[2]/form/div[2]/div[3]/table/tbody/tr[3]/td/div/span/input")).click();
		

		
		//Verific butonul Pasul2 si stochez pret pasul 1
		assertTrue(h.isElementPresent(By.name("Pasul2")));
		String price2 = d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td/table/tbody/tr[7]/td[4]/strong")).getText();
		System.out.println(price2);
		
		//price 1 si price2 (total) sunt diferite	
		assertTrue(price1!=price2);
		
		//sterg produsele
		d.findElements(By.linkText("Sterge")).get(1).click();
		d.findElement(By.linkText("Sterge")).click();
		
		//verific mesajul: 
		Thread.sleep(2000);
		assertTrue(h.checkForTextPresentOnThePage("Cosul Dvs. nu contine nici un produs"));
		
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}

}
