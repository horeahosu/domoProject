package com.domo.selenium.CosCumparaturi;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.domo.selenium.util.Constants;
import com.domo.selenium.util.WebDriverHelper;

public class incercare {
	
	FirefoxDriver d = WebDriverHelper.getDriverWithUserAgent(Constants.USER_AGENT);
	WebDriverHelper h = new WebDriverHelper(d);
	
	
	@Before
	public void doSetup()
	{	
		d.manage().deleteAllCookies();
		d.get(Constants.SITE);
	}
	
	@Test
	public void TestScenariul1() 
	{
		
		h.waitForElementPresent(By.className("logo"), 5);
		
		d.findElement(By.linkText("Computere")).click();
		//d.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td/div/ul/li[2]/a")).click();
		
		h.waitForElementPresent(By.linkText("Notebook - Laptop"), 5);
		d.findElement(By.linkText("Notebook - Laptop")).click();
		String s=d.findElement(By.id("celula_6_28259")).getText();
		String s2=h.SplitString2(s);
		System.out.println(s2);
	}
	
	@After
	public void quitDriver()
	{
		d.quit();
	}

}
