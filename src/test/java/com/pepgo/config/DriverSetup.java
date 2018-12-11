package com.pepgo.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface DriverSetup {
    DriverSetup createAppiumObject(URL appiumServerLocation, DesiredCapabilities capabilities);

    DriverSetup setActivity(Activity activity);

    AppiumDriver getAppiumDriver();
}