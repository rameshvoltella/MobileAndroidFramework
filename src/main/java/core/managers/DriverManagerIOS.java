package core.managers;

import api.android.Android;
import core.MyLogger;
import core.Timer;
import core.constants.Arg;
import core.constants.Resources;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static core.managers.ServerManager.*;

public class DriverManagerIOS {

    private static String nodeJS = "/usr/local/Cellar/node/6.8.0/bin/node";
    private static String appiumJS = "/usr/local/lib/node_modules/appium/build/lib/main.js";
    private static DriverService service;
    private static String deviceID;

    private static HashMap<String, URL> hosts;
    private static String unlockPackage = "de.telekom.mail";
    private static boolean fullResetNeeded = true;

    private static DesiredCapabilities getCaps() throws IOException, ParseException {
        MyLogger.log.info("Creating driver caps for device: " + deviceID);

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "10.3.3");
        caps.setCapability("deviceName", deviceID);
        caps.setCapability("app", getAppLocation());
        caps.setCapability("udid", getDeviceId());
        caps.setCapability("newCommandTimeout", 600);
        caps.setCapability("automationName", getAutomationName());
//        caps.setCapability(MobileCapabilityType.ROTATABLE, true);
        caps.setCapability("wdaConnectionTimeout", 60000);
        caps.setCapability("resetOnSessionStartOnly", true);
        caps.setCapability("useNewWDA", true);
        caps.setCapability("commandTimeouts", "120000");
        if (!fullResetNeeded) {
            caps.setCapability("noReset", true);
        }

        return caps;
    }

    private static DesiredCapabilities getCapsNoReset() throws IOException, ParseException {
        MyLogger.log.info("Creating driver caps for device: " + deviceID);

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "10.3.3");
        caps.setCapability("deviceName", deviceID);
        caps.setCapability("app", getAppLocation());
        caps.setCapability("udid", getDeviceId());
        caps.setCapability("newCommandTimeout", 600);
        caps.setCapability("automationName", getAutomationName());
//        caps.setCapability(MobileCapabilityType.ROTATABLE, true);
        caps.setCapability("wdaConnectionTimeout", 60000);
        caps.setCapability("resetOnSessionStartOnly", true);
        caps.setCapability("useNewWDA", true);
        caps.setCapability("commandTimeouts", "120000");
            caps.setCapability("noReset", true);

        return caps;
    }

    private static URL host(String deviceID) throws IOException, ParseException {
        String UDID = getDeviceId();
        if (hosts == null) {
            hosts = new HashMap<String, URL>();
            hosts.put(UDID, new URL("http://127.0.0.1:4723/wd/hub"));
        }
        return hosts.get(deviceID);
    }

    private static AppiumDriverLocalService createService() throws IOException, ParseException {
        Map<String, String> env = new HashMap<>(System.getenv());
        env.put("PATH", "/usr/local/bin:" + env.get("PATH"));

        service = AppiumDriverLocalService
                .buildService(new AppiumServiceBuilder()
                        .usingDriverExecutable(new File(nodeJS))
                        .withAppiumJS(new File(appiumJS))
                        .withIPAddress(getIP())
                        .usingAnyFreePort()
                        .withEnvironment(env)
                        .withStartUpTimeOut(120, TimeUnit.SECONDS)
//                        .withArgument(Arg.LOG_LEVEL, "warn"));
                        .withArgument(Arg.LOG_LEVEL, "debug"));

        MyLogger.log.info("+++++++++++++++++++++++ STARTING APPIUM SERVER ++++++++++++++++++++++");
        MyLogger.log.info(String.format(
                "Appium server running for device with UDID: " + getDeviceId()));
        MyLogger.log.info("++++++++++++++++++ STARTED APPIUM SERVER ++++++++++++++++++: " + service.getUrl());
        return (AppiumDriverLocalService) service;
    }

    public static void createiOSDriver() throws IOException, ParseException {
        String device = getDeviceId();
        try {
            deviceID = device;
            if (useDevice(deviceID)) {
                queueUp();
                gracePeriod();
                MyLogger.log.info("Trying to create new Driver for device: " + device);
                createService().start();
                Android.driverIos = getNewDriver((AppiumDriverLocalService) service, getCaps());
                leaveQueue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Ignore and try next device
        }
    }

    public static void createiOSDriverNoReset() throws IOException, ParseException {
        String device = getDeviceId();
        try {
            deviceID = device;
            if (useDevice(deviceID)) {
                queueUp();
                gracePeriod();
                MyLogger.log.info("Trying to create new Driver for device: " + device);
                createService().start();
                Android.driverIos = getNewDriver((AppiumDriverLocalService) service, getCapsNoReset());
                leaveQueue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Ignore and try next device
        }
    }

    public static void killIOSDriver() {
        if (Android.driverIos != null) {
            MyLogger.log.info("Killing iOS Driver");
            try {
                Android.driverIos.quit();
            } catch (Throwable t) {

            }
            try {
                Android.driverIos.close();
            } catch (Throwable t) {

            }
            try {
                service.stop();
            } catch (Throwable t) {

            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else MyLogger.log.info("IOS Driver is not initialized, nothing to kill");
    }

    private static void queueUp() {
        try {
            MyLogger.log.info("Queueing Up: " + deviceID);
            JSONObject json = new JSONObject();
            json.put("queued_at", Timer.getTimeStamp());
            JSONObject jsonQueue = Resources.getQueue();
            jsonQueue.put(deviceID, json);
            MyLogger.log.info("JSON Queue: " + jsonQueue);
            ServerManager.write(new File(Resources.QUEUE), jsonQueue.toString());
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean useDevice(String deviceID) {
        try {
            JSONObject json = Resources.getQueue();
            if (json.containsKey(deviceID)) {
                JSONObject deviceJson = (JSONObject) json.get(deviceID);
                long time = (long) deviceJson.get("queued_at");
                int diff = Timer.getDifference(time, Timer.getTimeStamp());
                if (diff >= 30) return true;
                else return false;
            } else return true;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void gracePeriod() {
        int waitTime = 0;
        try {
            JSONObject json = Resources.getQueue();
            Set keys = json.keySet();

            JSONObject ourDeviceJson = (JSONObject) json.get(deviceID);
            json.remove(deviceID);
            long weQueuedAt = (long) ourDeviceJson.get("queued_at");

            for (Object key : keys) {
                JSONObject deviceJson = (JSONObject) json.get(key);
                long theyQueuedAt = (long) deviceJson.get("queued_at");
                //If we did not queue first we need to wait for the other device to initialize driver so there is no collision
                if (weQueuedAt > theyQueuedAt) {
                    //But only if device queued first and recently, otherwise we can assume device was already initialized or no longer being used
                    int diff = Timer.getDifference(theyQueuedAt, Timer.getTimeStamp());
                    if (diff < 50) {
                        MyLogger.log.info("Device: " + key + " queued first, I will need to give it extra time to initialize");
                        waitTime += 15;
                    }
                }
            }
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void leaveQueue() {
        try {
            JSONObject jsonQueue = Resources.getQueue();
            jsonQueue.remove(deviceID);
            ServerManager.write(new File(Resources.QUEUE), jsonQueue.toString());
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static IOSDriver getNewDriver(AppiumDriverLocalService service1, Capabilities capabilities) {
        IOSDriver ad = null;
        try {
            ad = new IOSDriver(service1, capabilities);
        } catch (Throwable t) {
            // if it failed first time, try again
            ad = new IOSDriver(service1, capabilities);
        }
        return ad;
    }

}
