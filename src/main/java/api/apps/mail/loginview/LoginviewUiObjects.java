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
            loginButton,
            signInGmailHeader,
            gmailUsernameInput,
            gmailLoginNextButton,
            gmailAccountProfileIdentifier,
            gmailPasswordInput,
            gmailLoginPasswordNextButton,
            singInAgreeGmail,
            allowGmailnBackup,
            allowGmailNotification,
            chooseGmailAccount,
            selectAlreadyLoggedInGmailAccount,
            addNewGmailAccount,
            userNameField3rdParty,
            loginPasswordField3rdParty,
            loginButton3rdParty;

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
            gmailLogin = new UiSelector().resourceId("de.telekom.mail:id/tp_account_type_picker_third_party_account").makeUiObject();
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

    public UiObject userNameField() {
        if (userNameField == null)
            userNameField = new UiSelector().resourceId("de.telekom.mail:id/editTextUsername").makeUiObject();
        return userNameField;
    }

    public UiObject userNameField3rdParty() {
        if (userNameField3rdParty == null)
            userNameField3rdParty = new UiSelector().resourceId("de.telekom.mail:id/tp_create_edit_username").makeUiObject();
        return userNameField3rdParty;
    }

    public UiObject loginPasswordField() {
        if (loginPasswordField == null)
            loginPasswordField = new UiSelector().resourceId("de.telekom.mail:id/editTextPassword").makeUiObject();
        return loginPasswordField;
    }

    public UiObject loginPasswordField3rdParty() {
        if (loginPasswordField3rdParty == null)
            loginPasswordField3rdParty = new UiSelector().resourceId("de.telekom.mail:id/tp_create_edit_password").makeUiObject();
        return loginPasswordField3rdParty;
    }

    public UiObject loginButton() {
        if (loginButton == null)
            loginButton = new UiSelector().resourceId("de.telekom.mail:id/buttonLogin").makeUiObject();
        return loginButton;
    }

    public UiObject loginButton3rdParty() {
        if (loginButton3rdParty == null)
            loginButton3rdParty = new UiSelector().resourceId("de.telekom.mail:id/tp_create_btn_forward").makeUiObject();
        return loginButton3rdParty;
    }

    public UiObject signInGmailHeader() {
        if (signInGmailHeader == null)
            signInGmailHeader = new UiSelector().resourceId("headingText").index(0).makeUiObject();
        return signInGmailHeader;
    }

    public UiObject gmailUsernameInput() {
        if (gmailUsernameInput == null)
            gmailUsernameInput = new UiSelector().resourceId("identifierId").text("Email or phone").makeUiObject();
        return gmailUsernameInput;
    }

    public UiObject gmailLoginUsernameNextButton() {
        if (gmailLoginNextButton == null)
            gmailLoginNextButton = new UiSelector().resourceId("identifierNext").index(6).makeUiObject();
        return gmailLoginNextButton;
    }

    public UiObject gmailLoginPasswordNextButton() {
        if (gmailLoginPasswordNextButton == null)
            gmailLoginPasswordNextButton = new UiSelector().resourceId("passwordNext").index(3).makeUiObject();
        return gmailLoginPasswordNextButton;
    }

    public UiObject gmailAccountProfileIdentifier() {
        if (gmailAccountProfileIdentifier == null)
            gmailAccountProfileIdentifier = new UiSelector().resourceId("headingText").index(0).makeUiObject();
        return gmailAccountProfileIdentifier;
    }

    public UiObject gmailPasswordInput() {
        if (gmailPasswordInput == null)
            gmailPasswordInput = new UiSelector().text("Enter your password").index(0).makeUiObject();
        return gmailPasswordInput;
    }

    public UiObject singInAgreeGmail() {
        if (singInAgreeGmail == null)
            singInAgreeGmail = new UiSelector().resourceId("signinconsentNext").index(3).makeUiObject();
        return singInAgreeGmail;
    }

    public UiObject allowGmailnBackup() {
        if (allowGmailnBackup == null)
            allowGmailnBackup = new UiSelector().resourceId("com.google.android.gms:id/google_services_next_button_item").text("NEXT").makeUiObject();
        return allowGmailnBackup;
    }

    public UiObject allowGmailNotification() {
        if (allowGmailNotification == null)
            allowGmailNotification = new UiSelector().resourceId("com.google.android.gms:id/accept_button").text("ALLOW").makeUiObject();
        return allowGmailNotification;
    }

    public UiObject chooseGmailAccount() {
        if (chooseGmailAccount == null)
            chooseGmailAccount = new UiSelector().text("Choose an account").makeUiObject();
        return chooseGmailAccount;
    }

    public UiObject selectAlreadyLoggedInGmailAccount(int index) {
        if (selectAlreadyLoggedInGmailAccount == null)
            selectAlreadyLoggedInGmailAccount = new UiSelector().resourceId("android:id/text1").index(index).makeUiObject();
        return selectAlreadyLoggedInGmailAccount;
    }

    public UiObject addNewGmailAccount() {
        if (addNewGmailAccount == null)
            addNewGmailAccount = new UiSelector().resourceId("android:id/text1").index(1).makeUiObject();
        return addNewGmailAccount;
    }


}
