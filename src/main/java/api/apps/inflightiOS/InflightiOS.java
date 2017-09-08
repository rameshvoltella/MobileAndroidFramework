package api.apps.inflightiOS;

import api.apps.inflightiOS.firsttimeview.FirstTimeView;
import api.apps.inflightiOS.loginview.LoginView;
import api.apps.inflightiOS.mainmenu.MainMenu;
import api.apps.inflightiOS.pincode.PinCode;
import api.apps.inflightiOS.workarounds.Workarounds;
import api.interfaces.Application;

public class InflightiOS implements Application {

    public FirstTimeView firsttimeview = new FirstTimeView();
    public PinCode pinCode = new PinCode();
    public MainMenu mainMenu = new MainMenu();
    public Workarounds workarounds = new Workarounds();
    public LoginView loginView = new LoginView();

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
