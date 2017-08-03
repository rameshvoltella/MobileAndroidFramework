package core.customappium;

import core.MyLogger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomAppiumServer {

    private String port;
    private String bp;
    private String chromedriverPort;
    private String logfilePath;
    private String ip;
    private String logLevel = "error:debug";
    private String iosWebkitDebugProxyPort;

    public CustomAppiumServer() {
        MyLogger.log.info("Creating instance of appium server object");
        port = "PORT FROM CONFIG FILE";
        if ("BOOTSTRATP PORT FROM CONFIG FILE" == null) {
            MyLogger.log.info("No bootstrap port detected in cfg file. Using default bootstrap port value 4825");
            bp = "4825";
        } else {
            bp = "NEW PORT";
        }
        if ("CHROMEDRIVER PORT FROM CONFIG FILE" == null) {
            MyLogger.log.info("No chromedriverPort detected in cfg file. Using default chromedriverPort value 4925");
            chromedriverPort = "4925";
        } else {
            chromedriverPort ="CHROMEDRIVER PORT FROM CONFIG FILE";
        }
        if (null == ConfigHelper.getInstance().getAppiumServerCapabilities().getIosWebkitDebugProxyPort()) {
            MyLogger.log.info("iosWebkitDebugProxyPort not found in cfg file, falling back to 27753");
            iosWebkitDebugProxyPort = "27753";
        } else {
            iosWebkitDebugProxyPort = ConfigHelper.getInstance().getAppiumServerCapabilities().getIosWebkitDebugProxyPort();
        }
        MyLogger.log.info("No logfilePath detected in cfg file. Using default logfilePath value current directory");
        String sepparator = OsUtils.getOsSepparator();
        DateFormat dateFormat = new SimpleDateFormat(
                "yyyy" + sepparator + "MM" + sepparator + "dd" + sepparator + "HH.mm.ss");
        Date date = new Date();
        String folder = System.getProperty("user.dir") + sepparator + "logs" + sepparator + "appiumLogs" + sepparator;
        logfilePath = folder + String.valueOf(dateFormat.format(date) + Thread.currentThread().getId() + ".txt");
        MyLogger.log.info.info("Appium logs can be found at " + logfilePath);
        if (null != ConfigHelper.getInstance().getAppiumServerCapabilities().getLogLevel()) {
            logLevel = ConfigHelper.getInstance().getAppiumServerCapabilities().getLogLevel();
        }
        ip = ConfigHelper.getInstance().getAppiumServerCapabilities().getIp();
        MyLogger.log.info(toString());
    }
}
