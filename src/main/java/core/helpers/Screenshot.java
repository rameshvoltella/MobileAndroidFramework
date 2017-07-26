package core.helpers;

import api.android.Android;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;

public class Screenshot {

    /**
     * Takes a screenshot of the device
     */
    public static void takeScreenshot() {
        takeScreenshot("");
    }

    /**
     * Take a screenshot of the device
     *
     * @param meaningfulFilelName - text to be added to the screenshot. Avoid crazy characters,
     *                            no validation on filename is done.
     */
    public static void takeScreenshot(String meaningfulFilelName) {
        takeScreenshot(meaningfulFilelName, 0);
    }

    /**
     * Take a screenshot of the device
     *
     * @param meaningfulFilelName - text to be added to the screenshot. Avoid crazy characters,
     *                            no validation on filename is done.
     * @param waitMilliSeconds    - ms to wait before taking the screenshot
     */
    public static void takeScreenshot(String meaningfulFilelName,
                                      long waitMilliSeconds) {

        if (waitMilliSeconds > 0) {
            try {
                Thread.sleep(waitMilliSeconds);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        File screenshot = getScreenshot();
        Image.saveImageAsArtefact(screenshot, meaningfulFilelName);
    }

    public static File getScreenshot() {
        WebDriver augmentedDriver = new Augmenter()
                .augment(Android.driver);
        File screenshot = null;

        int retry = 0;
        do {
            try {
                screenshot = ((TakesScreenshot) augmentedDriver)
                        .getScreenshotAs(OutputType.FILE);
            } catch (Throwable e) {
                // do nothing
            }
            retry++;
        } while ((screenshot == null) && (retry < 5));

        // it may have failed, but we dodn't have an exception
        if (screenshot == null) {
            screenshot = ((TakesScreenshot) augmentedDriver)
                    .getScreenshotAs(OutputType.FILE);
        }
        return screenshot;
    }
}
