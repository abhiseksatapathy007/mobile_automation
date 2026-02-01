# Mobile Automation Framework

A generic, cross-platform mobile automation framework built with Appium, TestNG, and Java. This framework supports both Android and iOS platforms and can be easily customized for any mobile application.

## Features

- **Cross-Platform Support**: Test on both Android and iOS devices/simulators
- **Page Object Model**: Clean, maintainable test structure using POM design pattern
- **Extent Reports**: Beautiful HTML reports with screenshots for test execution
- **BrowserStack Integration**: Run tests on cloud devices via BrowserStack
- **Parallel Execution**: Run tests in parallel across multiple devices
- **Mobile-Specific Actions**: Swipe, tap, long press, and other mobile gestures
- **Generic and Extensible**: Easy to customize for any mobile application
- **Comprehensive Helper Methods**: Rich set of utility methods for mobile automation

## Prerequisites

- **Java JDK 8 or higher** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+** - [Download](https://maven.apache.org/download.cgi)
- **Appium Server 2.x** - [Installation Guide](https://appium.io/docs/en/latest/quickstart/install/)
- **Android SDK** (for Android testing) - [Android Studio](https://developer.android.com/studio)
- **Xcode** (for iOS testing on macOS) - Available on Mac App Store
- **Android Emulator or iOS Simulator** (for local testing)

## Project Structure

```
mobile_automation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── mobileautomation/
│   │   │           ├── Helper/          # Helper classes
│   │   │           │   ├── Webdriverbase.java      # Driver initialization
│   │   │           │   ├── Wrapperdriver.java     # Mobile action wrappers
│   │   │           │   └── Commonfunctions.java   # Common utilities
│   │   │           └── Utility/         # Utility classes
│   │   │               ├── BaseTest.java           # Base test class
│   │   │               ├── Environment.java        # Environment config
│   │   │               ├── JSWaiter.java           # JavaScript wait utilities
│   │   │               └── SlackIntegration.java   # Slack notifications
│   │   └── resources/
│   │       ├── test.properties                    # Test environment config
│   │       ├── test.properties.example            # Example config file
│   │       ├── browserstack.properties             # BrowserStack config
│   │       └── browserstack.properties.example     # Example BrowserStack config
│   └── test/
│       └── java/
│           └── com/
│               └── mobileautomation/
│                   ├── PageObjects/     # Page Object classes
│                   │   ├── LoginPage.java
│                   │   └── HomePage.java
│                   └── Tests/
│                       └── SampleTests/ # Sample test cases
│                           ├── SampleAppLaunchTest.java
│                           └── SampleLoginTest.java
├── apps/                                # Place your .apk and .app files here
│   └── README.md                        # Instructions for app files
├── HTMLReport/                          # Test execution reports (generated)
├── pom.xml                              # Maven dependencies
├── testng.xml                           # TestNG test suite configuration
├── html-config.xml                      # Extent Reports configuration
├── start-appium.sh                      # Appium server startup script
├── docker-compose.yml                   # Docker configuration (optional)
├── README.md                            # This file
└── QUICK_START.md                       # Quick start guide
```

## Quick Start

For a quick setup guide, see [QUICK_START.md](QUICK_START.md).

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd mobile_automation
```

### 2. Install Dependencies

```bash
mvn clean install
```

This will download all required dependencies including:
- Appium Java Client
- Selenium WebDriver
- TestNG
- ExtentReports
- And other dependencies

### 3. Configure Test Environment

Copy the example configuration file and update with your credentials:

```bash
cp src/main/resources/test.properties.example src/main/resources/test.properties
```

Edit `src/main/resources/test.properties`:

```properties
username=your_username
password=your_password
```

### 4. Configure App Paths

Update `src/main/java/com/mobileautomation/Helper/Webdriverbase.java`:

**For Android** (around line 92):
```java
String appPath = System.getProperty("user.dir") + "/apps/your-app.apk";
```

**For iOS** (around line 130):
```java
iosOptions.setBundleId("com.yourcompany.yourapp");
String appPath = userDir + "/apps/YourApp.app";
```

### 5. Place Your App Files

- Place Android `.apk` files in the `apps/` directory
- Place iOS `.app` bundles in the `apps/` directory (for simulators)
- Place iOS `.ipa` files for real device testing

See `apps/README.md` for more details.

### 6. Start Appium Server

**Option 1: Using the provided script**
```bash
chmod +x start-appium.sh
./start-appium.sh
```

**Option 2: Manual start**
```bash
appium
```

Verify Appium is running by checking: `http://localhost:4723`

## Running Tests

### Run All Tests

```bash
mvn test
```

This will execute all tests defined in `testng.xml`.

### Run Specific Test Class

```bash
mvn test -Dtest=SampleLoginTest
```

### Run with Specific Parameters

You can pass parameters via command line or update `testng.xml`:

```bash
mvn test -Dtest=SampleLoginTest -Dusername=testuser -Dpassword=testpass
```

### Run on Specific Platform

Edit `testng.xml` to include only Android or iOS tests, or use test groups.

### Run with BrowserStack

```bash
mvn test -Dbrowserstack=true
```

## BrowserStack Integration

### 1. Configure BrowserStack

Copy the example configuration file:

```bash
cp src/main/resources/browserstack.properties.example src/main/resources/browserstack.properties
```

Edit `src/main/resources/browserstack.properties`:

```properties
browserstack.userName=your_username
browserstack.accessKey=your_access_key
browserstack.url=https://hub.browserstack.com/wd/hub
browserstack.app=bs://your_app_id
browserstack.buildName=Mobile-Automation-Build
browserstack.projectName=Mobile Automation Framework
browserstack.local=false
```

### 2. Upload Your App to BrowserStack

1. Use [BrowserStack App Automate](https://www.browserstack.com/app-automate) to upload your app
2. Get the app ID (starts with `bs://`)
3. Update the `browserstack.app` property in the configuration file

### 3. Run Tests on BrowserStack

```bash
mvn test -Dbrowserstack=true
```

## Writing Your Own Tests

### 1. Create Page Objects

Create a new class in `src/test/java/com/mobileautomation/PageObjects/`:

```java
package com.mobileautomation.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.mobileautomation.Helper.Wrapperdriver;

public class YourPage {
    public WebDriver driver;
    Wrapperdriver wd;
    
    public YourPage(WebDriver driver) {
        this.driver = driver;
        wd = new Wrapperdriver(driver);
    }
    
    // Define your locators
    public By yourElement = By.xpath("//*[@text='Your Element']");
    
    // Define your methods
    public void clickYourElement() {
        wd.waitForElementToBeClickable(yourElement, 10);
        wd.clickOnWebElement(yourElement);
    }
}
```

### 2. Create Test Classes

Create a new test class in `src/test/java/com/mobileautomation/Tests/`:

```java
package com.mobileautomation.Tests;

import com.mobileautomation.Utility.BaseTest;
import com.mobileautomation.PageObjects.YourPage;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;

public class YourTest extends BaseTest {
    
    @Test
    public void testYourFeature() {
        String testCaseName = new Throwable().getStackTrace()[0].getMethodName();
        test = extent.createTest(testCaseName, "Test Description");
        extenttest.set(test);
        extenttest.get().assignCategory("Your Category");
        
        YourPage yourPage = new YourPage(driver);
        
        try {
            // Your test steps
            yourPage.clickYourElement();
            test.log(Status.PASS, "Test passed");
        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            test.fail(e);
            throw e;
        }
    }
}
```

### 3. Finding Element Locators

Use **Appium Inspector** to find correct locators:

1. Start Appium Inspector
2. Connect to your device/simulator
3. Inspect elements to find locators
4. Update Page Object classes with correct locators

**Common Locator Strategies:**
- `By.id()` - Resource ID (Android) or Accessibility ID (iOS)
- `By.xpath()` - XPath expressions
- `By.className()` - Class name
- `By.name()` - Name attribute

## Available Helper Methods

The `Wrapperdriver` class provides comprehensive mobile automation methods:

### Click Actions
- `clickOnWebElement(By by)` - Click on element
- `clickOnWebElement(WebElement element)` - Click on WebElement
- `tapOnElement(By by)` - Tap on element (mobile-specific)
- `longPressOnElement(By by)` - Long press on element
- `InterceptclickOnWebElement(By by)` - Intercept click

### Input Actions
- `sendKeysToWebelement(By by, String text)` - Send text to element
- `clearElement(By by)` - Clear element text
- `inputValues(By by, String text)` - Input values

### Wait Actions
- `waitForElementToBeVisible(By by, long timeout)` - Wait for element visibility
- `waitForElementToBeClickable(By by, long timeout)` - Wait for element to be clickable
- `waitForElementToBePresent(By by, long timeout)` - Wait for element presence
- `waitForElementToDisappear(By by, long timeout)` - Wait for element to disappear

### Swipe Actions
- `swipeUp()` - Swipe up (scroll down)
- `swipeDown()` - Swipe down (scroll up)
- `swipeLeft()` - Swipe left
- `swipeRight()` - Swipe right
- `swipeFromElementToElement(By from, By to)` - Swipe between elements

### Element Checks
- `webElementIsDisplayed(By by)` - Check if element is displayed
- `ifelementexist(By by)` - Check if element exists
- `isElementPresent(By by)` - Check if element is present
- `webElementIsEnabled(By by)` - Check if element is enabled

### Screenshots
- `getScreenshot(WebDriver driver)` - Capture screenshot (returns base64)

### Other Utilities
- `scrolltoelement(By by)` - Scroll to element
- `getTextOfWebElement(By by)` - Get element text
- `getAttributeOfWebElement(By by, String attribute)` - Get element attribute

## Reports

Test execution reports are automatically generated in the `HTMLReport/` directory. 

**Features:**
- Beautiful HTML reports with ExtentReports
- Screenshots automatically captured on test failures
- Test execution timeline
- Device and environment information
- Test categories and groups

Open the HTML file in a browser to view detailed test results.

## Configuration Options

### TestNG Configuration

Edit `testng.xml` to:
- Configure parallel execution (`parallel="tests"`)
- Set thread count (`thread-count="2"`)
- Set test parameters
- Include/exclude test classes
- Configure test groups
- Set suite-level parameters

### Environment Configuration

Create multiple environment property files:
- `test.properties` - Test environment
- `staging.properties` - Staging environment  
- `production.properties` - Production environment

Reference them in `testng.xml`:
```xml
<parameter name="environment" value="test" />
```

### Extent Reports Configuration

Customize report appearance in `html-config.xml`:
- Theme colors
- Report title
- Logo
- View order

## Troubleshooting

### Appium Connection Issues

**Problem**: Cannot connect to Appium server

**Solutions**:
- Ensure Appium server is running: `appium`
- Check Appium is on default port 4723: `http://localhost:4723`
- Verify device/simulator is connected:
  - Android: `adb devices`
  - iOS: `xcrun simctl list devices`
- Check UDID for iOS devices

### Element Not Found

**Problem**: Element not found errors

**Solutions**:
- Update locators in Page Objects using Appium Inspector
- Increase wait times if element loads slowly
- Check if element is in a different view/context
- Verify element is visible (not hidden or off-screen)
- Use different locator strategies

### Build Issues

**Problem**: Maven build fails

**Solutions**:
- Ensure Java 8+ is installed: `java -version`
- Check Maven is properly configured: `mvn -version`
- Clean and rebuild: `mvn clean install`
- Check internet connection for dependency downloads
- Verify `pom.xml` is valid

### iOS Simulator Issues

**Problem**: iOS simulator not starting

**Solutions**:
- Ensure Xcode is installed and updated
- Check simulator is available: `xcrun simctl list devices`
- Verify bundle ID matches your app
- Check app is properly signed for simulator
- Use `.app` bundle for simulators (not `.ipa`)

### Android Emulator Issues

**Problem**: Android emulator not connecting

**Solutions**:
- Ensure Android SDK is installed
- Check emulator is running: `adb devices`
- Verify emulator name matches in capabilities
- Check Android version compatibility

## Best Practices

1. **Use Page Object Model**: Keep page objects separate from test logic for maintainability
2. **Use Meaningful Names**: Name your tests, methods, and variables clearly
3. **Add Explicit Waits**: Always use explicit waits instead of hard-coded sleeps
4. **Take Screenshots**: Screenshots are automatically captured on failures
5. **Keep Tests Independent**: Each test should be able to run standalone
6. **Use Data Providers**: For data-driven testing with TestNG
7. **Clean Up**: Ensure proper cleanup in `@AfterMethod`
8. **Handle Exceptions**: Properly handle and report exceptions
9. **Use Constants**: Store test data and configuration in constants or properties files
10. **Version Control**: Keep app files out of version control (use `.gitignore`)

## Sample Test Cases

The framework includes sample test cases to help you get started:

- **`SampleAppLaunchTest`** - Verifies app launches successfully
- **`SampleLoginTest`** - Demonstrates complete login flow

These samples show:
- How to structure test classes
- How to use Page Objects
- How to handle test reporting
- How to use ExtentReports

Customize these tests based on your application's requirements.

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Add tests for new features
5. Commit your changes (`git commit -m 'Add amazing feature'`)
6. Push to the branch (`git push origin feature/amazing-feature`)
7. Open a Pull Request

## License

This project is open source and available for use under the MIT License.

## Support

For issues and questions:
- Open an issue in the repository
- Check existing issues for solutions
- Review the [QUICK_START.md](QUICK_START.md) guide

## Additional Resources

- [Appium Documentation](https://appium.io/docs/en/latest/)
- [TestNG Documentation](https://testng.org/doc/documentation-main.html)
- [Selenium WebDriver Documentation](https://www.selenium.dev/documentation/)
- [ExtentReports Documentation](https://www.extentreports.com/docs/versions/5/java/)

---

**Happy Testing!**
