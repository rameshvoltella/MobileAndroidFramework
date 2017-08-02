package api.android;

import api.apps.Apps;
import core.ADB;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class Android {

    public static AndroidDriver driver;
    public static IOSDriver driverIos;
    public static ADB adb;
    public static Apps app = new Apps();
}
