package tests;

import api.android.Android;
import core.MyLogger;
import core.classicmethods.AssertsUtils;
import core.managers.DriverManagerIOS;
import core.managers.TestManager;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.time.Duration;

public class TestSet_01_FirstTimeView extends TestManager {

    @Before
    public void skipLoginAndPin() {
        inflightiOS.workarounds.skipLoginmethod();
    }

    @Test
    public void navigateUpFTW() {

        testInfo.id("test1 from TestSet_01_FirstTimeView").name("navigateUpFTW").suite("GeneralTests");

        inflightiOS.firsttimeview.validateCloseBtnIsVisible();
        inflightiOS.firsttimeview.validatePageIndicatorValue("page 1 of 4");
        inflightiOS.firsttimeview.clickNextPageBtn();

        inflightiOS.firsttimeview.validatePageIndicatorIsVisible();
        inflightiOS.firsttimeview.validatePageIndicatorValue("page 2 of 4");
        inflightiOS.firsttimeview.validateBackBtnIsVisible();
        inflightiOS.firsttimeview.validateCloseBtnIsVisible();

        inflightiOS.firsttimeview.clickNextPageBtn();
        inflightiOS.firsttimeview.validatePageIndicatorValue("page 3 of 4");
        inflightiOS.firsttimeview.validateBackBtnIsVisible();
        inflightiOS.firsttimeview.validateCloseBtnIsVisible();

        inflightiOS.firsttimeview.clickNextPageBtn();
        inflightiOS.firsttimeview.validatePageIndicatorValue("page 4 of 4");
        inflightiOS.firsttimeview.validateBackBtnIsVisible();
        inflightiOS.firsttimeview.validateFinishBtnIsVisible();
        inflightiOS.firsttimeview.validateDontShowBtnIsVisible();
        inflightiOS.firsttimeview.validateCloseBtnIsVisible();


    }

    @Test
    public void navigateBackFTW() {

        testInfo.id("test2 from TestSet_01_FirstTimeView").name("navigateBackFTW").suite("GeneralTests");

        inflightiOS.firsttimeview.clickNextPageBtn();
        inflightiOS.firsttimeview.clickNextPageBtn();
        inflightiOS.firsttimeview.clickNextPageBtn();

        inflightiOS.firsttimeview.validatePageIndicatorValue("page 4 of 4");
        inflightiOS.firsttimeview.validateBackBtnIsVisible();
        inflightiOS.firsttimeview.validateFinishBtnIsVisible();
        inflightiOS.firsttimeview.validateDontShowBtnIsVisible();
        inflightiOS.firsttimeview.validateCloseBtnIsVisible();

        inflightiOS.firsttimeview.clickBackPageBtn();
        inflightiOS.firsttimeview.validatePageIndicatorValue("page 3 of 4");
        inflightiOS.firsttimeview.validateBackBtnIsVisible();
        inflightiOS.firsttimeview.validateCloseBtnIsVisible();

        inflightiOS.firsttimeview.clickBackPageBtn();
        inflightiOS.firsttimeview.validatePageIndicatorIsVisible();
        inflightiOS.firsttimeview.validatePageIndicatorValue("page 2 of 4");
        inflightiOS.firsttimeview.validateBackBtnIsVisible();
        inflightiOS.firsttimeview.validateCloseBtnIsVisible();

        inflightiOS.firsttimeview.clickBackPageBtn();
        inflightiOS.firsttimeview.validateBacktnIsNotVisible();
        inflightiOS.firsttimeview.validatePageIndicatorValue("page 1 of 4");

    }

    @Test
    public void ftwIsDisplayedAfterAppPutToBackground() {
        testInfo.id("test3 from TestSet_01_FirstTimeView").name("ftwIsDisplayedAfterAppPutToBackground").suite("GeneralTests");

        inflightiOS.firsttimeview.validatePageIndicatorValue("page 1 of 4");
        inflightiOS.firsttimeview.validateCloseBtnIsVisible();

        Android.driverIos.runAppInBackground(Duration.ofSeconds(2));
        inflightiOS.firsttimeview.validateCloseBtnIsVisible();
        inflightiOS.firsttimeview.validatePageIndicatorValue("page 1 of 4");
        inflightiOS.firsttimeview.clickNextPageBtn();

    }

    @Test
    public void ftwNotDisplayedAfterClosingTheApp() throws Exception {
        testInfo.id("test4 from TestSet_01_FirstTimeView").name("ftwNotDisplayedAfterClosingTheApp").suite("GeneralTests");

        inflightiOS.firsttimeview.validatePageIndicatorValue("page 1 of 4");
        inflightiOS.firsttimeview.validateCloseBtnIsVisible();
        DriverManagerIOS.createiOSDriverNoReset();
        Android.driverIos.closeApp();
        Android.driverIos.launchApp();
        inflightiOS.firsttimeview.validatePageIndicatorNotExists();
        inflightiOS.firsttimeview.validateBacktnIsNotVisible();

    }

    @Test
    public void ftwNotDisplayedAfterXBtnisPressed() {
        testInfo.id("test5 from TestSet_01_FirstTimeView").name("ftwNotDisplayedAfterXBtnisPressed").suite("GeneralTests");

        inflightiOS.firsttimeview.validatePageIndicatorValue("page 1 of 4");
        inflightiOS.firsttimeview.validateCloseBtnIsVisible();
        inflightiOS.firsttimeview.clickXBtn();
        inflightiOS.firsttimeview.validatePageIndicatorNotExists();
        inflightiOS.firsttimeview.validateBacktnIsNotVisible();

    }

    @Test
    public void enableDisableDontShow() throws Exception {
        testInfo.id("test6 from TestSet_01_FirstTimeView").name("enableDisableDontShow").suite("GeneralTests");

        inflightiOS.firsttimeview.clickNextPageBtn();
        inflightiOS.firsttimeview.clickNextPageBtn();
        inflightiOS.firsttimeview.clickNextPageBtn();

        inflightiOS.firsttimeview.clickDontShowAgainBtn();
        inflightiOS.firsttimeview.clickDontShowAgainBtn();
        DriverManagerIOS.createiOSDriverNoReset();
        Android.driverIos.closeApp();
        Android.driverIos.launchApp();
        inflightiOS.firsttimeview.validatePageIndicatorNotExists();

    }

    @Test
    public void swipeRightLeftToChangeFTW() throws IOException, ParseException {
        testInfo.id("test7 from TestSet_01_FirstTimeView").name("swipeRightLeftToChangeFTW").suite("GeneralTests");

        inflightiOS.firsttimeview.swipeLeftFTW();
        inflightiOS.firsttimeview.swipeLeftFTW();
        inflightiOS.firsttimeview.swipeLeftFTW();
        inflightiOS.firsttimeview.validatePageIndicatorValue("page 4 of 4");
        inflightiOS.firsttimeview.validateBackBtnIsVisible();
        inflightiOS.firsttimeview.validateFinishBtnIsVisible();
        inflightiOS.firsttimeview.validateDontShowBtnIsVisible();
        inflightiOS.firsttimeview.validateCloseBtnIsVisible();


        inflightiOS.firsttimeview.swipeRightFTW();
        inflightiOS.firsttimeview.swipeRightFTW();
        inflightiOS.firsttimeview.swipeRightFTW();
        inflightiOS.firsttimeview.validateCloseBtnIsVisible();
        inflightiOS.firsttimeview.validatePageIndicatorValue("page 1 of 4");

    }

}
