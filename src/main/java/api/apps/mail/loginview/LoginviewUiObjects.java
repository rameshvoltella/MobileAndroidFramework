package api.apps.mail.loginview;

import core.UiObject;
import core.UiSelector;

public class LoginviewUiObjects {

    private static UiObject
            accountpickertitle,
            tonlineLogin,
            gmailLogin,
            thirdPartyAccount,
            okBtnAccountPicker,
            exitingaccount,
            okBtnSelectGmail,
            gmailloginheaderpage,
            userNameField,
            loginPasswordField,
            loginButton;

    public UiObject okBtnAccountPicker() {
        if (okBtnAccountPicker == null)
            okBtnAccountPicker = new UiSelector().resourceId("de.telekom.mail:id/tp_account_picker_btn_ok").makeUiObject();
        return okBtnAccountPicker;
    }

    public UiObject accountpickertitle() {
        if (accountpickertitle == null)
            accountpickertitle = new UiSelector().resourceId("de.telekom.mail:id/textViewHeadline").makeUiObject();
        return accountpickertitle;
    }

    public UiObject tonlineLogin() {
        if (tonlineLogin == null)
            tonlineLogin = new UiSelector().resourceId("de.telekom.mail:id/tp_account_type_picker_telekom_account").makeUiObject();
        return tonlineLogin;
    }

    public UiObject gmailLogin() {
        if (gmailLogin == null)
            gmailLogin = new UiSelector().resourceId("de.telekom.mail:id/tp_account_type_picker_gmail_account").makeUiObject();
        return gmailLogin;
    }

    public UiObject thirdPartyAccount() {
        if (gmailLogin == null)
            gmailLogin = new UiSelector().resourceId("de.telekom.mail:id/tp_account_type_picker_gmail_account").makeUiObject();
        return gmailLogin;
    }

    public UiObject exitingaccount() {
        if (exitingaccount == null)
            exitingaccount = new UiSelector().resourceId("android:id/text1").index(0).makeUiObject();
        return exitingaccount;
    }

    public UiObject okBtnSelectGmail() {
        if (okBtnSelectGmail == null)
            okBtnSelectGmail = new UiSelector().resourceId("android:id/button1").makeUiObject();
        return okBtnSelectGmail;
    }

    public UiObject gmailloginheaderpage() {
        if (gmailloginheaderpage == null)
            gmailloginheaderpage = new UiSelector().resourceId("com.google.android.gms:id/title").makeUiObject();
        return gmailloginheaderpage;
    }

    public UiObject userNameField() {
        if (userNameField == null)
            userNameField = new UiSelector().resourceId("de.telekom.mail:id/editTextUsername").makeUiObject();
        return userNameField;
    }

    public UiObject loginPasswordField() {
        if (loginPasswordField == null)
            loginPasswordField = new UiSelector().resourceId("de.telekom.mail:id/editTextPassword").makeUiObject();
        return loginPasswordField;
    }

    public UiObject loginButton() {
        if (loginButton == null)
            loginButton = new UiSelector().resourceId("de.telekom.mail:id/buttonLogin").makeUiObject();
        return loginButton;
    }
}
