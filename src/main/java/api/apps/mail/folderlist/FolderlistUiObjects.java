package api.apps.mail.folderlist;

import core.UiObject;
import core.UiSelector;

public class FolderlistUiObjects {

    private static UiObject
            trustedDialogFolder;

    public UiObject trustedDialogFolder() {
        if (trustedDialogFolder == null)
            trustedDialogFolder = new UiSelector().text("Trusted Dialog").makeUiObject();
        return trustedDialogFolder;
    }
}
