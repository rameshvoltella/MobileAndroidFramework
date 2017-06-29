package core.managers;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by maiky on 6/29/2017.
 */
public class ServerManager {

    private static String OS;
    private static String ANDROID_HOME;

    public static String getAndroidHome() {
        if (ANDROID_HOME == null) {
            ANDROID_HOME = System.getenv("ANDROID_HOME");
            if (ANDROID_HOME == null) throw new RuntimeException("Failed to find Android home; make sure to set it");
        }
        return ANDROID_HOME;
    }

    public static String getOs() {
        if (OS == null) OS = System.getenv("os.name");
        return OS;
    }

    public static boolean isWindows() {
        return getOs().startsWith("Windows");
    }

    public static boolean isMac() {
        return getOs().startsWith("Mac");
    }

    public static String runCommand(String command) {
        String output = null;
        try {
            Scanner scanner = new Scanner(Runtime.getRuntime().exec(command).getInputStream()).useDelimiter("\\A");
            if (scanner.hasNext()) output = scanner.next();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return output;
    }
}
