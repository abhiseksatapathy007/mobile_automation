package com.mobileautomation.Tests.SampleTests;

import org.aeonbits.owner.ConfigFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.mobileautomation.PageObjects.HomePage;
import com.mobileautomation.PageObjects.LoginPage;
import com.mobileautomation.Utility.BaseTest;
import com.mobileautomation.Utility.Environment;

/**
 * Sample Login Test
 * This is a generic test case template that you can customize for your app
 */
public class SampleLoginTest extends BaseTest {
	LoginPage loginPage = null;
	HomePage homePage = null;
	Environment env;

	@BeforeTest
	@Parameters({ "environment" })
	public void beforeTest(String environment) {
		ConfigFactory.setProperty("testenv", environment);
		env = ConfigFactory.create(Environment.class);
	}

	@Test
	@Parameters({ "username", "password" })
	public void testLogin(@org.testng.annotations.Optional("") String username,
			@org.testng.annotations.Optional("") String password) throws Exception {

		String testCaseName = new Throwable().getStackTrace()[0].getMethodName();
		test = extent.createTest(testCaseName, "Sample Login Test");
		extenttest.set(test);
		extenttest.get().assignCategory("Sample Tests - Login");

		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);

		boolean statusflag = false;
		System.out.println("Test Case: " + testCaseName + " Started on thread: " + Thread.currentThread().getId());

		try {
			// Step 1: Verify login page is displayed
			Thread.sleep(2000); // Wait for app to load
			boolean isLoginPageDisplayed = loginPage.isLoginPageDisplayed();
			Assert.assertTrue(isLoginPageDisplayed, "Login page should be displayed");
			test.log(Status.PASS, "Login page displayed successfully");

			// Step 2: Perform login
			String loginUsername = (username != null && !username.isEmpty()) ? username : env.username();
			String loginPassword = (password != null && !password.isEmpty()) ? password : env.password();
			
			loginPage.login(loginUsername, loginPassword);
			test.log(Status.PASS, "Login completed successfully");

			// Step 3: Verify home page is displayed after login
			Thread.sleep(3000); // Wait for navigation
			boolean isHomePageDisplayed = homePage.isHomePageDisplayed();
			Assert.assertTrue(isHomePageDisplayed, "Home page should be displayed after login");
			test.log(Status.PASS, "Home page displayed successfully after login");

			statusflag = true;
		} catch (Exception e) {
			test.log(Status.FAIL, "Test failed with exception: " + e.getMessage());
			test.fail(e);
			throw e;
		} finally {
			if (statusflag == true) {
				System.out.println("Test Passed");
				test.log(Status.PASS, MarkupHelper.createLabel("TEST SUMMARY", ExtentColor.LIME));
				test.pass(MarkupHelper.createCodeBlock(
						"Login Test: Successful\n" +
						"Login Page: Displayed\n" +
						"Login Action: Completed\n" +
						"Home Page: Displayed"
				));
			} else {
				System.out.println("Test Failed");
				test.log(Status.FAIL, MarkupHelper.createLabel("TEST SUMMARY", ExtentColor.RED));
				test.fail(MarkupHelper.createCodeBlock(
						"Test Status: Failed"
				));
			}
		}
	}
}

