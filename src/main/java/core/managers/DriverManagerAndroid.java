package core.managers;

import api.android.Android;
import core.ADB;
import core.MyLogger;
import core.Timer;
import core.constants.Resources;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static core.managers.ServerManager.*;
import static core.managers.ServerManager.getDeviceId;


public class DriverManagerAndroid {

    private static String nodeJS = "C:/nodejs/node.exe";
    private static String appiumJS = "C:/Users/lumihai/AppData/Roaming/npm/node_modules/appium/build/lib/main.js";
//    private static String nodeJS = "/usr/local/Cellar/node/6.8.0/bin/node";
//    private static String appiumJS = "/usr/local/lib/node_modules/appium/build/lib/main.js";
    //    private static String appiumJS = "C:/Users/maiky/AppData/Roaming/npm/node_modules/appium/build/lib/main.js";
    private static String hubUrl;
    private static DriverService service;
    private static String deviceID;

    private static HashMap<String, URL> hosts;
    private static String unlockPackage = "de.telekom.mail";

    private static DesiredCapabilities getCaps() throws Exception {
        MyLogger.log.info("Creating driver caps for device: " + deviceID);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", getDeviceNameJason());
        caps.setCapability("platformName", getPlatformName());
        caps.setCapability("app", getAppLocation());
        caps.setCapability("automationName", getAutomationName());
        caps.setCapability("udid", getDeviceId());
        caps.setCapability("unicodeKeyboard", true);

        return caps;
    }

    private static URL host(String deviceID) throws Exception {
        String UDID = getDeviceId();
        if (hosts == null) {
            hosts = new HashMap<String, URL>();
            hosts.put(UDID, new URL("http://127.0.0.1:4723/wd/hub"));
        }
        return hosts.get(deviceID);
    }

    private static ArrayList<String> getAvailableDevices() {
        MyLogger.log.info("Checking for available devices");
        ArrayList<String> avaiableDevices = new ArrayList<String>();
        ArrayList connectedDevices = ADB.getConnectedDevices();
        for (Object connectedDevice : connectedDevices) {
            String device = connectedDevice.toString();
            ArrayList apps = new ADB(device).getInstalledPackages();
            if (!apps.contains(unlockPackage)) {
                if (useDevice(deviceID)) avaiableDevices.add(device);
                else MyLogger.log.info("Device: " + deviceID + " is being used by another JVM");
            } else
                MyLogger.log.info("Device: " + device + " has " + unlockPackage + " installed, assuming it is under testing");
        }
        if (avaiableDevices.size() == 0) {
            throw new RuntimeException("Not a single device is available for testing at this time");
//            createEmulator();
//            try {
//                Thread.sleep(40000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            ArrayList connectedDevices2 = ADB.getConnectedDevices();
//            for (Object connectedDevice : connectedDevices2) {
//                String device = connectedDevice.toString();
//                ArrayList apps = new ADB(device).getInstalledPackages();
//                if (!apps.contains(unlockPackage)) {
//                    if (useDevice(deviceID)) avaiableDevices.add(device);
//                    else MyLogger.log.info("Device: " + deviceID + " is being used by another JVM");
//                } else
//                    MyLogger.log.info("Device: " + device + " has " + unlockPackage + " installed, assuming it is under testing");
//            }
        }
        return avaiableDevices;
    }

    private static AppiumDriverLocalService createService() throws Exception {

        service = AppiumDriverLocalService
                .buildService(new AppiumServiceBuilder()
                        .usingDriverExecutable(new File(nodeJS))
                        .withAppiumJS(new File(appiumJS))
                        .withIPAddress(getIP())
                        .usingAnyFreePort()
                        .withArgument(GeneralServerFlag.ROBOT_ADDRESS, getDeviceId())
                        .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER,
                                getBootstrap())
//                        .withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, getChromedriver())
//                        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                        //.withArgument(GeneralServerFlag.COMMAND_TIMEOUT, "60")
                        .withStartUpTimeOut(120, TimeUnit.SECONDS)
                        .withCapabilities(getCaps()));
        MyLogger.log.info("+++++++++++++++++++++++ STARTING APPIUM SERVER ++++++++++++++++++++++");
        MyLogger.log.info(String.format(
                "Appium server running for device with UDID: " + getDeviceId() + " using bootstrap port: " + getBootstrap() + " and chromedriverport: " +
                        getChromedriver()));
        MyLogger.log.info("++++++++++++++++++ STARTED APPIUM SERVER ++++++++++++++++++: " + service.getUrl());
        return (AppiumDriverLocalService) service;
    }

    public static void createDriver() throws Exception {
        ArrayList connectedDevices = ADB.getConnectedDevices();
        if (connectedDevices.size() == 0) {
            throw new RuntimeException("Not a single device is available for testing at this time");
        } else if (connectedDevices.size() > 0) {
            for (Object connectedDevice : connectedDevices) {
                String device = connectedDevice.toString();
                if (!device.contains(getDeviceId())) {
                    throw new RuntimeException("Not a single device is available for testing at this time with udid: " + getDeviceId());
                }
            }


            String device = getDeviceId();
            try {
                deviceID = device;
                if (useDevice(deviceID)) {
                    queueUp();
                    gracePeriod();
                    MyLogger.log.info("Trying to create new Driver for device: " + device);
                    createService().start();
                    Android.driver = getNewDriver((AppiumDriverLocalService) service, getCaps());
                    Android.adb = new ADB(device);
                    leaveQueue();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //Ignore and try next device
            }
        }
    }

    public static void killDriver() {
        if (Android.driver != null) {
            MyLogger.log.info("Killing Android Driver");
            try {
                Android.driver.quit();
            } catch (Throwable t) {

            }
            try {
                Android.driver.close();
            } catch (Throwable t) {

            }
            Android.adb.uninstallApp(unlockPackage);
            try {
                service.stop();
            } catch (Throwable t) {

            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else MyLogger.log.info("Android Driver is not initialized, nothing to kill");
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

    private static AndroidDriver getNewDriver(AppiumDriverLocalService service1, Capabilities capabilities) {
        AndroidDriver ad = null;
        try {
            ad = new AndroidDriver(service1, capabilities);
        } catch (Throwable t) {
            // if it failed first time, try again
            ad = new AndroidDriver(service1, capabilities);
        }

        return ad;
    }

    public static URL getHubUrl() throws Exception {
        URL url = null;
        hubUrl = "http://" + getIP() + ":" + getPort() + "/wd/hub";
        try {
            url = new URL(hubUrl);
        } catch (MalformedURLException e) {
            MyLogger.log.error(e.getMessage());
        }
        return url;
    }
}
