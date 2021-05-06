package com.pepgo;

import com.pepgo.config.AppiumFactory;
import com.pepgo.listeners.ScreenshotListener;
import com.pepgo.listeners.TestListener;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners({ScreenshotListener.class, TestListener.class})
public class AppiumBase {

    private static List<AppiumFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<AppiumFactory>());
    private static ThreadLocal<AppiumFactory> appiumFactory;

    @BeforeSuite(alwaysRun = true)
    public static void instantiateDriverObject() {
        appiumFactory = new ThreadLocal<AppiumFactory>() {
            @Override
            protected AppiumFactory initialValue() {
                AppiumFactory appiumFactory = new AppiumFactory();
                webDriverThreadPool.add(appiumFactory);
                return appiumFactory;
            }
        };
    }

    public static AppiumDriver getDriver() throws Exception {
        return appiumFactory.get().getDriver();
    }

    public static AppiumDriver getDriver(Activity desiredActivity) throws Exception {
        return appiumFactory.get().getDriver(desiredActivity);
    }

    @AfterSuite(alwaysRun = true)
    public static void closeDriverObjects() {
        for (AppiumFactory appiumFactory : webDriverThreadPool) {
            appiumFactory.quitDriver();
        }
    }
}