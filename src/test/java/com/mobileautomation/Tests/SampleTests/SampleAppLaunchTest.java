package com.mobileautomation.Tests.SampleTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.mobileautomation.PageObjects.HomePage;
import com.mobileautomation.Utility.BaseTest;

/**
 * Sample App Launch Test
 * This test verifies that the app launches successfully
 */
public class SampleAppLaunchTest extends BaseTest {
	HomePage homePage = null;

	@Test
	public void testAppLaunch() throws Exception {
		String testCaseName = new Throwable().getStackTrace()[0].getMethodName();
		test = extent.createTest(testCaseName, "Sample App Launch Test");
		extenttest.set(test);
		extenttest.get().assignCategory("Sample Tests - App Launch");

		homePage = new HomePage(driver);

		boolean statusflag = false;
		System.out.println("Test Case: " + testCaseName + " Started on thread: " + Thread.currentThread().getId());

		try {
			// Step 1: Wait for app to load
			Thread.sleep(3000);

			// Step 2: Verify app is launched (check for any home page element)
			boolean isAppLaunched = homePage.isHomePageDisplayed();
			Assert.assertTrue(isAppLaunched, "App should be launched successfully");
			test.log(Status.PASS, "App launched successfully");

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
						"App Launch: Successful\n" +
						"App Status: Running"
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

