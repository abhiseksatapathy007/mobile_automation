package com.mobileautomation.Helper;

import static org.testng.Assert.assertTrue;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.function.Function;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class Wrapperdriver {

	public AppiumDriver Wrapperdriver;
	static Properties prop = new Properties();
	public static int DefaultTimeOut = 25;
	boolean flag = true;
	WebDriverWait wait1;

	public Wrapperdriver(WebDriver driver) {
		this.Wrapperdriver = (AppiumDriver) driver;
	}

	@SuppressWarnings("rawtypes")
	public TouchAction getTouchAction() {
		return new TouchAction((PerformsTouchActions) Wrapperdriver);
	}

	public String getTimeStamp() {
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		return timestamp;
	}

	public String getScreenshot(WebDriver webdriver) {
		TakesScreenshot newScreen = (TakesScreenshot) webdriver;
		String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
		return "data:image/jpg;base64, " + scnShot;
	}

	public WebElement waitForElementToBeVisible(By byElement, long waitTime) {
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(waitTime));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(byElement));
		return element;
	}

	public void waitForElementToBeClickable(By byElement, long waitTime) {
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(waitTime));
		wait.until(ExpectedConditions.elementToBeClickable(byElement));
	}

	public void clickOnWebElement(By by) {
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		wait.until(ExpectedConditions.elementToBeClickable(by));
		Wrapperdriver.findElement(by).click();
	}

	public void clickOnWebElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	}

	public void InterceptclickOnWebElement(By by) {
		WebElement element = Wrapperdriver.findElement(by);
		getTouchAction().tap(TapOptions.tapOptions().withElement(ElementOption.element(element))).perform();
	}

	public void HardclickOnWebElement(By by) {
		// For mobile, use tap instead of ENTER key
		WebElement element = Wrapperdriver.findElement(by);
		getTouchAction().tap(TapOptions.tapOptions().withElement(ElementOption.element(element))).perform();
	}

	public void clickonInvisibleElement(String xpathkey) {
		WebElement element = Wrapperdriver.findElement(By.xpath(xpathkey));
		// Use tap for mobile instead of JavaScript click
		getTouchAction().tap(TapOptions.tapOptions().withElement(ElementOption.element(element))).perform();
	}

	public void jsClick(By by) {
		WebElement element = Wrapperdriver.findElement(by);
		// Use tap for mobile instead of JavaScript click
		getTouchAction().tap(TapOptions.tapOptions().withElement(ElementOption.element(element))).perform();
	}
	
	public void removeAttribute(By by, String attribute) {
		WebElement element = Wrapperdriver.findElement(by);
		// For mobile, JavaScript execution may not work the same way
		// This method is kept for compatibility but may need platform-specific implementation
		try {
			org.openqa.selenium.JavascriptExecutor executor = (org.openqa.selenium.JavascriptExecutor) Wrapperdriver;
			executor.executeScript("arguments[0].removeAttribute('"+attribute+"');", element);
		} catch (Exception e) {
			System.out.println("Could not remove attribute: " + e.getMessage());
		}
	}

	public int getrowcount(By by) {
		@SuppressWarnings("unchecked")
		List<WebElement> row = (List<WebElement>) Wrapperdriver.findElement(by);
		int rowcount = row.size();
		return rowcount;
	}

	public List<String> getAlldropdownOptions(By by) {
		List<String> options = new ArrayList<String>();
		for (WebElement option : new Select(Wrapperdriver.findElement(by)).getOptions()) {
			if (option.getAttribute("value") != "")
				options.add(option.getText());
		}
		return options;
	}

	public boolean isOptionListedinDropDown(By by, String option) {
		try {

			Select selection = new Select(Wrapperdriver.findElement(by));
			selection.selectByVisibleText(option);

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void sendKeysToWebelement(By by, String string) {
		Wrapperdriver.findElement(by).clear();
		Wrapperdriver.findElement(by).sendKeys(string);
	}

	public void sendKeyChordToWebelement(By by, String string) {
		Wrapperdriver.findElement(by).sendKeys(Keys.chord(string));
	}

	public void sendCharAsKeys(By by, String string) throws Exception {
		WebElement element = Wrapperdriver.findElement(by);
		for (char c : string.toCharArray()) {
			element.sendKeys(Character.toString(c));
			Thread.sleep(1000);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void sendKeysToWebelement(By by, org.openqa.selenium.Keys keys) {
		Wrapperdriver.findElement(by).clear();
		Wrapperdriver.findElement(by).sendKeys(keys);
	}

	public void sendKeysToWebelement(String xpathkey, String string) {
		WebElement e = Wrapperdriver.findElement(By.xpath(xpathkey));
		e.clear();
		e.sendKeys(string);
	}

	public void clearElement(By by) {
	    WebElement toClear = Wrapperdriver.findElement(by);
	    
	    // Tap to focus (if needed)
	    getTouchAction().tap(TapOptions.tapOptions().withElement(ElementOption.element(toClear))).perform();
	    
	    // For mobile, use clear() method or long press to select all
	    toClear.clear();
	    
	    // Alternative: Long press and select all for mobile
	    getTouchAction().longPress(LongPressOptions.longPressOptions()
	    	.withElement(ElementOption.element(toClear))
	    	.withDuration(Duration.ofMillis(1000))).perform();
	}


	public void sendHardKeysToWebelement(By by, String string) {
		Wrapperdriver.findElement(by).sendKeys(string);

	}

	public void inputValues(By by, String string) {
		WebElement toClear = Wrapperdriver.findElement(by);
		// For mobile, use clear() method
		toClear.clear();
		toClear.sendKeys(string);
	}

	public String alltablerowcount(By by, By by1) {
		String tablecount = null;
		try {
			int rowCount = Wrapperdriver.findElements(by).size();

			while (webElementIsEnabled(by1)) {
				Wrapperdriver.findElement(by1).click();
				Thread.sleep(300);
				rowCount += Wrapperdriver.findElements(by).size();
			}
			tablecount = String.valueOf(rowCount);

		} catch (Exception ex) {
			// System.out.println("All Notes tab grid not visible");
		}
		return tablecount;
	}

	public boolean assertLinkNotPresent(By by) {
		List<WebElement> bob = Wrapperdriver.findElements(by);
		if (bob.isEmpty() == true) {
			return true;
		} else
			return false;
	}

	public boolean webElementIsDisplayed(By by) {
		// Wrapperdriver.waitForAjaxRefresh();

		try {
			if (Wrapperdriver.findElement(by).isDisplayed())
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean webElementIsEnabled(By by) {
		// Wrapperdriver.waitForElementToBeVisible(by, 30, true);

		if (Wrapperdriver.findElement(by).isEnabled())
			return true;
		else
			return false;
	}

	public boolean isElementEditable(By by) {
		WebElement e = Wrapperdriver.findElement(by);
		return e.isDisplayed() && e.isEnabled();
	}

	public String getCSSValueOfWebElement(By by, String cssKey) {
		WebElement e = Wrapperdriver.findElement(by);
		return e.getCssValue(cssKey);
	}

	public String getAttributeOfWebElement(By by, String attribute) {
		return Wrapperdriver.findElement(by).getAttribute(attribute);
	}

	public boolean webElementIsReadOnly(By by) {
		String readonly = getAttributeOfWebElement(by, "readonly");
		if (readonly.equals("true"))
			return true;
		else
			return false;

	}

	public boolean webElementIsRequired(By by) {
		String required = getAttributeOfWebElement(by, "required");
		if (required.equals("true"))
			return true;
		else
			return false;

	}

	public String getTextOfWebElement(By by) {
		return Wrapperdriver.findElement(by).getText();
	}

	public List<WebElement> getListOfWebElement(By by) {
		List<WebElement> elementList = Wrapperdriver.findElements(by);
		if (elementList.size() > 0) {
			return elementList;
		} else {
			System.out.println("No data found");
			return null;
		}
//    	return Wrapperdriver.findElement(by).getSize();
	}

	public boolean isCheckBoxChecked(By by) {
		return Wrapperdriver.findElement(by).isSelected();
	}

	public boolean isCheckBoxChecked(WebElement element) {
		return element.isSelected();
	}

	public void selectDropDownValueByText(By selectBy, String visibleText) throws Exception {
		Select dropDown = new Select(Wrapperdriver.findElement(selectBy));
		dropDown.selectByVisibleText(visibleText);
	}

	public void selectDropDownValueByText(String xpath, String visibleText) throws Exception {
		Select dropDown = new Select(Wrapperdriver.findElement(By.xpath(xpath)));
		dropDown.selectByVisibleText(visibleText);
	}

	public String getFirstSelectedOption(By selectBy) throws Exception {
		Select dropDown = new Select(Wrapperdriver.findElement(selectBy));
		WebElement o = dropDown.getFirstSelectedOption();
		String selectedoption = o.getText();
		System.out.println("Selected element: " + selectedoption);
		return selectedoption;
	}

	public void selectDropDownValueByValue(By selectBy, String value) {
		Select dropDown = new Select(Wrapperdriver.findElement(selectBy));
		dropDown.selectByValue(value);
	}

	public String selectDropDownValueById(By selectBy, int id) {
		Select dropDown = new Select(Wrapperdriver.findElement(selectBy));
		dropDown.selectByIndex(id);
		return null;
	}

	public int getElementCount(By by) {
		List<WebElement> ele = Wrapperdriver.findElements(by);
		return ele.size();
	}

	public void hoverOnWebElement(By toElement) {
		// For mobile, use long press instead of hover
		WebElement element = Wrapperdriver.findElement(toElement);
		getTouchAction().longPress(LongPressOptions.longPressOptions()
			.withElement(ElementOption.element(element))
			.withDuration(Duration.ofMillis(500))).release().perform();
	}

	public void switchToFrame(By by) {
		Wrapperdriver.switchTo().frame(Wrapperdriver.findElement(by));
	}

	public void switchToDefaultContent() {
		Wrapperdriver.switchTo().defaultContent();
	}

	public void closeDriver() {
		Wrapperdriver.quit();
	}

	public WebElement returnwebelement(By by) throws Exception {
		System.out.println("Inside find webelement function with " + by);

		Thread.sleep(10000);
		WebElement e = null;

		try {

			WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
			e = wait.until(ExpectedConditions.elementToBeClickable(by));
			return e;
		} catch (Exception e1) {
			System.out.println("Error is " + e1.fillInStackTrace());
		}
		return null;
	}

	public void loadurl(String url) {
		System.out.println("Loading url " + url);
		Wrapperdriver.get(url);

	}

	public void scrolltoelement(By by) {
		// For mobile, use swipe to scroll to element
		WebElement e = Wrapperdriver.findElement(by);
		int elementY = e.getLocation().getY();
		int screenHeight = Wrapperdriver.manage().window().getSize().getHeight();
		
		// Swipe down if element is below, swipe up if element is above
		if (elementY > screenHeight) {
			swipeDown();
		} else {
			swipeUp();
		}
	}

	public void scrolltoelement(WebElement element) {
		// For mobile, use swipe to scroll to element
		int elementY = element.getLocation().getY();
		int screenHeight = Wrapperdriver.manage().window().getSize().getHeight();
		
		if (elementY > screenHeight) {
			swipeDown();
		} else {
			swipeUp();
		}
	}

	public void scrolltoelement(String xpath) {
		// For mobile, use swipe to scroll to element
		WebElement e = Wrapperdriver.findElement(By.xpath(xpath));
		int elementY = e.getLocation().getY();
		int screenHeight = Wrapperdriver.manage().window().getSize().getHeight();
		
		if (elementY > screenHeight) {
			swipeDown();
		} else {
			swipeUp();
		}
	}

	public void settemplatefile() throws Exception {
		// For mobile, paste functionality is handled differently
		// This method is kept for compatibility but may need platform-specific implementation
		System.out.println("Inside set template file function - Mobile paste not implemented");
	}

	public boolean iselementexist(String by) {
		int size = Wrapperdriver.findElements(By.xpath(by)).size();
		if (size == 0)
			return false;
		else
			return true;
	}

	public String getname(String xpathkey) {
		String name = Wrapperdriver.findElement(By.xpath(xpathkey)).getText();
		System.out.println("Printing name " + name);
		return name;
	}

	public boolean iselementdisplayed(String by) {

		WebElement e = Wrapperdriver.findElement(By.xpath(by));

		if (e.isDisplayed()) {
			return true;
		} else
			return false;

	}

	public boolean elementdisplayed(By by) {

		WebElement e = Wrapperdriver.findElement(by);

		if (e.isDisplayed()) {
			return true;
		} else
			return false;

	}

	public void clickOnWebElement(String xpathkey) {

		try {
			WebElement e = null;
			WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
			e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathkey)));
			e.click();
		} catch (Exception e) {
			System.out.println("Failed to click on element " + e.fillInStackTrace());
		}
	}

	public void selectDatafromCombobox(By by, String value) throws Exception {
		Wrapperdriver.findElement(by).clear();
		Thread.sleep(2000);
		Wrapperdriver.findElement(by).sendKeys(value);
		try {
			WebElement e = null;
			System.out.println("Inside click on webelement function with " + value);
			WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(30));
			e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@text,'" + value + "') or contains(@name,'" + value + "')]")));
			getTouchAction().tap(TapOptions.tapOptions().withElement(ElementOption.element(e))).perform();
		} catch (Exception e) {
			System.out.println("Failed to click on element " + e.fillInStackTrace());
		}
	}

	public void selectData(By by, String value) throws Exception {
		Wrapperdriver.findElement(by).click();
		Thread.sleep(2000);
		Wrapperdriver.findElement(by).sendKeys(value);
		// For mobile, tap outside or use back button instead of ENTER
		Thread.sleep(1000);
	}

	public void checkDatafromCombobox(By by, String value) throws Exception {
		Wrapperdriver.findElement(by).sendKeys(value);
		Thread.sleep(1000);
		// For mobile, use tap on dropdown option instead of keyboard
		WebElement option = Wrapperdriver.findElement(By.xpath("//*[contains(@text,'" + value + "') or contains(@name,'" + value + "')]"));
		getTouchAction().tap(TapOptions.tapOptions().withElement(ElementOption.element(option))).perform();
	}

	public void selectDatafromCombobox(By by, String value, String index) throws Exception {
		Wrapperdriver.findElement(by).clear();
		Thread.sleep(2000);
		Wrapperdriver.findElement(by).sendKeys(value);
		try {
			WebElement e = null;
			System.out.println("Inside click on webelement function with " + value);
			WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
			e = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("(//*[contains(text(),'" + value + "')])[" + index + "]")));
			e.click();
		} catch (Exception e) {
			System.out.println("Failed to click on element " + e.fillInStackTrace());
		}
	}

	public boolean iselementclickable(By by) {

		try {
			WebElement e = null;
			System.out.println("Inside is element clickable function ");
			e = Wrapperdriver.findElement(by);
			// e.click();
			System.out.println("Function returning true ********************* ");
			return true;
		} catch (Exception e1) {
			System.out.println("Element not clickable " + e1.fillInStackTrace());
		}
		System.out.println("Function returning false    ********************  ");
		return false;
	}

	public Long getElementTextLength(By by) {
		WebElement e = null;
		e = Wrapperdriver.findElement(by);
		JavascriptExecutor jse = (JavascriptExecutor) Wrapperdriver;
		return (Long) jse.executeScript("return arguments[0].value.length;", e);
	}

	public String getElementTextWithJS(By by) {
		WebElement e = null;
		e = Wrapperdriver.findElement(by);
		JavascriptExecutor jse = (JavascriptExecutor) Wrapperdriver;
		return (String) jse.executeScript("return arguments[0].value;", e);
	}

	public void scrollElementDown(By by) throws InterruptedException {
		WebElement e = Wrapperdriver.findElement(by);
		System.out.println("Inside scroll element down function");
		// For mobile, swipe within the element
		int startX = e.getLocation().getX() + e.getSize().getWidth() / 2;
		int startY = e.getLocation().getY() + e.getSize().getHeight() - 50;
		int endY = e.getLocation().getY() + 50;
		
		getTouchAction().press(PointOption.point(startX, startY))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
			.moveTo(PointOption.point(startX, endY))
			.release().perform();
	}

	public void scrolldown() throws InterruptedException {
		System.out.println("Inside scroll down function ");
		swipeDown();
	}

	public void scrollup() {
		System.out.println("Inside scroll up function ");
		swipeUp();
	}

	public void scrollleft() throws Exception {
		System.out.println("Inside scroll left function ");
		swipeLeft();
	}

	public void scrollright() throws Exception {
		System.out.println("Inside scroll right function ");
		swipeRight();
	}

	public void makingelementclickable(By by) throws Exception {

		System.out.println("Inside makingelementclickable function ");

		while (!iselementclickable(by)) {
			System.out.println("Inside while loop *************************** ");
			System.out.println("Scrolling down by swiping ");
			swipeDown();
		}
		System.out.println("Exiting while loop ");
	}

	public void makingelementclickable(String xpathkey) throws Exception {

		System.out.println("Inside makingelementclickable function ");
		int i = 0;
		while (!iselementclickable(xpathkey)) {
			System.out.println("Inside while loop *************************** ");
			System.out.println("Scrolling down by swiping ");
			swipeDown();
			i++;
			System.out.println("In click loop " + i);
			if (i > 5) {
				System.out.println("Breaking loop ,element not found to click ");
				break;
			}

		}
		System.out.println("Exiting while loop ");
	}

	public boolean iselementclickable(String xpathkey) {

		try {
			WebElement e = null;
			System.out.println("Inside is element clickable function ");

			// e= Wrapperdriver.findElement(By.xpath(xpathkey));
			e = returnwebelement(xpathkey);
			e.click();
			System.out.println("Function returning true ********************* ");
			return true;
		} catch (Exception e1) {
			System.out.println("Element not clickable " + e1.fillInStackTrace());
		}
		System.out.println("Function returning false    *********************  ");
		return false;
	}

	public WebElement returnwebelement(String xpathkey) {
		System.out.println("Inside find webelement function with ***********" + xpathkey);

		WebElement e = null;

		try {

			WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
			e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathkey)));

			return e;
		} catch (Exception e1) {
			System.out.println("Error is " + e1.fillInStackTrace());
		}
		return null;
	}

	public WebElement returnwebelementusingtext(String text) {
		System.out.println("Inside find webelement function with ***********" + text);

		WebElement e = null;

		try {

			WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
			e = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(text)));
			return e;
		} catch (Exception e1) {
			System.out.println("Error is " + e1.fillInStackTrace());
		}
		return null;
	}

	public void OpenLinkinNewTab(By by) {
		// For mobile, long press to open context menu
		WebElement e = Wrapperdriver.findElement(by);
		getTouchAction().longPress(LongPressOptions.longPressOptions()
			.withElement(ElementOption.element(e))
			.withDuration(Duration.ofMillis(1000))).perform();
	}

	public String getHref(By by) {
		WebElement e = null;
		String s = e.getAttribute("href");
		System.out.println(s);
		return s;
	}

	public void waitForLoad(WebDriver driver) {
		// For mobile apps, wait for element presence instead of document ready state
		try {
			Thread.sleep(2000); // Wait for app to load
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void waitForElementToBePresent(By byElement, long waitTime) {
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(waitTime));
		wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
	}

	public void waitForElementToDisappear(By byElement, long waitTime) {
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofMillis(waitTime));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(byElement));
	}

	public void isElementPresent(By byElement, long waitTime) {
		long endTime = System.currentTimeMillis() + waitTime;
		while (System.currentTimeMillis() < endTime) {
			try {
				WebElement element = Wrapperdriver.findElement(byElement);
				if (element.isDisplayed()) {
					return;
				}
			} catch (Exception e) {
				// Element not found or not visible yet
			}
			try {
				Thread.sleep(100); // Polling interval
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void isElementToDisappear(By byElement, long waitTime) {
		long endTime = System.currentTimeMillis() + waitTime;
		while (System.currentTimeMillis() < endTime) {
			try {
				WebElement element = Wrapperdriver.findElement(byElement);
				if (!element.isDisplayed()) {
					return;
				}
			} catch (Exception e) {
				// Element not found or already invisible
				return;
			}
			try {
				Thread.sleep(100); // Polling interval
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void waitForElementToBeActive(By byElement, long waitTime) {
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(waitTime));
		wait.until(ExpectedConditions.elementSelectionStateToBe(byElement, true));
	}

	public List<WebElement> getAlldropdownOption(By by) {
		Select select = new Select(Wrapperdriver.findElement(by));
		return select.getOptions();
	}

	public String getNonSelectedOption(By selectBy) throws Exception {
		List<WebElement> options = this.getAlldropdownOption(selectBy);
		String currentSelection = this.getFirstSelectedOption(selectBy);
		List<WebElement> nonSelectedOptions = new ArrayList<>();
		for (WebElement option : options) {
			String optionText = option.getText();
			if (!optionText.equals(currentSelection)) {
				nonSelectedOptions.add(option);
			}
		}
		Random rand = new Random();
		WebElement randomOption = nonSelectedOptions.get(rand.nextInt(nonSelectedOptions.size()));
		String newOptionValue = randomOption.getText();
		return newOptionValue;
	}

	public boolean webElementIsChecked(By by) {
		try {
			if (Wrapperdriver.findElement(by).getAttribute("checked").equals("true"))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public void Refresh() throws InterruptedException {
		Thread.sleep(5000);
		Wrapperdriver.navigate().refresh();
		Thread.sleep(5000);
	}

	public boolean ifelementexist(By by) {
		int size = Wrapperdriver.findElements(by).size();
		if (size == 0)
			return false;
		else
			return true;
	}

	public List<String> ReturnWebElements(By by) {
		List<String> myElements = new ArrayList<String>();
		for (WebElement e : Wrapperdriver.findElements(by)) {
			myElements.add(e.getText());
		}
		return myElements;
	}

	public int ReturnWebElementsSize(By by) {
		List<WebElement> myElements = Wrapperdriver.findElements(by);
		return myElements.size();
	}

	public boolean valueDisplayedinCombox(String value) {

		WebElement ele = Wrapperdriver.findElement(By.xpath("//*[contains(text(),'" + value + "')]"));
		try {
			if (ele.isDisplayed())
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public void jsSendKeys(By by, String data) {
		WebElement element = Wrapperdriver.findElement(by);
		JavascriptExecutor js = (JavascriptExecutor) Wrapperdriver;
		js.executeScript("arguments[0].value = arguments[1]", element, data);
		System.out.println("Sendkeys Done");
	}

	public boolean textvisibleinPage(String value) {
		try {
			if (Wrapperdriver.getPageSource().contains(value))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSorted(List<String> list) {
		List<String> listSorted = new ArrayList<String>(list);
		Collections.sort(listSorted);
		return listSorted.equals(list);
	}

	public void ZoomOut() {
		// For mobile, use pinch gesture to zoom out
		pinchZoomOut();
	}

	public void ZoomOut(int zoomLevel) {
		// For mobile, use pinch gesture - zoomLevel is number of pinch operations
		for (int i = 0; i < zoomLevel; i++) {
			pinchZoomOut();
		}
	}

	public void waitForAngular() {
		final String javaScriptToLoadAngular = "var injector = window.angular.element('body').injector();"
				+ "var $http = injector.get('$http');" + "return ($http.pendingRequests.length === 0)";

		ExpectedCondition<Boolean> pendingHttpCallsCondition = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(javaScriptToLoadAngular).equals(true);
			}
		};
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		wait.until(pendingHttpCallsCondition);
	}

	public void WaitForPageLoad(WebDriver driver) throws Exception {
		ExpectedCondition<Boolean> pageLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DefaultTimeOut));
		Thread.sleep(1000);
		wait.until(pageLoad);
		Thread.sleep(2000);
	}

	public boolean isAttributePresent(By by, String attribute) {
		Boolean result = false;
		try {
			String value = Wrapperdriver.findElement(by).getAttribute(attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	public boolean isElementPresent(By by) {
		try {
			Wrapperdriver.findElement(by);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public void waitForPageLoad() {

		Wait<WebDriver> wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				System.out.println("Current Window State       : "
						+ String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
				return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
	}

	public <T> T waitForCondition(ExpectedCondition<T> condition, int timeout) {
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(timeout));
		return wait.until(condition);
	}

	public <T> T waitForCondition(ExpectedCondition<T> condition) {
		return waitForCondition(condition, DefaultTimeOut);
	}

	public void dragandDrop(By drag, By drop) throws Exception {

		WebElement sourceElement = Wrapperdriver.findElement(drag);
		WebElement destinationElement = Wrapperdriver.findElement(drop);
		// HTML 5
		final String java_script = "var src=arguments[0],tgt=arguments[1];var dataTransfer={dropEffe"
				+ "ct:'',effectAllowed:'all',files:[],items:{},types:[],setData:fun"
				+ "ction(format,data){this.items[format]=data;this.types.append(for"
				+ "mat);},getData:function(format){return this.items[format];},clea"
				+ "rData:function(format){}};var emit=function(event,target){var ev"
				+ "t=document.createEvent('Event');evt.initEvent(event,true,false);"
				+ "evt.dataTransfer=dataTransfer;target.dispatchEvent(evt);};emit('"
				+ "dragstart',src);emit('dragenter',tgt);emit('dragover',tgt);emit("
				+ "'drop',tgt);emit('dragend',src);";

		((JavascriptExecutor) Wrapperdriver).executeScript(java_script, sourceElement, destinationElement);
	}

	public void divDropDownSelect(By by, String xpathvalue) {
		Wrapperdriver.findElement(by).click();
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathvalue)));
		// Scroll to element if needed
		scrolltoelement(option);
		getTouchAction().tap(TapOptions.tapOptions().withElement(ElementOption.element(option))).perform();
	}

	public void divDropDownSelectListbox(By by, String xpathvalue) {
		Wrapperdriver.findElement(by).click();
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathvalue)));
		// Scroll to element if needed
		scrolltoelement(option);
		getTouchAction().tap(TapOptions.tapOptions().withElement(ElementOption.element(option))).perform();
	}

	public void refreshUntilElementDisplayed(By by, int timeoutInSeconds) {
		while (true) {
			try {
				WebElement element = Wrapperdriver.findElement(by);
				if (element.isDisplayed()) {
					System.out.println("Element is displayed.");
					break;
				}
			} catch (Exception e) {
				// Element not found or not displayed, continue refreshing
			}

			Wrapperdriver.navigate().refresh();
			System.out.println("Page Refreshed");

			// Wait for a short time to allow the page to reload
			try {
				Thread.sleep(timeoutInSeconds * 1000); // Adjust this delay as needed
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public List<WebElement> waitForAllElementToBePresent(By xpath) {
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(15));
		return wait.until(ExpectedConditions.visibilityOfAllElements(Wrapperdriver.findElements(xpath)));
	}

	public void selectFromListOfWebElemetsUsingText(By xpath, String textToBeSelected) throws InterruptedException {
		List<WebElement> elements = waitForAllElementToBePresent(xpath);
		Thread.sleep(2000);
		for (WebElement ele : elements) {
			Thread.sleep(500);
			// For mobile, scroll to element if needed
			scrolltoelement(ele);
			System.out.println("Text ==== " + ele.getText());
			if (ele.getText().equalsIgnoreCase(textToBeSelected)) {
				getTouchAction().tap(TapOptions.tapOptions().withElement(ElementOption.element(ele))).perform();
				break;
			}
		}
	}

	public Color getColorFromRgbaString(String rgba) {
		// Remove the "rgba(" and ")" from the string and split by commas
		String[] values = rgba.replace("rgba(", "").replace(")", "").split(",\\s*");

		// Parse the individual components
		int red = Integer.parseInt(values[0]);
		int green = Integer.parseInt(values[1]);
		int blue = Integer.parseInt(values[2]);

		// Convert the alpha value from 0-1 to 0-255 range
		int alpha = (int) (Float.parseFloat(values[3]) * 255);

		// Create and return the Color object
		return new Color(red, green, blue, alpha);
	}

	public void selectDisplayOptions(List<WebElement> allDisplayOptions, String optionToBeSelected)
			throws InterruptedException {

		if (!allDisplayOptions.isEmpty()) {
			Thread.sleep(1000);

			if (optionToBeSelected.equalsIgnoreCase("all") || optionToBeSelected.equalsIgnoreCase("on")) {
				for (WebElement ele : allDisplayOptions) {
					Thread.sleep(2000);
					String classAttr = ele.getAttribute("class");

					if (!classAttr.contains("on")) {
						ele.click();
					}
				}
			} else if (optionToBeSelected.equalsIgnoreCase("off")) {

				for (int i = allDisplayOptions.size() - 1; i >= 0; i--) {
					Thread.sleep(2000);
					String classAttr = allDisplayOptions.get(i).getAttribute("class");

					if (classAttr.contains("on")) {
						allDisplayOptions.get(i).click();
					}
				}
			}

		} else {
			Assert.fail("Can not select the Options from Display, As list is Empty !");
		}

	}

	/*
	 * Available buttonToBeSelected = Display, Map, GIS, Curtain
	 */
//	public void selectFromBottomButton(String buttonToBeSelected) throws InterruptedException {
//		buttonToBeSelected = buttonToBeSelected.toLowerCase();
//		Thread.sleep(2000);
//		try {
//			By commonBottomButtonsXP = By.xpath("//div[ contains(@id , 'bottom_button_" + buttonToBeSelected + "')]");
//			waitForElementToBeVisible(commonBottomButtonsXP, 20);
//			clickOnWebElement(commonBottomButtonsXP);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/*
	 * mapType = map , Satellite
	 */
	public void selectMap(String mapType) throws InterruptedException {
		mapType = mapType.toLowerCase();
		Thread.sleep(1000);
		By mapSelect = By.xpath("//div[@id = 'base_map_menu']//div[contains(@id, 'map_switch_" + mapType + "')]");

		if (mapType.equalsIgnoreCase("map")) {
			Thread.sleep(2000);
			waitForElementToBePresent(mapSelect, 10);
			WebElement ele = Wrapperdriver.findElement(mapSelect);
			String classAttr = ele.getAttribute("class");
			if (!classAttr.contains("on")) {
				ele.click();
			}
		} else if (mapType.equalsIgnoreCase("Satellite")) {

			Thread.sleep(2000);
			waitForElementToBePresent(mapSelect, 10);
			WebElement ele = Wrapperdriver.findElement(mapSelect);
			String classAttr = ele.getAttribute("class");
			if (!classAttr.contains("on")) {
				ele.click();
			}
		}
	}

	/*
	 * action = on / off
	 */
	public void makeBoundariesPipesOnOff(By by, String action) throws InterruptedException {
		Thread.sleep(1000);
		String classAttr = getAttributeOfWebElement(by, "class");

		if (action.equalsIgnoreCase("on")) {
			Thread.sleep(1000);
			if (!classAttr.equalsIgnoreCase("switch on")) {
				WebElement allBoundaries = waitForElementToBeVisible(by, 10);
				allBoundaries.click();
			}

		} else if (action.equalsIgnoreCase("off")) {
			Thread.sleep(1000);
			if (!classAttr.equalsIgnoreCase("switch")) {
				WebElement allBoundaries = waitForElementToBeVisible(by, 10);
				allBoundaries.click();
			}
		}
	}

	public Map<String, String> setRowMapValues(List<WebElement> cells) {
		Map<String, String> map;

		try {
			map = new HashMap<>();
			Thread.sleep(1000);

			for (int i = 0; i < cells.size() - 1; i++) {

				WebElement cell = cells.get(i);

				Thread.sleep(1000);

				String dataField = cell.getAttribute("data-field").trim();
				String value = cell.getText().trim();

				switch (dataField) {
				case "Tag":
					map.put("Tag", value);
					break;

				case "SurveyModeType":
					map.put("Mode", value);
					break;

				case "UserName":
					map.put("Driver", value);
					break;

				case "SerialNumber":
					map.put("Analyzer", value);
					break;

				case "SurveyorUnit":
					map.put("Surveyor", value);
					break;

				case "StartDateTime":
					map.put("Start Time", value);
					break;

				case "EndDateTime":
					map.put("End Time", value);
					break;
				}
			}

		} catch (Exception e) {
			map = null;
			e.printStackTrace();
		}

		System.out.println("----- Values From Row Map -----");
		assert map != null;

		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}

		return map;

	}

	public Map<String, String> setHeaderMapValues(List<WebElement> allHeaders) throws InterruptedException {
		Map<String, String> headerMap = new HashMap<>();

		for (WebElement header : allHeaders) {
			Thread.sleep(1000);
			String headerText = header.getText();
			if (!headerText.contains("Stability Class")) {

				int index = headerText.indexOf(":");

				if (index != -1) {
					String key = headerText.substring(0, index);
					String value = headerText.substring(index + 1).trim();
					headerMap.put(key, value);
				}
			}
		}
		System.out.println("----- Values From Header Map -----");
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}

		return headerMap;
	}

	public void clickOnAllIndications(List<WebElement> allIndicationToggleElements) throws Exception {
		Thread.sleep(1000);

		try {
			for (WebElement indiaction : allIndicationToggleElements) {
				Thread.sleep(1000);
				indiaction.click();
			}

		} catch (Exception e) {

		}

	}

	/*
	 * num = zooming level
	 */
	public void zoomIn(By xpath, int num) throws Exception {
		waitForElementToBePresent(xpath, 10);

		if (num > 0) {
			for (int i = 1; i <= num; i++) {
				Thread.sleep(1000);
				clickOnWebElement(xpath);
			}
		}
	}

	public void zoomOut(By xpath, int num) throws Exception {
		waitForElementToBePresent(xpath, 10);

		if (num >= 0) {
			for (int i = 1; i <= num; i++) {
				Thread.sleep(1000);
				clickOnWebElement(xpath);
			}
		}
	}

	public void switchOnDisplayOption(By xpath) throws InterruptedException {

		waitForElementToBePresent(xpath, 10);
		clickOnWebElement(xpath);
		Thread.sleep(1000);
	}

	public void zoomInOnMap(String xAxis, String yAxis) {
		// For mobile, use pinch gesture to zoom in at specific coordinates
		int x = Integer.parseInt(xAxis);
		int y = Integer.parseInt(yAxis);
		pinchZoomIn(x, y);
	}

	public void zoomOutOnMap() {
		// For mobile, use pinch gesture to zoom out
		pinchZoomOut();
	}

//	public void selectMaterialTypes(By xpath, String... materialTypes) throws InterruptedException {
//		Thread.sleep(2000);
//
//		List<WebElement> materialElements = waitForAllElementToBePresent(xpath);
//		System.out.println("Size of Material Elements === " + materialElements.size());
//		Thread.sleep(2000);
//		if (materialTypes == null || materialTypes.length == 0) {
//			throw new IllegalArgumentException("Material Type Must Not be Empty !!!");
//		}
//
//		for (int i = 0; i < materialElements.size(); i++) {
//
//			Thread.sleep(1000);
//
//			String materialText = materialElements.get(i).findElement(By.xpath(".//div[1]//div[1]")).getText().trim();
//
//			for (String materialType : materialTypes) {
//
//				if (materialText.equalsIgnoreCase(materialType.trim())) {
//					Thread.sleep(1000);
//					materialElements.get(i).findElement(By.xpath(".//div[1]//div[2]")).click();
//					System.out.println("Selected material: " + materialType);
//
//					break;
//				}
//			}
//		}
//	}

	public void webPageZoomer(int zoomLevel) throws InterruptedException {
		Thread.sleep(2000);
		// For mobile, use pinch gesture - zoomLevel determines number of operations
		if (zoomLevel > 100) {
			for (int i = 0; i < (zoomLevel - 100) / 10; i++) {
				pinchZoomIn();
			}
		} else {
			for (int i = 0; i < (100 - zoomLevel) / 10; i++) {
				pinchZoomOut();
			}
		}
	}

	public void validateMapStatus(String mapType) throws InterruptedException {
		mapType = mapType.toLowerCase();
		Thread.sleep(1000);
		By mapSelect = By.xpath("//div[@id = 'base_map_menu']//div[contains(@id, 'map_switch_" + mapType + "')]");

		if (mapType.equalsIgnoreCase("map")) {
			Thread.sleep(2000);
			waitForElementToBePresent(mapSelect, 10);
			WebElement ele = Wrapperdriver.findElement(mapSelect);
			String classAttr = ele.getAttribute("class");
			System.out.println("Class Atr " + classAttr);
			if (!classAttr.contains("on")) {
				assertTrue(false, mapType + ": is not selected");
			}
		} else if (mapType.equalsIgnoreCase("Satellite")) {

			Thread.sleep(2000);
			waitForElementToBePresent(mapSelect, 10);
			WebElement ele = Wrapperdriver.findElement(mapSelect);
			String classAttr = ele.getAttribute("class");
			System.out.println("Class Atr " + classAttr);
			if (!classAttr.contains("on")) {
				assertTrue(false, mapType + ": is not selected");
			}
		}
	}

	public void handleMap(String mapType, boolean validateOnly) throws InterruptedException {
		mapType = mapType.toLowerCase();
		Thread.sleep(1000);
		By mapSelect = By.xpath("//div[@id = 'base_map_menu']//div[contains(@id, 'map_switch_" + mapType + "')]");

		Thread.sleep(2000);
		waitForElementToBePresent(mapSelect, 10);
		WebElement ele = Wrapperdriver.findElement(mapSelect);
		String classAttr = ele.getAttribute("class");
		System.out.println("Class Atr " + classAttr);

		if (validateOnly) {
			if (!classAttr.contains("on")) {
				assertTrue(false, mapType + ": is not selected");
			}
		} else {
			if (!classAttr.contains("on")) {
				ele.click();
			}
		}
	}

	public HashMap<String, String> getTestData() {
		HashMap<String, String> userDetails = new HashMap<>();
		try {
			String userName = RandomStringUtils.randomAlphabetic(5);
			userDetails.put("UserName", "AutoV" + userName + "Test");

			String firstName = userName.split("\\.")[0].trim();
			userDetails.put("FirstName", firstName);

			String lastName = userName.split("\\.")[1].trim();
			userDetails.put("LastName", lastName);

			String password = RandomStringUtils.random(10, true, true);
			userDetails.put("password", password);

			String email = userName + "@mailinator.com";
			userDetails.put("Email", email);

			String phoneNumber = RandomStringUtils.randomNumeric(10);
			userDetails.put("Cell", phoneNumber);

			System.out.println("Generated User Details ==>>");
			System.out.println(userDetails);
		} catch (Exception e) {
			userDetails = null;
			e.printStackTrace();
		}

		return userDetails;
	}

	public boolean validateText(By xpath, String value) {
		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		System.out.println("Text === " + getTextOfWebElement(xpath));
		return wait.until(ExpectedConditions.textToBe(xpath, value));
	}

	public void sendKeysToWebelement(By by, String string, int duration) {

		WebDriverWait wait = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(duration));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		element.clear();
		element.sendKeys(string);
	}

	public void waitForElementInvisible(By xpath) {
		wait1 = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(50));
		try {
			while (flag) {

				System.out.println("Invisiblity ==" + Wrapperdriver.findElement(xpath).getAttribute("class"));
				boolean flag2 = wait1.until(ExpectedConditions.invisibilityOf(Wrapperdriver.findElement(xpath)));
				System.out.println("Flag2 === " + flag2);

				if (flag2 == true) {
					break;
				}
			}

		} catch (Exception e) {

		}
	}

	public String getTextOfWebElement(String xpath) {
		String textOfWebElement = "";
		wait1 = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		if (flag) {
			textOfWebElement = wait1.until(ExpectedConditions.visibilityOf(Wrapperdriver.findElement(By.xpath(xpath))))
					.getText();
		} else {
			textOfWebElement = null;
			throw new RuntimeException("Invisibility of Web Eement failed !!");
		}
		System.out.println("Text Captured :: " + textOfWebElement);
		return textOfWebElement;
	}

	public String generateRandomEmail(int lenght) {

		StringBuilder sb = new StringBuilder();

		while (sb.length() < lenght) {
			String emailData = RandomStringUtils.randomAlphabetic(8);

			if (sb.length() + emailData.length() <= 50) {
				sb.append(emailData).toString().replaceAll(" ", "");
			} else {
				break;
			}
		}

		String result = sb.toString().trim();

		if (result.length() > 50) {
			result = result.substring(0, 50);
		}
		result = result + "@mailinator.com";
		System.out.println("Generated Email : " + result);

		return result;
	}

	public boolean verifyPageTitle(String expectedPageTitle) {
		wait1 = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		return wait1.until(ExpectedConditions.titleIs(expectedPageTitle));
	}

	public boolean isElementPresent(String xPath) {
		wait1 = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		return wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath))).isDisplayed();
	}

	public WebElement getElementUsingCSSSelector(String cssSelector) {
		wait1 = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		WebElement element = wait1
				.until(ExpectedConditions.visibilityOf(Wrapperdriver.findElement(By.cssSelector(cssSelector))));
		return element;
	}

	public boolean verifyAttributeValueToBe(By xpath, String attribute, String attrValue) {
		wait1 = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		return wait1.until(ExpectedConditions.attributeToBe(xpath, attribute, attrValue));
	}

	public Boolean verifyTextToBe(By locator, String textToBe) {
		wait1 = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		return wait1.until(ExpectedConditions.textToBe(locator, textToBe));
	}

	public String handleAlert(String alertAction) {
		String alertText = "";

		wait1 = new WebDriverWait(Wrapperdriver, Duration.ofSeconds(DefaultTimeOut));
		Alert alert = wait1.until(ExpectedConditions.alertIsPresent());

		if (alertAction.equalsIgnoreCase("accept")) {
			alert.accept();
			alertText = "Alert Accepted";
			System.out.println(alertText);
		} else if (alertAction.equalsIgnoreCase("dismis")) {
			alert.dismiss();
			alertText = "Alert Dismissed";
			System.out.println(alertText);
		} else if (alertAction.equalsIgnoreCase("text")) {
			alertText = alert.getText();
			System.out.println(alertText);
		}

		return alertText;
	}

	public WebElement getFirstVisibleElement(By selector) {
		List<WebElement> elements = Wrapperdriver.findElements(selector);

		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				return element;
			}
		}

		return null;
	}

	public List<String> getAlldropdownOptions(WebElement element) {
		List<String> options = new ArrayList<String>();
		for (WebElement option : new Select(element).getOptions()) {
			if (option.getAttribute("value") != "")
				System.out.println("Available Options --> " + option.getText());
			options.add(option.getText());
		}
		return options;
	}

	public int getRandomIntegerNumbers(int count) {
		return Integer.parseInt(RandomStringUtils.randomNumeric(count));
	}

	// ========== Mobile-Specific Methods ==========

	/**
	 * Swipe down (scroll up) on the screen
	 */
	public void swipeDown() {
		int screenWidth = Wrapperdriver.manage().window().getSize().getWidth();
		int screenHeight = Wrapperdriver.manage().window().getSize().getHeight();
		int startY = (int) (screenHeight * 0.8);
		int endY = (int) (screenHeight * 0.2);
		int centerX = screenWidth / 2;

		getTouchAction().press(PointOption.point(centerX, startY))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
			.moveTo(PointOption.point(centerX, endY))
			.release().perform();
	}

	/**
	 * Swipe up (scroll down) on the screen
	 */
	public void swipeUp() {
		int screenWidth = Wrapperdriver.manage().window().getSize().getWidth();
		int screenHeight = Wrapperdriver.manage().window().getSize().getHeight();
		int startY = (int) (screenHeight * 0.2);
		int endY = (int) (screenHeight * 0.8);
		int centerX = screenWidth / 2;

		getTouchAction().press(PointOption.point(centerX, startY))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
			.moveTo(PointOption.point(centerX, endY))
			.release().perform();
	}

	/**
	 * Swipe left on the screen
	 */
	public void swipeLeft() {
		int screenWidth = Wrapperdriver.manage().window().getSize().getWidth();
		int screenHeight = Wrapperdriver.manage().window().getSize().getHeight();
		int startX = (int) (screenWidth * 0.8);
		int endX = (int) (screenWidth * 0.2);
		int centerY = screenHeight / 2;

		getTouchAction().press(PointOption.point(startX, centerY))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
			.moveTo(PointOption.point(endX, centerY))
			.release().perform();
	}

	/**
	 * Swipe right on the screen
	 */
	public void swipeRight() {
		int screenWidth = Wrapperdriver.manage().window().getSize().getWidth();
		int screenHeight = Wrapperdriver.manage().window().getSize().getHeight();
		int startX = (int) (screenWidth * 0.2);
		int endX = (int) (screenWidth * 0.8);
		int centerY = screenHeight / 2;

		getTouchAction().press(PointOption.point(startX, centerY))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
			.moveTo(PointOption.point(endX, centerY))
			.release().perform();
	}

	/**
	 * Tap on an element
	 */
	public void tapOnElement(By by) {
		WebElement element = Wrapperdriver.findElement(by);
		getTouchAction().tap(TapOptions.tapOptions().withElement(ElementOption.element(element))).perform();
	}

	/**
	 * Tap on an element
	 */
	public void tapOnElement(WebElement element) {
		getTouchAction().tap(TapOptions.tapOptions().withElement(ElementOption.element(element))).perform();
	}

	/**
	 * Long press on an element
	 */
	public void longPressOnElement(By by) {
		WebElement element = Wrapperdriver.findElement(by);
		getTouchAction().longPress(LongPressOptions.longPressOptions()
			.withElement(ElementOption.element(element))
			.withDuration(Duration.ofMillis(1000))).perform();
	}

	/**
	 * Long press on an element
	 */
	public void longPressOnElement(WebElement element) {
		getTouchAction().longPress(LongPressOptions.longPressOptions()
			.withElement(ElementOption.element(element))
			.withDuration(Duration.ofMillis(1000))).perform();
	}

	/**
	 * Pinch zoom in at screen center
	 */
	public void pinchZoomIn() {
		int screenWidth = Wrapperdriver.manage().window().getSize().getWidth();
		int screenHeight = Wrapperdriver.manage().window().getSize().getHeight();
		pinchZoomIn(screenWidth / 2, screenHeight / 2);
	}

	/**
	 * Pinch zoom in at specific coordinates
	 */
	public void pinchZoomIn(int centerX, int centerY) {
		int offset = 50;
		// Two finger pinch - move fingers closer together
		getTouchAction().press(PointOption.point(centerX - offset, centerY))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
			.moveTo(PointOption.point(centerX, centerY))
			.release().perform();
	}

	/**
	 * Pinch zoom out at screen center
	 */
	public void pinchZoomOut() {
		int screenWidth = Wrapperdriver.manage().window().getSize().getWidth();
		int screenHeight = Wrapperdriver.manage().window().getSize().getHeight();
		pinchZoomOut(screenWidth / 2, screenHeight / 2);
	}

	/**
	 * Pinch zoom out at specific coordinates
	 */
	public void pinchZoomOut(int centerX, int centerY) {
		int offset = 50;
		// Two finger pinch - move fingers apart
		getTouchAction().press(PointOption.point(centerX, centerY))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
			.moveTo(PointOption.point(centerX - offset, centerY))
			.release().perform();
	}

	/**
	 * Swipe from one element to another
	 */
	public void swipeFromElementToElement(By fromElement, By toElement) {
		WebElement from = Wrapperdriver.findElement(fromElement);
		WebElement to = Wrapperdriver.findElement(toElement);
		
		int fromX = from.getLocation().getX() + from.getSize().getWidth() / 2;
		int fromY = from.getLocation().getY() + from.getSize().getHeight() / 2;
		int toX = to.getLocation().getX() + to.getSize().getWidth() / 2;
		int toY = to.getLocation().getY() + to.getSize().getHeight() / 2;

		getTouchAction().press(PointOption.point(fromX, fromY))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
			.moveTo(PointOption.point(toX, toY))
			.release().perform();
	}

	/**
	 * Get device orientation
	 * Note: This method may need platform-specific implementation
	 */
	public String getOrientation() {
		// Orientation detection may require platform-specific code
		// For now, return a placeholder
		return "PORTRAIT"; // Default assumption
	}

	/**
	 * Rotate device to landscape
	 * Note: This method may need platform-specific implementation
	 */
	public void rotateToLandscape() {
		// Rotation may require platform-specific implementation
		// For Android: Use ADB commands or device-specific APIs
		// For iOS: Use XCUITest specific methods
		System.out.println("Rotate to landscape - requires platform-specific implementation");
	}

	/**
	 * Rotate device to portrait
	 * Note: This method may need platform-specific implementation
	 */
	public void rotateToPortrait() {
		// Rotation may require platform-specific implementation
		// For Android: Use ADB commands or device-specific APIs
		// For iOS: Use XCUITest specific methods
		System.out.println("Rotate to portrait - requires platform-specific implementation");
	}

	/**
	 * Hide keyboard (Android/iOS)
	 * Note: Platform-specific implementation may be required
	 */
	public void hideKeyboard() {
		// Hide keyboard implementation may vary by platform
		// For Android/iOS, this typically requires platform-specific driver methods
		System.out.println("Hide keyboard - may require platform-specific implementation");
	}

	/**
	 * Check if keyboard is displayed
	 * Note: Platform-specific implementation may be required
	 */
	public boolean isKeyboardShown() {
		// Keyboard detection may vary by platform
		// For Android, this typically requires platform-specific driver methods
		return false; // Default return
	}
}
