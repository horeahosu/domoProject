package com.domo.selenium.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverHelper {

	private WebDriver driver;
	private String mainWindow;

	public WebDriverHelper(WebDriver d) {
		if (d == null) {
			throw new IllegalArgumentException("Driver is NULL");
		}
		driver = d;
	}
	
	public void screenShooter(String errorLocation, WebDriver driver){
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			String filename = "src/test/resources/screenshots/"+"test_"+errorLocation+"_failed.png";
			FileUtils.copyFile(scrFile, new File(filename));
		} catch (IOException e) {System.out.println("Couldn't take the screenshot ");}	
	}
	
	public int ConvertAmountToInt(String s)
	{
		String amount;
		
		String[] first_part = s.split(",");
		String part1 = first_part[0]; 
		if (part1.contains("."))
		{
		String[] second_part = part1.split("\\.");
		String part2 = second_part[0];
		String part3 = second_part[1];
		amount =part2+part3;
		}
		else
		{
			amount = part1;
		}
		int n = Integer.parseInt(amount);
		
		return n;
	}
	
	public String SplitString1(String s)
	{
		String[] parts = s.split(","); 
		String part1 = parts[0];
		//String part2 = parts[1];
		
		return part1;
	}
	
	public String SplitString2(String s)
	{
		String[] parts = s.split(" "); 
		//String part1 = parts[0];
		String part1 = parts[0];
		
		return part1;
	}
	
	public String SplitString3(String s)
	{
		String[] parts = s.split("\\."); 
		//String part1 = parts[0];
		String part1 = parts[0];
		
		return part1;
	}

	/**
	 * Call this method before using the driver into another window
	 */
	public void storeMainWindow() {
		mainWindow = driver.getWindowHandle();
	}

	/**
	 * You must call storeMainWindow() before calling this method
	 */
	public void switchBackToMainWindow() {
		if (mainWindow == null) {
			throw new IllegalStateException(
					"storeMainWindow() was not called!!!");
		}
		driver.switchTo().window(mainWindow);
	}

	/**
	 * Returns true if element is present on the page or false otherwise
	 * 
	 * @param by
	 * @return
	 */
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Waits until an element will be displayed on the page
	 * 
	 * @param by
	 * @param maximumSeconds
	 */
	public void waitForElementPresent(final By by, final int maximumSeconds) {
		(new WebDriverWait(driver, maximumSeconds))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver error1) {
						WebElement error = driver.findElement(by);
						return error;
					}
				});
	}

	/**
	 * Waits until a text will be displayed on the page
	 * 
	 * @param by
	 * @param maximumSeconds
	 */
	public void waitForTextPresent(final String text, final int maximumSeconds) {
		(new WebDriverWait(driver, maximumSeconds))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver error1) {
						WebElement error = driver.findElement(By
								.tagName("body"));
						return error.getText().contains(text);
					}
				});
	}

	/**
	 * Waits until a WebElement will be displayed on the page. Used first for
	 * the help tests methods. When, not recurrent, the text for a tag will be
	 * null, use this method. The method waits till the element is displayed.
	 * 
	 * @param by
	 * @param maximumSeconds
	 */
	public void waitForElementDisplayed(final By by, final int maximumSeconds) {
		(new WebDriverWait(driver, maximumSeconds))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver error1) {
						return driver.findElement(by).isDisplayed();
					}
				});
	}

	/**
	 * Checks for presence of the text in a html page
	 * 
	 * @param text
	 * @return
	 */
	public boolean checkForTextPresentOnThePage(String text) {
		WebElement body = driver.findElement(By.tagName("body"));
		return body.getText().contains(text);
	}

	/**
	 * Checks if a text is displayed in the drop-down. This method considers the
	 * actual display text <br/>
	 * For example if we have the following situation: &lt;select id="test"&gt;
	 * &lt;option value="4"&gt;June&lt;/option&gt; &lt;/select&gt; we will call
	 * the method as follows: isValuePresentInDropDown(By.id("test"), "June");
	 * 
	 * @param dropDown
	 * @param text
	 * @return
	 */
	public boolean isTextPresentInDropDown(By dropDown, String text) {
		WebElement element = driver.findElement(dropDown);
		List<WebElement> options = element.findElements(By
				.xpath(".//option[normalize-space(.) = " + escapeQuotes(text)
						+ "]"));
		return options != null && !options.isEmpty();
	}

	/**
	 * Checks if the VALUE is present in the drop-down. This considers the VALUE
	 * attribut of the option, not the actual display text <br/>
	 * For example if we have the following situation: &lt;select id="test"&gt;
	 * &lt;option value="4"&gt;June&lt;/option&gt; &lt;/select&gt; we will call
	 * the method as follows: isValuePresentInDropDown(By.id("test"), "4");
	 * 
	 * @param dropDown
	 * @param value
	 * @return
	 */
	public boolean isValuePresentInDropDown(By dropDown, String value) {
		WebElement element = driver.findElement(dropDown);

		StringBuilder builder = new StringBuilder(".//option[@value = ");
		builder.append(escapeQuotes(value));
		builder.append("]");
		List<WebElement> options = element.findElements(By.xpath(builder
				.toString()));

		return options != null && !options.isEmpty();
	}

	/**
	 * Select a value from a drop down list
	 * 
	 * @param dropDown
	 * @param value
	 */

	public void selectOptionFromDropdownByValue(By dropDown, String value) {
		Select select = new Select(driver.findElement(dropDown));
		select.selectByValue(value);
	}

	/**
	 * Select text from a drop down list
	 * 
	 * @param dropDown
	 * @param displayText
	 */

	public void selectOptionFromDropdownByDisplayText(By dropDown,
			String displayText) {
		Select select = new Select(driver.findElement(dropDown));
		select.selectByVisibleText(displayText);
	}

	/**
	 * Checks if a text is selected in a drop down list
	 * 
	 * @param dropDown
	 * @param displayText
	 * @return
	 */

	public boolean isTextSelectedInDropDown(By dropDown, String displayText) {
		WebElement element = driver.findElement(dropDown);
		List<WebElement> options = element.findElements(By
				.xpath(".//option[normalize-space(.) = "
						+ escapeQuotes(displayText) + "]"));

		for (WebElement opt : options) {
			if (opt.isSelected()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns the default selected value from a drop down list
	 * 
	 * @param dropDown
	 * @return
	 */
	public String getSelectedValue(By dropDown) {

		Select select = new Select(driver.findElement(dropDown));
		String defaultSelectedValue = select.getFirstSelectedOption().getText();
		return defaultSelectedValue;

	}

	/**
	 * Checks if a value is selected in a drop down list
	 * 
	 * @param dropDown
	 * @param value
	 * @return
	 */

	public boolean isValueSelectedInDropDown(By dropDown, String value) {
		WebElement element = driver.findElement(dropDown);

		StringBuilder builder = new StringBuilder(".//option[@value = ");
		builder.append(escapeQuotes(value));
		builder.append("]");
		List<WebElement> options = element.findElements(By.xpath(builder
				.toString()));

		for (WebElement opt : options) {
			if (opt.isSelected()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Deselect all values from a drop down list
	 * 
	 * @param dropdown
	 */

	public void deselectAllDropDownOptions(By dropdown) {
		new Select(driver.findElement(dropdown)).deselectAll();
	}

	/**
	 * Select a radio button
	 * 
	 * @param radioButtonName
	 * @param value
	 */

	public void selectRadioButtonByValue(String radioButtonName, String value) {
		List<WebElement> month1 = driver.findElements(By.name(radioButtonName));
		for (WebElement rMonth : month1) {
			if (rMonth.getAttribute("value").equalsIgnoreCase(value)) {
				rMonth.click();
				break;
			}
		}
	}

	/**
	 * Checks if a radio button has the values selected
	 * 
	 * @param radioButtonName
	 * @param value
	 * @return
	 */

	public boolean isRadioButtonValueSelected(String radioButtonName,
			String value) {
		List<WebElement> month1 = driver.findElements(By.name(radioButtonName));
		for (WebElement rMonth : month1) {
			if (rMonth.getAttribute("value").equalsIgnoreCase(value)
					&& rMonth.isSelected()) {
				return true;

			}
		}

		return true;
	}

	/**
	 * Used to switch to a window by title
	 * 
	 * @param title
	 */
	public void selectWindowByTitle(String title) {
		String currentWindow = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		if (!handles.isEmpty()) {
			for (String windowId : handles) {
				if (!driver.switchTo().window(windowId).getTitle()
						.equals(title)) {
					driver.switchTo().window(currentWindow);
				}
			}
		}
	}

	/**
	 * Returns a WebDriver object that has the proxy server set
	 * 
	 * @param proxyURL
	 * @param port
	 * @param driver
	 * @return
	 */
	public static WebDriver getDriverWithProxy(String proxyURL, String port,
			WebDriver driver) {
		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		proxy.setHttpProxy(proxyURL + ":" + port)
				.setFtpProxy(proxyURL + ":" + port)
				.setSslProxy(proxyURL + ":" + port);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.PROXY, proxy);

		try {
			Constructor<?> c = driver.getClass().getConstructor(
					Capabilities.class);
			return (WebDriver) c.newInstance(cap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	/**
	 * Gets a profile with a specific user agent
	 * 
	 * @param userAgent
	 * @return
	 */

	public static FirefoxDriver getDriverWithUserAgent(String userAgent) {

		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("general.useragent.override", userAgent);
		profile.setAcceptUntrustedCertificates(true);
		profile.setAssumeUntrustedCertificateIssuer(false);
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(FirefoxDriver.PROFILE, profile);
		// cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		FirefoxDriver driver = new FirefoxDriver(cap);
		return driver;

	}

	/**
	 * Returns a new WebDriver instance and loads an existing profile from the
	 * disk. You must pass the path to the profile. Example:
	 * "C:/Users/milie/AppData/Roaming/Mozilla/Firefox/Profiles/rti3ufvd.default"
	 * 
	 * @param pathToProfile
	 * @return
	 */
	public static WebDriver getDriverWithExistingProfile(String pathToProfile) {

		FirefoxProfile profile = new FirefoxProfile(new File(pathToProfile));

		return new FirefoxDriver(profile);
	}

	/**
	 * Gets a profile with a specific user agent with or without javascript
	 * enabled
	 * 
	 * @param userAgent
	 *            - the User Agent String
	 * @param javascriptEnabled
	 *            - true if JS will be enabled; false if JS will be disabled
	 * @return
	 */
	public static WebDriver getDriverWithUserAgentAndJavascriptOnOrOff(
			String userAgent, boolean javascriptEnabled) {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("general.useragent.override", userAgent);
		profile.setPreference("javascript.enabled", javascriptEnabled);
		WebDriver driver = new FirefoxDriver(profile);
		return driver;

	}

	/**
	 * 
	 * @return
	 */
	public static WebDriver getDriverWithDifferentSettings() {
		return null;
	}

	public String escapeQuotes(String toEscape) {
		if (toEscape.indexOf("\"") > -1 && toEscape.indexOf("'") > -1) {
			boolean quoteIsLast = false;
			if (toEscape.indexOf("\"") == toEscape.length() - 1) {
				quoteIsLast = true;
			}
			String[] substrings = toEscape.split("\"");

			StringBuilder quoted = new StringBuilder("concat(");
			for (int i = 0; i < substrings.length; i++) {
				quoted.append("\"").append(substrings[i]).append("\"");
				quoted.append(((i == substrings.length - 1) ? (quoteIsLast ? ", '\"')"
						: ")")
						: ", '\"', "));
			}
			return quoted.toString();
		}

		if (toEscape.indexOf("\"") > -1) {
			return String.format("'%s'", toEscape);
		}

		return String.format("\"%s\"", toEscape);
	}

	@SuppressWarnings("deprecation")
	private static WebDriver getNewDriver(Builder builder) {
		ProfilesIni allProfiles = new ProfilesIni();
		FirefoxProfile profile = allProfiles.getProfile("default");

		profile.setAcceptUntrustedCertificates(builder.acceptAllCertificates);
		profile.setPreference("javascript.enabled", builder.jsEnabled);

		if (builder.userAgent != null) {
			profile.setPreference("general.useragent.override",
					builder.userAgent);
		}
		if (builder.proxyHost != null) {
			org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
			proxy.setHttpProxy(builder.proxyHost + ":" + builder.proxyPort)
					.setFtpProxy(builder.proxyHost + ":" + builder.proxyPort)
					.setSslProxy(builder.proxyHost + ":" + builder.proxyPort);
			profile.setProxyPreferences(proxy);
		} else {
			profile.setPreference("network.proxy.type", 0);
		}

		return new FirefoxDriver(profile);
	}

	public static class Builder {
		private String userAgent;
		private boolean jsEnabled;
		private String proxyHost;
		private String proxyPort;
		private boolean acceptAllCertificates;

		public Builder proxy(String proxyHost, String proxyPort) {
			this.proxyHost = proxyHost;
			this.proxyPort = proxyPort;
			return this;
		}

		public Builder userAgent(String ua) {
			this.userAgent = ua;
			return this;
		}

		public Builder jsEnabled(boolean jsEnabled) {
			this.jsEnabled = jsEnabled;
			return this;
		}

		public Builder acceptAllCerts(boolean acceptAllCerts) {
			this.acceptAllCertificates = acceptAllCerts;
			return this;
		}

		public WebDriver build() {
			return getNewDriver(this);
		}
	}

}
