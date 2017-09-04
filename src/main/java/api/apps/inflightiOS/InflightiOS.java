package api.apps.inflightiOS;

import api.apps.inflightiOS.firsttimeview.FirstTimeView;
import api.interfaces.Application;

public class InflightiOS implements Application {

    public FirstTimeView firsttimeview = new FirstTimeView();

    @Override
    public void forceStop() {

    }

    @Override
    public void clearData() {

    }

    @Override
    public Object open() {
        return null;
    }

    @Override
    public String packageID() {
        return null;
    }

    @Override
    public String activityID() {
        return null;
    }
}
