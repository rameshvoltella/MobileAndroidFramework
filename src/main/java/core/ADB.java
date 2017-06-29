package core;

import core.managers.ServerManager;

import java.util.ArrayList;

/**
 * Created by lumihai on 6/29/2017.
 */
public class ADB {

    private String ID;

    public ADB(String deviceID) {
        ID = deviceID;
    }

    public static String command(String command) {

        if (command.startsWith("adb"))
            command = command.replace("adb", ServerManager.getAndroidHome() + "/platform-tools/adb ");
        else throw new RuntimeException("this method is desingned to run ADB commands");
        String output = ServerManager.runCommand(command);
        if (output == null) return "";
        else return output.trim();
    }

    public static void killServer() {
        command("adb kill-server");
    }

    public static void startServer() {
        command("adb start-server");
    }

    public static ArrayList getConnectedDevices() {
        ArrayList devices = new ArrayList();
        String output = command("adb devices");
        for (String line : output.split("\n")) {
            line = line.trim();
            if (line.endsWith("devices")) devices.add(line.replace("device", "").trim());
        }
        return devices;
    }

    public String getForegroundActivity() {
        return command("adb-s" + ID + "shell dumpsys window window | grep mCurrentFocus");
    }

    public String getAndroidVersionString() {
        String output = command("adb -s" + ID + "shell getprop ro.build.version.release");
        if (output.length() == 3) output += ".0";
        return output;
    }

    public int getAndroidVersion() {
        return Integer.parseInt(getAndroidVersionString().replaceAll("\\.", ""));
    }

    public ArrayList getInstalledPackages() {
        ArrayList packages = new ArrayList();
        String[] output = command("adb -s" + ID + "shell pm list packages").split("\n");
        for (String packageID : output) packages.add(packageID.replace("package:", "").trim());
        return packages;
    }

    public void openAppsActivity(String packageID, String activityID) {
        command("adb -s" + ID + "shell am start -c api.android.intent.category.LAUNCHER -a api.android.intent.action.MAIN -n" + packageID + "/" + activityID);
    }

    public void clearAppsData(String packageID) {
        command("adb -s" + ID + "shell pm clear" + packageID);
    }

    public void forceStopApp(String packageID) {
        command("adb -s" + ID + "shell am force-stop" + packageID);
    }

    public void installApp(String apkPath) {
        command("adb -s" + ID + "install" + apkPath);
    }

    public void uninstallApp(String packageID) {
        command("adb -s" + ID + "uninstall" + packageID);
    }

    public void clearLogBuffer() {
        command("adb -s" + ID + "shell logcat -c");
    }

    public void pushFile(String source, String target) {
        command("adb -s" + ID + "push " + source + " " + target);
    }

    public void pullFile(String source, String target) {
        command("adb -s" + ID + "pull " + source + " " + target);
    }

    public void deleteFile(String target) {
        command("adb -s" + ID + "shell rm " + target);
    }

    public void moveFile(String source, String target) {
        command("adb -s" + ID + "shell mv " + source + " " + target);
    }

    public void takeScreenshot(String target) {
        command("adb -s" + ID + "shell screencap" + target);
    }

    public void rebootDevice() {
        command("adb -s" + ID + "reboot");
    }

    public String getDeviceModel() {
        return command("adb -s" + ID + "shell getprop ro.product.model");
    }

    public String getDeviceSerial() {
        return command("adb -s" + ID + "shell getprop ro.serialno");
    }

    public String getDeviceCarier() {
        return command("adb -s" + ID + "shell getprop gsm.operator.alpha");
    }
}
