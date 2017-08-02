package core.managers;

import api.android.Android;
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

import java.net.MalformedURLException;


public class TestManager {

    public static TestInfo testInfo = new TestInfo();
    public static Mail mail = Android.app.mail;

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
    public void before() throws MalformedURLException {
        testInfo.reset();
//        DriverManagerAndroid.createDriver();
        DriverManagerIOS.createiOSDriver();
    }

    @After
    public void cleanAfterTest() {
        DriverManagerAndroid.killDriver();
    }
}
