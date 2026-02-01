# Quick Start Guide

## Prerequisites Checklist

- [ ] Java JDK 8+ installed
- [ ] Maven 3.6+ installed
- [ ] Appium Server 2.x installed and running
- [ ] Android SDK installed (for Android testing)
- [ ] Xcode installed (for iOS testing on macOS)
- [ ] Android Emulator or iOS Simulator set up

## Setup Steps

### 1. Install Dependencies

```bash
mvn clean install
```

### 2. Configure Your App

1. Place your app files in the `apps/` directory:
   - Android: `apps/your-app.apk`
   - iOS: `apps/YourApp.app` (for simulators)

2. Update `src/main/java/com/mobileautomation/Helper/Webdriverbase.java`:
   - Line ~92: Update Android app path
   - Line ~130: Update iOS bundle ID and app path

### 3. Configure Test Credentials

Copy the example file and update with your credentials:

```bash
cp src/main/resources/test.properties.example src/main/resources/test.properties
```

Edit `test.properties`:
```properties
username=your_username
password=your_password
```

### 4. Update Page Objects

Update the locators in:
- `src/test/java/com/mobileautomation/PageObjects/LoginPage.java`
- `src/test/java/com/mobileautomation/PageObjects/HomePage.java`

Use Appium Inspector to find the correct locators for your app.

### 5. Start Appium Server

```bash
appium
```

### 6. Run Tests

```bash
mvn test
```

## Customizing for Your App

### Finding Element Locators

1. Start Appium Inspector
2. Connect to your device/simulator
3. Inspect elements to find locators
4. Update Page Object classes with correct locators

### Creating New Page Objects

1. Create a new class in `src/test/java/com/mobileautomation/PageObjects/`
2. Define locators as `By` objects
3. Create methods for interactions
4. Use `Wrapperdriver` for actions

### Creating New Tests

1. Create a new test class extending `BaseTest`
2. Initialize Page Objects in your test
3. Use `test.log()` for reporting
4. Use `extenttest.get()` for test logging

## Common Issues

### Appium Connection Failed
- Ensure Appium is running: `appium`
- Check device is connected: `adb devices` (Android) or `xcrun simctl list` (iOS)

### Element Not Found
- Verify locators are correct
- Increase wait times
- Check if element is in a different view/context

### Build Errors
- Run `mvn clean install`
- Check Java version: `java -version`
- Verify Maven is installed: `mvn -version`

## Next Steps

- Read the full [README.md](README.md) for detailed documentation
- Review sample tests in `src/test/java/com/mobileautomation/Tests/SampleTests/`
- Customize Page Objects for your app
- Add your own test cases

