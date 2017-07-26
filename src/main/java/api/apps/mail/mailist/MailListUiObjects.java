package api.apps.mail.mailist;

import core.UiObject;
import core.UiSelector;

public class MailListUiObjects {

    private static UiObject
            composeBtn,
            backBtnMailList;

    public UiObject composeBtn() {
        if (composeBtn == null)
            composeBtn = new UiSelector().resourceId("de.telekom.mail:id/menu_new_mail").makeUiObject();
        return composeBtn;
    }

    public UiObject backBtnMailList() {
        if (backBtnMailList == null)
            backBtnMailList = new UiSelector().className("android.widget.ImageButton").index(0).makeUiObject();
        return backBtnMailList;
    }

}
