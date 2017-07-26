package api.apps.mail.folderlist;

import api.interfaces.Activity;
import core.MyLogger;
import org.openqa.selenium.NoSuchElementException;

public class Folderlist implements Activity {

    public FolderlistUiObjects folderlistUiObjects = new FolderlistUiObjects();

    public Folderlist findTrustedDialogFolder() {
        try {
            MyLogger.log.info("Find trusted dialog folder down in page");
            folderlistUiObjects.trustedDialogFolder().findElementToClickOnItDownInPage();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("TrustedDialog folder could not be found");
        }
    }

    public Folderlist findInboxFolder() {
        try {
            MyLogger.log.info("Find Inbox folder up in page");
            folderlistUiObjects.inboxFolder().findElementToClickOnItUpInPage();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Inbox folder could not be found");
        }
    }

    public Folderlist enterInboxFolder() {
        try {
            MyLogger.log.info("Enter Inbox folder");
            folderlistUiObjects.inboxFolder().tap();
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
