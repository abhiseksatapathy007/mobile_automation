package com.mobileautomation.Helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

public class Webdriverbase {

	static Properties prop = new Properties();
	// Appium 2.x uses root endpoint, no /wd/hub needed
	public static String remote_url = "http://127.0.0.1:4723";
	public AppiumDriver Driver;
	private static Properties browserstackProps = null;

	// Load BrowserStack properties
	private static Properties getBrowserStackProperties() {
		if (browserstackProps == null) {
			browserstackProps = new Properties();
			try {
				String propPath = System.getProperty("user.dir") + "/src/main/resources/browserstack.properties";
				FileInputStream fis = new FileInputStream(propPath);
				browserstackProps.load(fis);
				fis.close();
			} catch (IOException e) {
				System.err.println("Error loading browserstack.properties: " + e.getMessage());
			}
		}
		return browserstackProps;
	}

	@BeforeTest
	@Parameters({ "browser", "deviceName", "platformVersion" })
	public WebDriver StartWebDriver(String browser, 
			@org.testng.annotations.Optional("") String deviceName, 
			@org.testng.annotations.Optional("") String platformVersion) throws Exception {
		
		// Check if BrowserStack is enabled via system property or parameter
		String useBrowserStack = System.getProperty("browserstack", "false");
		boolean isBrowserStack = "true".equalsIgnoreCase(useBrowserStack);

		URL url;
		Properties bsProps = getBrowserStackProperties();

		if (isBrowserStack && bsProps != null && !bsProps.isEmpty()) {
			// Use BrowserStack
			String bsUrl = bsProps.getProperty("browserstack.url");
			String bsUser = bsProps.getProperty("browserstack.userName");
			String bsKey = bsProps.getProperty("browserstack.accessKey");
			url = new URL("https://" + bsUser + ":" + bsKey + "@" + bsUrl.replace("https://", ""));
		} else {
			// Use local Appium
			url = new URL(remote_url);
		}

		switch (browser.toLowerCase()) {

		case "android":
			UiAutomator2Options androidOptions = new UiAutomator2Options();
			androidOptions.setPlatformName("Android");
			androidOptions.setAutomationName("UiAutomator2");

			if (isBrowserStack && bsProps != null && !bsProps.isEmpty()) {
				// BrowserStack Android capabilities
				androidOptions.setCapability("bstack:options", new java.util.HashMap<String, Object>() {
					{
						put("userName", bsProps.getProperty("browserstack.userName"));
						put("accessKey", bsProps.getProperty("browserstack.accessKey"));
						put("buildName", bsProps.getProperty("browserstack.buildName"));
						put("projectName", bsProps.getProperty("browserstack.projectName"));
						put("local", Boolean.parseBoolean(bsProps.getProperty("browserstack.local", "false")));
					}
				});
				androidOptions.setDeviceName((deviceName != null && !deviceName.isEmpty()) ? deviceName : "Samsung Galaxy S22 Ultra");
				androidOptions.setPlatformVersion((platformVersion != null && !platformVersion.isEmpty()) ? platformVersion : "12.0");
				androidOptions.setApp(bsProps.getProperty("browserstack.app"));
			} else {
				// Local Android capabilities
				androidOptions.setDeviceName((deviceName != null && !deviceName.isEmpty()) ? deviceName : "emulator-5554");
				androidOptions.setPlatformVersion((platformVersion != null && !platformVersion.isEmpty()) ? platformVersion : "16");
				String appPath = System.getProperty("user.dir") + "/app-staging-debug.apk";
				androidOptions.setApp(appPath);
				androidOptions.setSkipServerInstallation(false);
			}
			Driver = new AndroidDriver(url, androidOptions);
			break;

		case "ios":
			XCUITestOptions iosOptions = new XCUITestOptions();
			iosOptions.setPlatformName("iOS");
			iosOptions.setAutomationName("XCUITest");

			if (isBrowserStack && bsProps != null && !bsProps.isEmpty()) {
				// BrowserStack iOS capabilities
				iosOptions.setCapability("bstack:options", new java.util.HashMap<String, Object>() {
					{
						put("userName", bsProps.getProperty("browserstack.userName"));
						put("accessKey", bsProps.getProperty("browserstack.accessKey"));
						put("buildName", bsProps.getProperty("browserstack.buildName"));
						put("projectName", bsProps.getProperty("browserstack.projectName"));
						put("local", Boolean.parseBoolean(bsProps.getProperty("browserstack.local", "false")));
					}
				});
				iosOptions.setDeviceName((deviceName != null && !deviceName.isEmpty()) ? deviceName : "iPhone 14");
				iosOptions.setPlatformVersion((platformVersion != null && !platformVersion.isEmpty()) ? platformVersion : "16");
				iosOptions.setApp(bsProps.getProperty("browserstack.app"));
			} else {
				// Local iOS capabilities
				if (deviceName != null && !deviceName.isEmpty()) {
					iosOptions.setDeviceName(deviceName);
				} else {
					// Use first booted simulator if available, otherwise default to iPhone
					iosOptions.setDeviceName("iPhone 16 Pro");
					// Try to use UDID of booted simulator - iPhone 16 Pro
					iosOptions.setUdid("AF0B48AF-306A-418B-A5BD-526A781820C2");
				}
				
				// Set bundle ID (required)
				iosOptions.setBundleId("com.picarro.pcubedmobile.dev");
				
				// Set iOS app path - for simulators, use .app bundle (not .ipa)
				// .ipa files contain nested bundles which Appium can't handle for simulators
				String userDir = System.getProperty("user.dir");
				String appPath = userDir + "/SurveyorApp.app";
				java.io.File appFile = new java.io.File(appPath);
				
				if (appFile.exists() && appFile.isDirectory()) {
					// Verify it's a valid app bundle by checking for executable
					java.io.File executable = new java.io.File(appPath + "/SurveyorApp");
					if (executable.exists()) {
						iosOptions.setApp(appPath);
						System.out.println("Using .app bundle: " + appPath);
					} else {
						System.out.println("Warning: .app bundle found but executable missing. Using bundleId only.");
					}
				} else {
					System.out.println("Warning: .app bundle not found. Using bundleId only (app must be pre-installed).");
				}
				
				// Set platform version if provided
				if (platformVersion != null && !platformVersion.isEmpty()) {
					iosOptions.setPlatformVersion(platformVersion);
				}
				
				// Set reset options
				iosOptions.setNoReset(false);
				iosOptions.setFullReset(false);
			}
			Driver = new IOSDriver(url, iosOptions);
			break;

		default:
			throw new IllegalArgumentException("Unsupported platform: " + browser + ". Supported platforms: android, ios");
		}

		// Set implicit wait for mobile
		Driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		return Driver;
	}

}