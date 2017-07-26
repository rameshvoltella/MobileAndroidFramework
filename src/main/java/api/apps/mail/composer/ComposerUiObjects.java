package api.apps.mail.composer;

import core.UiObject;
import core.UiSelector;

public class ComposerUiObjects {

    private static UiObject
            anField,
            subjectField,
            bodyField,
            emigPopUpPageTile,
            okBtnEmigPopUp,
            sendButton;

    public UiObject anField() {
        if (anField == null)
            anField = new UiSelector().resourceId("de.telekom.mail:id/fragment_content_message_compose_to_edittext").makeUiObject();
        return anField;
    }

    public UiObject subjectField() {
        if (subjectField == null)
            subjectField = new UiSelector().resourceId("de.telekom.mail:id/fragment_content_message_compose_subject_edittext").makeUiObject();
        return subjectField;
    }

    public UiObject bodyField() {
        if (bodyField == null)
            bodyField = new UiSelector().resourceId("de.telekom.mail:id/fragment_content_message_compose_body_edittext").makeUiObject();
        return bodyField;
    }

    public UiObject emigPopUpPageTile() {
        if (emigPopUpPageTile == null)
            emigPopUpPageTile = new UiSelector().text("Sichere Kommunikation").makeUiObject();
        return emigPopUpPageTile;
    }

    public UiObject okBtnEmigPopUp() {
        if (okBtnEmigPopUp == null)
            okBtnEmigPopUp = new UiSelector().resourceId("android:id/button1").makeUiObject();
        return okBtnEmigPopUp;
    }

    public UiObject sendButton() {
        if (sendButton == null)
            sendButton = new UiSelector().resourceId("de.telekom.mail:id/menu_send_mail").makeUiObject();
        return sendButton;
    }


}
