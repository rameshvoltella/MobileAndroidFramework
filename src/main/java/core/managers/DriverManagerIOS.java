package core.managers;

import api.android.Android;
import core.MyLogger;
import core.Timer;
import core.constants.Arg;
import core.constants.Resources;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

import static core.managers.ServerManager.getDeviceId;

public class DriverManagerIOS {

    private static String nodeJS = "/usr/local/Cellar/node/6.8.0/bin/node";
    private static String appiumJS = "/usr/local/lib/node_modules/appium/build/lib/main.js";
    private static DriverService service;
    private static String deviceID;

    private static HashMap<String, URL> hosts;
    private static String unlockPackage = "de.telekom.mail";

    private static DesiredCapabilities getCaps(String deviceID) {
        MyLogger.log.info("Creating driver caps for device: " + deviceID);

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "10.3.3");
        caps.setCapability("deviceName", deviceID);
        caps.setCapability("app", "/Users/Shared/Appium/TelekomMail-VTU-Universal.ipa");
        caps.setCapability("udid", "2ccf30dd21fa31a77967a66580e6c7ee62ecce88");
        caps.setCapability("newCommandTimeout", 600);
        caps.setCapability("automationName", "XCUITest");
//        caps.setCapability(MobileCapabilityType.ROTATABLE, true);
        caps.setCapability("wdaConnectionTimeout", 60000);
        caps.setCapability("resetOnSessionStartOnly", true);
        caps.setCapability("useNewWDA", true);
        caps.setCapability("commandTimeouts", "120000");

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

    private static DriverService createService() throws IOException, ParseException {
        service = new AppiumServiceBuilder()
                .usingDriverExecutable(new File(nodeJS))
                .withAppiumJS(new File(appiumJS))
                .withIPAddress(host(deviceID).toString().split(":")[1].replace("//", ""))
                .usingPort(Integer.parseInt(host(deviceID).toString().split(":")[2].replace("/wd/hub", "")))
                .withArgument(Arg.TIMEOUT, "120")
                .withArgument(Arg.LOG_LEVEL, "warn")
                .build();
        return service;
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
                Android.driverIos = new IOSDriver(host(device), getCaps(device));
//                    Android.adb = new ADB(device);
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
            Android.driverIos.quit();
//            Android.adb.uninstallApp(unlockPackage);
//            Android.adb.killEmulator();
            service.stop();
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
}
