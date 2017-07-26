package api.apps.mail.folderlist;

import core.UiObject;
import core.UiSelector;

public class FolderlistUiObjects {

    private static UiObject
            trustedDialogFolder,
            inboxFolder;

    public UiObject trustedDialogFolder() {
        if (trustedDialogFolder == null)
            trustedDialogFolder = new UiSelector().text("Trusted Dialog").makeUiObject();
        return trustedDialogFolder;
    }

    public UiObject inboxFolder() {
        if (inboxFolder == null)
            inboxFolder = new UiSelector().text("Posteingang").makeUiObject();
        return inboxFolder;
    }

}
