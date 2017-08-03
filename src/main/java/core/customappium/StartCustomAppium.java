package core.customappium;

import core.MyLogger;
import core.managers.DriverManagerAndroid;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.IOSServerFlag;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static core.managers.ServerManager.*;

public class StartCustomAppium {

    private static String hubUrl;
    private static String nodeJS = "C:/nodejs/node.exe";
    //    private static String appiumJS = "C:/Users/lumihai/AppData/Roaming/npm/node_modules/appium/build/lib/main.js";
    private static String appiumJS = "C:/Users/maiky/AppData/Roaming/npm/node_modules/appium/build/lib/main.js";

    public static AppiumDriverLocalService startLocalAppiumServer() throws IOException, ParseException {
        verifyEnvVars();
        AppiumDriverLocalService server;
        CustomAppiumServer customServer = new CustomAppiumServer();
        MyLogger.log.info("Detected request to start appium server from code");
        server = buildService(customServer);
        MyLogger.log.info("-------------STARTING APPIUM SERVER------------");
//        ExecutionTimer.start();
        server.start();
//        ExecutionTimer.stop();

        MyLogger.log.info(String.format(
                "Appium server running for device with UDID=%s at %s using bootstrap port=%s and chromedriverport=%s",
                getDeviceId(), getHubUrl(), getBootstrap(),
                getChromedriver()));
        return server;
    }

    private static AppiumDriverLocalService buildService(CustomAppiumServer customServer) throws IOException, ParseException {
        MyLogger.log.info("-------------SETTING UP APPIUM SERVER PARAMETERS------------");

        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File(nodeJS))
                .withAppiumJS(new File(appiumJS))
                .usingPort(Integer.parseInt(getPort()))
                .withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, getChromedriver())
                .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, getBootstrap())
                .withIPAddress(getIP()).withArgument(GeneralServerFlag.SESSION_OVERRIDE)
//                .withArgument(IOSServerFlag.WEBKIT_DEBUG_PROXY_PORT, getIosWebKit())
                .withCapabilities(getCaps()));
    }

    private static void verifyEnvVars() {
        verifyEnvVar("NODE_HOME");
        verifyEnvVar("APPIUM_HOME");
    }

    private static void verifyEnvVar(String varName) {
        MyLogger.log.info("Checking if env variable " + varName + " is set");
        if (null == System.getenv(varName) || "".equals(System.getenv(varName))) {
            MyLogger.log.error(varName + " IS NOT SET AS AN ENVIRONMENT VARIABLE");
            Assert.fail(varName + " IS NOT SET AS AN ENVIRONMENT VARIABLE");
        }
        MyLogger.log.info("Env var " + varName + " OK");
    }

    public static URL getHubUrl() throws IOException, ParseException {
        URL url = null;
        hubUrl = "http://" + getIP() + ":" + getPort() + "/wd/hub";
        try {
            url = new URL(hubUrl);
        } catch (MalformedURLException e) {
            MyLogger.log.error(e.getMessage());
        }
        return url;
    }

    public static DesiredCapabilities getCaps() throws IOException, ParseException {
        MyLogger.log.info("Creating driver caps for device: " + getDeviceId());

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", getDeviceId());
        caps.setCapability("platformName", "Android");
//        caps.setCapability("app", "D:/AppiumApk/Emma-prodautomation.apk");
        caps.setCapability("app", "E:/GradleProject/Emma-prodautomation.apk");
        caps.setCapability("automationName", "uiautomator2");
        return caps;
    }
}
