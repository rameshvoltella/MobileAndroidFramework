package core.customappium;

import core.MyLogger;
import core.managers.ServerManager;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static core.managers.ServerManager.*;

public class CustomAppiumServer {

    private String port;
    private String bp;
    private String chromedriverPort;
    private String logfilePath;
    private String ip;
    private String logLevel = "error:debug";
    private String iosWebkitDebugProxyPort;

    public CustomAppiumServer() throws IOException, ParseException {
        MyLogger.log.info("Creating instance of appium server object");
        port = getIP();
        if (getBootstrap() == null) {
            MyLogger.log.info("No bootstrap port detected in cfg file. Using default bootstrap port value 4825");
            bp = "4825";
        } else {
            bp = getBootstrap();
        }
        if (getChromedriver() == null) {
            MyLogger.log.info("No chromedriverPort detected in cfg file. Using default chromedriverPort value 4925");
            chromedriverPort = "4925";
        } else {
            chromedriverPort = getChromedriver();
        }
//        if (getIosWebKit() == null) {
//            MyLogger.log.info("iosWebkitDebugProxyPort not found in cfg file, falling back to 27753");
//            iosWebkitDebugProxyPort = "27753";
//        } else {
//            iosWebkitDebugProxyPort = getIosWebKit();
//        }

        String sepparator = ServerManager.getOsSepparator();
        DateFormat dateFormat = new SimpleDateFormat(
                "yyyy" + sepparator + "MM" + sepparator + "dd" + sepparator + "HH.mm.ss");
        Date date = new Date();
        String folder = System.getProperty("user.dir") + sepparator + "logs" + sepparator + "appiumLogs" + sepparator;
        logfilePath = folder + String.valueOf(dateFormat.format(date) + Thread.currentThread().getId() + ".txt");
        MyLogger.log.info("Appium logs can be found at " + logfilePath);
//        if (null != ConfigHelper.getInstance().getAppiumServerCapabilities().getLogLevel()) {
//            logLevel = ConfigHelper.getInstance().getAppiumServerCapabilities().getLogLevel();
//        }
        ip = getIP();
        MyLogger.log.info(toString());
    }
}
