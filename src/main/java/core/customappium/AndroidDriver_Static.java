package core.customappium;

import core.MyLogger;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Capabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static core.managers.ServerManager.getBootstrap;

public class AndroidDriver_Static {
    private static AppiumDriverLocalService service = null;
    private static AndroidDriver androidDriver = null;
    private static String nodeJS = "C:/nodejs/node.exe";
    private static String appiumJS = "C:/Users/lumihai/AppData/Roaming/npm/node_modules/appium/build/lib/main.js";
//    private static String appiumJS = "C:/Users/maiky/AppData/Roaming/npm/node_modules/appium/build/lib/main.js";

    private static void startAppiumServer() throws IOException, ParseException {
        service = AppiumDriverLocalService
                .buildService(new AppiumServiceBuilder()
                        .usingAnyFreePort()
                        .withAppiumJS(new File(appiumJS))
                        .usingDriverExecutable(new File(nodeJS))
                        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                        //.withArgument(GeneralServerFlag.CHROME_DRIVER_PORT,
                        //	CHROME_DRIVER_PORT)
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                        .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER,
                                getBootstrap())
                        //.withArgument(GeneralServerFlag.COMMAND_TIMEOUT, "60")
                        .withStartUpTimeOut(120, TimeUnit.SECONDS));
        MyLogger.log.info("New Appium service: " + service.getUrl());
    }

    public static AndroidDriver getAndroidDriver(URL address,
                                                 Capabilities capabilities) throws IOException, ParseException {
        // check if driver is null, or capabilities are different, initialise it
        if ((androidDriver == null)) {
            MyLogger.log.info("Getting New Appium Session");
            init(address, capabilities);
//            capabilitiesCurrent = capabilities;
        } else {
            MyLogger.log.info("Reusing Appium Session from previous run");
        }

        return androidDriver;
    }

    public static AndroidDriver getAndroidDriver() {
        return androidDriver;
    }

    private static void init(URL address, Capabilities capabilities) throws IOException, ParseException {
        try {
            if (address == null) {
                cleanAndroidDriver();
                startCreatingSessionOnJenkins();
                androidDriver = getNewDriver(service, capabilities);
            } else {
//                androidDriver = getNewDriver(address, capabilities);
            }
            MyLogger.log.info("New Appium Session:"
                    + androidDriver.getSessionId().toString());
            androidDriver.manage().timeouts()
                    .implicitlyWait(1, TimeUnit.SECONDS);
        } finally {
            finishedCreatingSessionOnSession();
        }
    }

    public static void cleanAndroidDriver() {
        try {
            androidDriver.quit();
        } catch (Throwable t) {
        }
    }

    private static void startCreatingSessionOnJenkins() throws IOException, ParseException {
        if ((service == null) || (!service.isRunning())) {
            startAppiumServer();
        }
    }

    private static AndroidDriver getNewDriver(AppiumDriverLocalService service2, Capabilities capabilities) {
        AndroidDriver ad = null;
        try {
            ad = new AndroidDriver(service2, capabilities);
        } catch (Throwable t) {
            // if it failed first time, try again
            ad = new AndroidDriver(service2, capabilities);
        }

        return ad;
    }

    private static void finishedCreatingSessionOnSession() {
        // if (getEnvironmentVariable(ENV_JENKINS_BOX) == null) {
        // log.info("Not a Jenkins Box, ignoring concurrent appium sessions handling");
        // return;
        // }
        // log.info("Removing sessionCreationInProgressFlag");
        // TerminalUtils.executeCommand("d:\\JenkinsSettings\\removeFlag.bat");
    }
}
