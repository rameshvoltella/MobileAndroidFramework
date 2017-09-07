package core.managers;

import api.android.Android;
import api.apps.inflightiOS.InflightiOS;
import api.apps.mail.Mail;
import core.MyLogger;
import core.Retry;
import core.TestInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static core.managers.ServerManager.*;


public class TestManager {

    public static TestInfo testInfo = new TestInfo();
    public static Mail mail = Android.app.mail;
    public static InflightiOS inflightiOS = Android.app.inflightiOS;

    @Rule
    public Retry retry = new Retry(1);
    //    public Retry retry = new Retry(3);
    @Rule
    public TestRule listen = new TestWatcher() {
        @Override
        public void failed(Throwable t, Description description) {
            MyLogger.log.info("Test Failed:");
            TestInfo.printResults();
        }

        @Override
        public void succeeded(Description description) {
            MyLogger.log.info("Test Passed:");
            TestInfo.printResults();
        }
    };

    @Before
    public void before() throws Exception {
        testInfo.reset();
        if (isWindows() && isAndroid()) {
            MyLogger.log.info("Driver creation started for Windows Environment");
            DriverManagerAndroid.createDriver();
//        } else if (isMac() && isAndroid()) {
//            MyLogger.log.info("Driver creation started for Windows Environment");
//            DriverManagerAndroid.createDriver();
        } else if (isMac() && isIos()) {
            MyLogger.log.info("Driver creation started for Mac Environment");
            DriverManagerIOS.createiOSDriver();
        } else {

            MyLogger.log.info("Environment is other than Windows and Mac. Please revise getOS method");
            throw new Exception("Setup is ran on other environment; no Windows or Mac could be identified");

        }
}

    @After
    public void cleanAfterTest() throws Exception {
        if (isWindows()) {
            MyLogger.log.info("Driver creation started for Windows Environment");
            DriverManagerAndroid.killDriver();
        } else if (isMac()) {
            MyLogger.log.info("Driver creation started for Mac Environment");
            DriverManagerIOS.killIOSDriver();
        } else {
            MyLogger.log.info("Environment is other than Windows and Mac. Please revise getOS method");
            throw new Exception("Setup is ran on other environment; no Windows or Mac could be identified");

        }
    }

}
