package com.mobileautomation.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mobileautomation.Helper.Wrapperdriver;

/**
 * Generic Login Page Object
 * Update the locators based on your app's UI elements
 */
public class LoginPage {
	public WebDriver driver = null;
	Wrapperdriver wd;

	// Constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		wd = new Wrapperdriver(driver);
	}

	// Login Page Elements (Generic for both Android and iOS)
	// Update these locators based on your app
	public By usernameField = By.xpath(
		"//*[@resource-id='username' or @id='username' or " +
		"@name='username' or @content-desc='username' or " +
		"(@text and contains(@text, 'Username'))]"
	);
	
	public By passwordField = By.xpath(
		"//*[@resource-id='password' or @id='password' or " +
		"@name='password' or @content-desc='password' or " +
		"(@text and contains(@text, 'Password'))]"
	);
	
	public By loginButton = By.xpath(
		"//*[@resource-id='login' or @id='login' or " +
		"@name='login' or @content-desc='login' or " +
		"@text='Login' or @name='Login' or @content-desc='Login' or " +
		"@text='Sign in' or @name='Sign in' or @content-desc='Sign in']"
	);
	
	/**
	 * Complete login flow
	 * @param username - The username to enter
	 * @param password - The password to enter
	 */
	public void login(String username, String password) {
		wd.waitForElementToBeVisible(usernameField, 10);
		wd.sendKeysToWebelement(usernameField, username);
		
		wd.waitForElementToBeVisible(passwordField, 10);
		wd.sendKeysToWebelement(passwordField, password);
		
		wd.waitForElementToBeClickable(loginButton, 10);
		wd.clickOnWebElement(loginButton);
	}
	
	/**
	 * Check if login page is displayed
	 */
	public boolean isLoginPageDisplayed() {
		return wd.webElementIsDisplayed(usernameField);
	}
}

