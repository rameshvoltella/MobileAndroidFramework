package api.apps.mail.alerts;

import core.UiObject;
import core.UiSelector;

public class AlertsUiObjects {

    private static UiObject
            allowAccessContacts,
            dennyAccessContacts,
            okAdsDisclaimerInbox;

    public UiObject allowAccessContacts() {
        if (allowAccessContacts == null)
            allowAccessContacts = new UiSelector().resourceId("com.android.packageinstaller:id/permission_allow_button").makeUiObject();
        return allowAccessContacts;
    }

    public UiObject dennyAccessContacts() {
        if (dennyAccessContacts == null)
            dennyAccessContacts = new UiSelector().resourceId("com.android.packageinstaller:id/permission_deny_button").makeUiObject();
        return dennyAccessContacts;
    }

    public UiObject okAdsDisclaimerInbox() {
        if (okAdsDisclaimerInbox == null)
            okAdsDisclaimerInbox = new UiSelector().resourceId("android:id/button1").text("OK").makeUiObject();
        return okAdsDisclaimerInbox;
    }
}

