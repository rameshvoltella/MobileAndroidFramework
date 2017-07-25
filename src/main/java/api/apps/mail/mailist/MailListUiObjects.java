package api.apps.mail.mailist;

import core.UiObject;
import core.UiSelector;

public class MailListUiObjects {

    private static UiObject
            composeBtn;

    public UiObject composeBtn() {
        if (composeBtn == null)
            composeBtn = new UiSelector().resourceId("de.telekom.mail:id/menu_new_mail").makeUiObject();
        return composeBtn;
    }

}
