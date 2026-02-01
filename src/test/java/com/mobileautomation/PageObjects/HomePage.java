package com.mobileautomation.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mobileautomation.Helper.Wrapperdriver;

/**
 * Generic Home Page Object
 * Update the locators based on your app's UI elements
 */
public class HomePage {
	public WebDriver driver = null;
	Wrapperdriver wd;

	// Constructor
	public HomePage(WebDriver driver) {
		this.driver = driver;
		wd = new Wrapperdriver(driver);
	}

	// Home Page Elements (Generic for both Android and iOS)
	// Update these locators based on your app
	public By welcomeMessage = By.xpath(
		"//*[@text='Welcome' or @name='Welcome' or @content-desc='Welcome']"
	);
	
	public By menuButton = By.xpath(
		"//*[@resource-id='menu' or @id='menu' or " +
		"@name='menu' or @content-desc='menu' or " +
		"@text='Menu' or @name='Menu']"
	);
	
	/**
	 * Check if home page is displayed
	 */
	public boolean isHomePageDisplayed() {
		return wd.ifelementexist(welcomeMessage) || wd.ifelementexist(menuButton);
	}
	
	/**
	 * Click on menu button
	 */
	public void clickMenuButton() {
		if (wd.ifelementexist(menuButton)) {
			wd.waitForElementToBeClickable(menuButton, 10);
			wd.clickOnWebElement(menuButton);
		}
	}
}

