package core.helpers;

import api.android.Android;
import core.managers.ServerManager;
import io.appium.java_client.TouchAction;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.And;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.classicmethods.Swipe.refreshEmailListUntilEmailIsDisplayed;
import static core.classicmethods.Swipe.swipeDown;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EmailHelpers {

    private final static Logger log = Logger.getLogger(EmailHelpers.class);
    private static final int MAX_LOGIN_RETRIES = 10;
    private static final int MAX_FINDFOLDER_RETRIES = 5;
    private static boolean hasAllowedToCheckContacts = false;
    private static boolean hasAllowedThePushNotifications = false;
    private static boolean hasAllowedToCheckPhotos = false;
    private static long TOTALTIMEWAITINGFOREMAIL = 0;
    private static long TOTALTIMERESETAPP = 0;
    private static int currentLoginRetryCount = 0;
    private static String currentTestClass = "";
    private static String currentTestMethod = "";
    private static ScreenOrientation devicScreenOrientation = ScreenOrientation.PORTRAIT;

    public static ScreenOrientation getDevicScreenOrientation() {
        return devicScreenOrientation;
    }

    public static void setDevicScreenOrientation(ScreenOrientation devicScreenOrientation) {
        EmailHelpers.devicScreenOrientation = devicScreenOrientation;
    }

    /**
     * /** Sends another email to the mentioned address, and returns it's
     * subject. App needs to be in Inbox for this to work
     *
     * @param myEmailAddress - the email address to send the email to
     * @return Subject of email
     */
    public static String testMailSubject(String myEmailAddress) {

        String subject = String.format("Subject %f", Math.random());
        return testMailSubject(myEmailAddress, subject, getCurrentTestMethod());
    }

    private static String getCurrentTestMethod() {
        return currentTestClass;
    }

    /**
     * /** Sends a number of email to the mentioned address, and returns their
     * subject. App needs to be in Inbox for this to work
     *
     * @param myEmailAddress - the email address to send the email to
     * @return Subjects of emails sent
     */
    public static String[] testMailSubject(String myEmailAddress, int numberOfEmails) {

        String[] subjects = new String[numberOfEmails];
        for (int i = 0; i < numberOfEmails; i++) {
            subjects[i] = String.format("Subject %f", Math.random());
        }
        return testMailSubject(myEmailAddress, subjects, getCurrentTestMethod());
    }

    /**
     * Send another email to myEmailAddress, using a predefined sender. App
     * needs to be in Inbox for this to work
     *
     * @param myEmailAddress
     * @param subject
     * @param body
     * @return Subject of email
     */
    public static String testMailSubject(String myEmailAddress, String subject, String body) {
        return testMailSubject(myEmailAddress, "paket_qs_05@ver.sul.t-online.de", "1234test", subject, body);
    }

    /**
     * Send a set of emails to myEmailAddress, using a predefined sender. App
     * needs to be in Inbox for this to work
     *
     * @param myEmailAddress
     * @param subjects
     * @param body
     * @return Subjects of email
     */
    public static String[] testMailSubject(String myEmailAddress, String[] subjects, String body) {
        return testMailSubject(myEmailAddress, "paket_qs_05@ver.sul.t-online.de", "1234test", subjects, body);
    }

    /**
     * Send a set of emails to myEmailAddress, using a predefined sender. App
     * needs to be in Inbox for this to work
     *
     * @param myEmailAddress
     * @param subjects
     * @param body
     * @return Subjects of email
     */
    public static String[] testMailSubject(String myEmailAddress, String fromEmailAddress, String[] subjects,
                                           String body) {
        return testMailSubject(myEmailAddress, fromEmailAddress, "1234test", subjects, body);
    }

    /**
     * Send another email to myEmailAddress, using the provided sender. App
     * needs to be in Inbox for this to work
     *
     * @param myEmailAddress
     * @param fromEmailAddress
     * @param fromEmailPassword
     * @param subject
     * @param body
     * @return
     */
    public static String testMailSubject(String myEmailAddress, String fromEmailAddress, String fromEmailPassword,
                                         String subject, String body) {
        String[] subjects = new String[1];
        subjects[0] = subject;
        String[] returnSubject = testMailSubject(myEmailAddress, fromEmailAddress, fromEmailPassword, subjects, body);
        return returnSubject[0];
    }

    /**
     * Send a set of emails to myEmailAddress, using the provided sender. App
     * needs to be in Inbox for this to work
     *
     * @param myEmailAddress
     * @param fromEmailAddress
     * @param fromEmailPassword
     * @param subjects
     * @param body
     * @return
     */
    public static String[] testMailSubject(String myEmailAddress, String fromEmailAddress, String fromEmailPassword,
                                           String[] subjects, String body) {
        try {
            for (String subject : subjects) {
                SmtpEmail.putMailInInbox(fromEmailAddress, fromEmailPassword, myEmailAddress, subject, body);
            }
        } catch (AddressException e) {
            e.printStackTrace();
            fail("Smtp email sending failed with address exception");
        } catch (MessagingException e) {
            e.printStackTrace();
            fail("Smtp email sending failed with messaging exception");
        }

        swipeDown();
        waitForEmail(Folder.POSTEINGANG, subjects);

        return subjects;
    }

    /**
     * Send another email to myEmailAddress, with one attachment from camera
     * roll
     *
     * @param myEmailAddress
     * @return Subject of email
     */
    public static String testMailSubjectWithAttachment(String myEmailAddress) {

        String subject = String.format("Subject %f", Math.random());
        try {
            SmtpEmail.putMailInInboxWithAttachment("paket_qs_05@ver.sul.t-online.de", "1234test", myEmailAddress,
                    subject, getCurrentTestMethod(), "target/test-classes/TestImage.jpg");
        } catch (AddressException e) {
            e.printStackTrace();
            fail("Smtp email sending failed with address exception");
        } catch (MessagingException e) {
            e.printStackTrace();
            fail("Smtp email sending failed with messaging exception");
        }
        waitForEmail(Folder.POSTEINGANG, subject);
        return subject;
    }

    /**
     * Send another email to myEmailAddress, with one attachment from camera
     * roll
     *
     * @param myEmailAddress
     * @param pathToAttachment
     * @return Subject of email
     */
    public static String testMailSubjectWithAttachment(String myEmailAddress, String pathToAttachment) {

        String subject = String.format("Subject %f", Math.random());
        try {
            SmtpEmail.putMailInInboxWithAttachment("vtu.capgemini1@ver.sul.t-online.de", "1234test", myEmailAddress,
                    subject, getCurrentTestMethod(), pathToAttachment);
        } catch (AddressException e) {
            e.printStackTrace();
            fail("Smtp email sending failed with address exception");
        } catch (MessagingException e) {
            e.printStackTrace();
            fail("Smtp email sending failed with messaging exception");
        }
        waitForEmail(Folder.POSTEINGANG, subject);
        return subject;
    }

    /**
     * Opens an email, while in a folder. Needed because of how email element is
     * identified in different iOS version
     *
     * @param subject
     */
    public static void openEmail(String subject) {
        // 99.9% of the time the email is visible.
        try {
            log.info("Clicking email with subject: " + subject);
            clickEmail(subject);
        } catch (RuntimeException e) {
            log.info("clickEmail exception caught:");
            e.printStackTrace();
            if (e.getCause() instanceof TimeoutException) {
                refreshEmailListUntilEmailIsDisplayed(subject);
                clickEmail(subject);
            } else {
                throw e;
            }
        }
    }

    private static void clickEmail(String subject) {
        WebElement we = (WebElement) Android.driver.findElement(By.id(subject));
        int heigt = we.getLocation().getY();
        int width = we.getLocation().getX();
        TouchAction touchAction = new TouchAction(Android.driver).press(width, heigt).release()
                .perform();
    }

    public static void tapImageSection(String imageName) {

        // change below variable to false if you need to take all the
        // screenshots for a test. If false, will not crash the test if images
        // do not match, but still harvest screenshots and comparison results.
        // !!!NEVER COMMIT WITH VALUE false!!!!

        boolean crashTestIfNoMatch = true;

        boolean found = false;
        double percentageFound = 0.0;
        Coordinates coordinates = new Coordinates();

        String imagePath = String.format("target/test-classes/%s_%s.png", imageName, getImageDeviceSuffix());
        BufferedImage sectionBI;
        try {
            sectionBI = Image.getBufferedImageFromFile(imagePath);
            BufferedImage screenshotBI = null;

            File screenshot = Screenshot.getScreenshot();
            screenshotBI = ImageIO.read(screenshot);

            percentageFound = ImageSectionSikuli.findSubImage(screenshotBI, imagePath, coordinates);

            int cX = coordinates.getX();
            int cY = coordinates.getY();
            int sW = sectionBI.getWidth();
            int sH = sectionBI.getHeight();

            if (percentageFound > 0.999) { // changed to 0.97 from 0.999 because
                // ipad has time in screens
                // Kai-Felix Braun 21.04.2016
                found = true;
            } else {
                log.info(String.format("Image %s not found in screenshot for tap.", imagePath));
            }

            if (!found) {
                BufferedImage combined = new BufferedImage(2 * screenshotBI.getWidth(), screenshotBI.getHeight(),
                        BufferedImage.TYPE_INT_ARGB);

                // paint both images, preserving the alpha channels
                Graphics g = combined.getGraphics();
                g.drawImage(sectionBI, 0, 0, null);
                g.drawImage(screenshotBI, screenshotBI.getWidth(), 0, null);

                // draw differences
                Graphics2D gc = combined.createGraphics();
                gc.setColor(Color.RED);

                for (int i = 0; i < sW; i++) {
                    for (int j = 0; j < sH; j++) {

                        if (!Image.isPixelTheSame(i, j, i + cX, j + cY, sectionBI, screenshotBI, 1)) {
                            // draw an indicator on the change image to show
                            // where change was detected.
                            int x1 = i / Image.DIFFERENCE_SQUARE_SIZE;
                            x1 = x1 * Image.DIFFERENCE_SQUARE_SIZE;
                            int w = Image.DIFFERENCE_SQUARE_SIZE;
                            int y1 = j / Image.DIFFERENCE_SQUARE_SIZE;
                            y1 = y1 * Image.DIFFERENCE_SQUARE_SIZE;
                            int h = Image.DIFFERENCE_SQUARE_SIZE;
                            gc.drawRect(x1, y1, w, h);
                            gc.drawRect(x1 + cX + screenshotBI.getWidth(), y1 + cY, w, h);
                        }
                    }
                }
                Image.saveImageAsArtefact(combined, "LeftExpectedRightActual");
                // also save screenshot with the not found name, it may help
                // testers regenerate the missing sections easily
                Image.saveImageAsArtefact(screenshotBI, String.format("%s_%s", imageName, getImageDeviceSuffix()));
            }
            if (crashTestIfNoMatch) {
                assertTrue("Screenshot does not contain expected section for tapping", found);
            }

            // tap on coordinates
            new TouchAction(Android.driver).longPress(cX + sW / 2, cY + sH / 2, Duration.ofSeconds(1)).release().perform();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                File screenshot = Screenshot.getScreenshot();
                BufferedImage screenshotBI = ImageIO.read(screenshot);
                Image.saveImageAsArtefact(screenshotBI, String.format("%s_%s", imageName, getImageDeviceSuffix()));
            } catch (IOException ee) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (crashTestIfNoMatch) {
                fail(String.format("Cannot read image from file %s for comparison", imagePath));
            }
        }
    }

    public static void compareImageSectionToCurrentScreen(String imageName) {
        compareImageSectionToCurrentScreen(imageName, 1);
    }

    public static void compareImageSectionToCurrentScreen(String imageName, int maxRetries) {

        // change below variable to false if you need to take all the
        // screenshots for a test. If false, will not crash the test if images
        // do not match, but still harvest screenshots and comparison results.
        // !!!NEVER COMMIT WITH VALUE false!!!!

        boolean crashTestIfNoMatch = false;

        int currentRetry = 0;
        boolean found = false;
        double percentageFound = 0.0;
        Coordinates coordinates = new Coordinates();

        String imagePath = String.format("target/test-classes/%s_%s.png", imageName, getImageDeviceSuffix());
        BufferedImage sectionBI;
        try {
            sectionBI = Image.getBufferedImageFromFile(imagePath);
            BufferedImage screenshotBI = null;
            while ((currentRetry < maxRetries) && !found) {
                File screenshot = Screenshot.getScreenshot();
                screenshotBI = ImageIO.read(screenshot);

                percentageFound = ImageSectionSikuli.findSubImage(screenshotBI, imagePath, coordinates);

                if (percentageFound > 0.999) { // changed to 0.97 from 0.999
                    // because ipad has time in
                    // screens Kai-Felix Braun
                    // 21.04.2016
                    found = true;
                } else {
                    currentRetry++;
                    log.info(String.format("Image %s not found in screenshot. Try %d/%d", imagePath, currentRetry,
                            maxRetries));
                }
            }

            if (!found) {
                BufferedImage combined = new BufferedImage(2 * screenshotBI.getWidth(), screenshotBI.getHeight(),
                        BufferedImage.TYPE_INT_ARGB);

                // paint both images, preserving the alpha channels
                Graphics g = combined.getGraphics();
                g.drawImage(sectionBI, 0, 0, null);
                g.drawImage(screenshotBI, screenshotBI.getWidth(), 0, null);

                // draw differences
                Graphics2D gc = combined.createGraphics();
                gc.setColor(Color.RED);

                int cX = coordinates.getX();
                int cY = coordinates.getY();
                int sW = sectionBI.getWidth();
                int sH = sectionBI.getHeight();

                for (int i = 0; i < sW; i++) {
                    for (int j = 0; j < sH; j++) {

                        if (!Image.isPixelTheSame(i, j, i + cX, j + cY, sectionBI, screenshotBI, 1)) {
                            // draw an indicator on the change image to show
                            // where change was detected.
                            int x1 = i / Image.DIFFERENCE_SQUARE_SIZE;
                            x1 = x1 * Image.DIFFERENCE_SQUARE_SIZE;
                            int w = Image.DIFFERENCE_SQUARE_SIZE;
                            int y1 = j / Image.DIFFERENCE_SQUARE_SIZE;
                            y1 = y1 * Image.DIFFERENCE_SQUARE_SIZE;
                            int h = Image.DIFFERENCE_SQUARE_SIZE;
                            gc.drawRect(x1, y1, w, h);
                            gc.drawRect(x1 + cX + screenshotBI.getWidth(), y1 + cY, w, h);
                        }
                    }
                }
                Image.saveImageAsArtefact(combined, "LeftExpectedRightActual");
                // also save screenshot with the not found name, it may help
                // testers regenerate the missing sections easily
                Image.saveImageAsArtefact(screenshotBI, String.format("%s_%s", imageName, getImageDeviceSuffix()));
            }
            if (crashTestIfNoMatch) {
                assertTrue("Screenshot does not contain expected section", found);
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                File screenshot = Screenshot.getScreenshot();
                BufferedImage screenshotBI = ImageIO.read(screenshot);
                Image.saveImageAsArtefact(screenshotBI, String.format("%s_%s", imageName, getImageDeviceSuffix()));
            } catch (IOException ee) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (crashTestIfNoMatch) {
                fail(String.format("Cannot read image from file %s for comparison", imagePath));
            }
        }
    }

    public static void saveScreenshotForFutureUnitTests(String newFilePathAndName) {

        try {
            File screenshot = Screenshot.getScreenshot();

            BufferedImage screenshotBI;

            screenshotBI = ImageIO.read(screenshot);

            screenshotBI = Image.cropBufferedImageForTesting(screenshotBI);

            // extract folder using regex
            Pattern p = Pattern.compile("(.*)/.*");
            Matcher m = p.matcher(newFilePathAndName);
            if (m.find()) {
                String folder = m.group(1);
                File folderFile = new File(folder);
                if (!folderFile.exists()) {
                    FileUtils.forceMkdir(folderFile);
                }
            }

            newFilePathAndName = String.format("%s_%s.png", newFilePathAndName, getImageDeviceSuffix());
            File newFile = new File(newFilePathAndName);
            newFile.createNewFile();

            ImageIO.write(screenshotBI, "png", newFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static String getImageDeviceSuffix() {
        String imageDeviceSuffix;

        if (ServerManager.OS_VERSION.startsWith("6")) {
            imageDeviceSuffix = "Android6";
        } else if (ServerManager.OS_VERSION.startsWith("7")) {
            imageDeviceSuffix = "Android7";
        } else {
            imageDeviceSuffix = "Android8";
        }

        if (getDevicScreenOrientation() == ScreenOrientation.LANDSCAPE) {
            imageDeviceSuffix = imageDeviceSuffix + "_L";
        } else {
            imageDeviceSuffix = imageDeviceSuffix + "_P";
        }

        if ((ServerManager.DEVICE_NAME.toUpperCase().contains("Pixel_XL"))) {
            imageDeviceSuffix = imageDeviceSuffix + "Pixel_XL";
        } else if (ServerManager.DEVICE_NAME.toUpperCase().contains(" S6")) {
            imageDeviceSuffix = imageDeviceSuffix + "_S6";
        } else {
            System.out
                    .println("Device not recognised. Consider updating EmailApplicationHelpers.setImageDeviceSuffix()");
        }

        return imageDeviceSuffix;
    }

    /**
     * Waits for mail with 'subject' to be present in folder, on first position.
     * Has a retry mechanism implemented. At start, the app has to be in the
     * mentioned folder
     *
     * @param folder
     * @param subjects
     */
    public static void waitForEmail(Folder folder, String... subjects) {
        // Funtion has to be in folder to work ! ! !

        String folderName = "";
        String listOfSubjects = "";
        for (String subject : subjects) {
            listOfSubjects = String.format("%s, %s", subject, listOfSubjects);
        }

        switch (folder) {
            case POSTEINGANG:
                folderName = "Posteingang";
                break;
            case GESENDET:
                folderName = "Gesendet";
                break;
            case PAPIERKORB:
                folderName = "Papierkorb";
                break;
            case ENTWURFE:
                folderName = "Entwürfe";
                break;
            case SPAM:
                folderName = "Spam";
                break;
            default:
                fail("Not implemented foder in waitForEmail function: " + folder);
                break;
        }

        long startTime = System.currentTimeMillis();
        swipeDown();

        boolean found = false;
        boolean[] individualMailFound = new boolean[subjects.length];
        int TIMEOUT = 10;
        int retry = 1;
        do {
            try {
                for (int i = 0; i < subjects.length; i++) {
                    if (!individualMailFound[i]) {
                        Assert.assertTrue(subjects[i], true);
                        log.info("mail still not found");
                    }
                    individualMailFound[i] = true;
                }
                found = true;
                for (boolean individualFound : individualMailFound) {
                    found = found & individualFound;
                }
            } catch (Throwable e) {
                retry++;
                swipeDown();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    // do nothing
                }
            }
        } while ((retry <= TIMEOUT) && (!found));

        long endTime = System.currentTimeMillis();
        long waitTime = (endTime - startTime) / 1000;
        TOTALTIMEWAITINGFOREMAIL = TOTALTIMEWAITINGFOREMAIL + waitTime;

        System.out.println(String.format(
                "Waiting for emails %s in folder %s took %d seconds. Total wait time this run: %d seconds (i.e. %d minutes)",
                listOfSubjects, folder, (endTime - startTime) / 1000, TOTALTIMEWAITINGFOREMAIL,
                (TOTALTIMEWAITINGFOREMAIL / 60)));
        if (!found) {
            fail(String.format("One of emails  %s not found in %s.", listOfSubjects, folder));
        } else {
            // mail was found, now just wait for the Aktualisert message at the
            // bottom of the screen, so that the page is completely loaded.
            // iOsAssertExists(By.id(FOLDER_AKTUALISIERT_NAME),
            // "Folder not refreshed, since Aktualisiert message not found");
            // try {
            // Thread.sleep(1000);
            // } catch (InterruptedException e) {
            // // do nothing
            // }
        }
    }

    public static void send201Mails(String usermail) {
        try {
            for (int i = 0; i < 201; i++) {
                SmtpEmail.sendEmail("vtu.test1@ver.sul.t-online.de", "1234test", usermail, "Test" + i, "");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());// nothing
        }
    }

    public enum Folder {
        POSTEINGANG, GESENDET, PAPIERKORB, ENTWURFE, SPAM;
    }

}