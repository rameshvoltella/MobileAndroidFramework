package api.apps.mail.folderlist;

import api.interfaces.Activity;
import core.MyLogger;
import core.UiObject;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;

import static core.managers.TestManager.mail;

public class Folderlist implements Activity {

    public FolderlistUiObjects folderlistUiObjects = new FolderlistUiObjects();

    public Folderlist findFolderDown(UiObject object) {
        try {
            MyLogger.log.info("Find trusted dialog folder down in page");
            object.findElementToClickOnItDownInPage();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("TrustedDialog folder could not be found");
        }
    }


    public Folderlist findFolderUp(UiObject object) {
        try {
            MyLogger.log.info("Find trusted dialog folder down in page");
            object.findElementToClickOnItUpInPage();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("TrustedDialog folder could not be found");
        }
    }

    public Folderlist enterInFolder(UiObject object) {
        try {
            MyLogger.log.info("Enter Inbox folder");
            if (object.getText().contains("Posteingang || Inbox")) {
                try {
                    object.tap();
                    mail.alerts.clickOkAdsDisclaimerInbox();
                } catch (WebDriverException e) {
                    MyLogger.log.info("Inbox disclaimer was not displayed" + e.getMessage());
                }
            } else {
                object.tap();
            }
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Inbox cannot be opened");
        }
    }

    @Override
    public Object waitToLoad() {
        return null;
    }
}
