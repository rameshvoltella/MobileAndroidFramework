package core.managers;

import core.MyLogger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Scanner;


public class ServerManager {

    public static final String DEVICE_NAME = getDeviceName();
    public static final String OS_VERSION = getOsVersion();
    private static String OS;
    private static String ANDROID_HOME;
    private static String sepparator = null;
    private static String nodeExecutable = null;

    private static String getOsVersion() {
        String value = System.getenv("OS_VERSION");
        value = "8.0";
        return value;
    }

    private static String getDeviceName() {
        String value = System.getenv("DEVICE_NAME");
        value = "Pixel_XL";
        return value;
    }

    public static String getAndroidHome() {
        if (ANDROID_HOME == null) {
            ANDROID_HOME = System.getenv("ANDROID_HOME");
            if (ANDROID_HOME == null)
                throw new RuntimeException("Failed to find ANDROID_HOME, make sure the environment variable is set");
        }
        return ANDROID_HOME;
    }

    public static String getOS() {
//        if (OS == null) OS = System.getenv("os.name");
        MyLogger.log.info("Running command to see if it is Windows or Mac environment");
        if (OS == null) OS = System.getProperty("os.name");
        MyLogger.log.info("Detected OS is: " + OS);
        return OS;
    }

    public static boolean isWindows() {
        return getOS().contains("Windows");
    }

    public static boolean isMac() {
        return getOS().contains("Mac");
    }

    public static String getOsSepparator() {
        if (isWindows()) {
            sepparator = "\\";
        }
        if (isMac()) {
            sepparator = "/";
        }
        return sepparator;
    }

    public static String getNodeExecutable() {
        if (isWindows()) {
            nodeExecutable = "node.exe";
        }
        if (isMac()) {
            nodeExecutable = "node";
        }
        return nodeExecutable;
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

    public static String getWorkingDir() {
        return System.getProperty("user.dir");
    }

    public static String read(File file) {
        StringBuilder output = new StringBuilder();
        try {
            String line;
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) output.append(line + "\n");
            bufferedReader.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
        return output.toString();
    }

    public static void write(File file, String content) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))) {
            writer.write(content);
            writer.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public static String getDeviceId() throws IOException, ParseException {
        if (isWindows()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src\\test\\resources\\LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("DeviceID");
            return name;
        } else {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/localJsonIOS.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("DeviceID");
            return name;
        }

    }

    public static String getCustomURL() throws IOException, ParseException {
        if (isWindows()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src\\test\\resources\\LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("AppiumURL");
            return name;
        } else {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/localJsonIOS.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("AppiumURL");
            return name;
        }

    }


    public static String getIP() throws IOException, ParseException {
        if (isWindows()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src\\test\\resources\\LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("ip");
            return name;
        } else {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/localJsonIOS.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("ip");
            return name;
        }

    }

    public static String getBootstrap() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(
                "src\\test\\resources\\LocalJsonAndroid.json"));
        JSONObject jsonObject = (JSONObject) obj;
        String name = (String) jsonObject.get("BootstratPort");
        return name;

    }

    public static String getChromedriver() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(
                "src\\test\\resources\\LocalJsonAndroid.json"));
        JSONObject jsonObject = (JSONObject) obj;
        String name = (String) jsonObject.get("ChromePort");
        return name;

    }

    public static String getPort() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(
                "src\\test\\resources\\LocalJsonAndroid.json"));
        JSONObject jsonObject = (JSONObject) obj;
        String name = (String) jsonObject.get("port");
        return name;

    }

    public static String getIosWebKit() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(
                "src\\test\\resources\\LocalJsonAndroid.json"));
        JSONObject jsonObject = (JSONObject) obj;
        String name = (String) jsonObject.get("IosWebKit");
        return name;

    }

    public static String getAppLocation() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(
                "src\\test\\resources\\LocalJsonAndroid.json"));
        JSONObject jsonObject = (JSONObject) obj;
        String name = (String) jsonObject.get("appLocation");
        return name;

    }

    public static String getAutomationName() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(
                "src\\test\\resources\\LocalJsonAndroid.json"));
        JSONObject jsonObject = (JSONObject) obj;
        String name = (String) jsonObject.get("automationName");
        return name;

    }

    public static String getPlatformName() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(
                "src\\test\\resources\\LocalJsonAndroid.json"));
        JSONObject jsonObject = (JSONObject) obj;
        String name = (String) jsonObject.get("platformName");
        return name;

    }

    public static String getDeviceNameJason() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(
                "src\\test\\resources\\LocalJsonAndroid.json"));
        JSONObject jsonObject = (JSONObject) obj;
        String name = (String) jsonObject.get("deviceName");
        return name;

    }
}