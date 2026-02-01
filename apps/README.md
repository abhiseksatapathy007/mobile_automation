# Apps Directory

Place your mobile application files in this directory:

- **Android**: Place your `.apk` files here
- **iOS**: Place your `.app` bundles here (for simulators) or `.ipa` files (for real devices)

## Example Structure

```
apps/
├── android-app.apk
├── ios-app.app
└── ios-app.ipa
```

## Configuration

After placing your app files, update the app paths in:
- `src/main/java/com/mobileautomation/Helper/Webdriverbase.java`

For Android:
```java
String appPath = System.getProperty("user.dir") + "/apps/your-app.apk";
```

For iOS:
```java
String appPath = userDir + "/apps/YourApp.app";
iosOptions.setBundleId("com.yourcompany.yourapp");
```

