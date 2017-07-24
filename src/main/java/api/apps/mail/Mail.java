package api.apps.mail;

import api.android.Android;
import api.apps.mail.loginview.Loginview;
import api.interfaces.Application;

public class Mail implements Application {

    public Loginview loginview = new Loginview();


    @Override
    public void forceStop() {
        Android.adb.forceStopApp(packageID());
    }

    @Override
    public void clearData() {
        Android.adb.clearAppsData(packageID());
    }

    @Override
    public Object open() {
        Android.adb.openAppsActivity(packageID(), activityID());
        return null;
    }

    @Override
    public String packageID() {
        return "de.telekom.mail";
    }

    @Override
    public String activityID() {
        return "de.telekom.mail.emma.activities.SplashScreenActivity";
    }
}