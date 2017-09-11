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

    public static String getPlatformAndroid() throws IOException, ParseException {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src\\test\\resources\\LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("platformName");
            return name;
        } catch (Throwable t) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("platformName");
            return name;
        }
    }

    public static String getPlatformVersionFromJason() throws IOException, ParseException {
        MyLogger.log.info("Get Platform Version From Jason");
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src\\test\\resources\\PlatfomVersion.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("platformName");
            MyLogger.log.info("Platform Version From Jason is: " + name);
            return name;

        } catch (Throwable t) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/PlatfomVersion.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("platformName");
            MyLogger.log.info("Platform Version From Jason is: " + name);
            return name;
        }
    }

    public static String getPlatformIos() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(
                "src/test/resources/localJsonIOS.json"));
        JSONObject jsonObject = (JSONObject) obj;
        String name = (String) jsonObject.get("platformName");
        return name;
    }


    public static boolean isAndroid() throws IOException, ParseException {
        return getPlatformAndroid().equals("android");
    }

    public static boolean isAndroidFromJason() throws IOException, ParseException {
        return getPlatformVersionFromJason().equals("android");
    }

    public static boolean isIos() throws IOException, ParseException {
        return getPlatformIos().equals("ios");
    }

    public static boolean isIosFromJason() throws IOException, ParseException {
        return getPlatformVersionFromJason().equals("ios");
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

    public static String getDeviceId() throws Exception {
        if (isWindows() && isAndroidFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src\\test\\resources\\LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("DeviceID");
            return name;
        } else if (isMac() && isAndroidFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("DeviceID");
            return name;
        } else if (isMac() && isIosFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/localJsonIOS.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("DeviceID");
            return name;
        } else {
            MyLogger.log.info("Environment is other than Windows and Mac. Please revise getOS method");
            throw new Exception("Setup is ran on other environment; no Windows or Mac could be identified");

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


    public static String getIP() throws Exception {
        if (isWindows() && isAndroidFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src\\test\\resources\\LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("ip");
            return name;
        } else if (isMac() && isAndroidFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("ip");
            return name;
        } else if (isMac() && isIosFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/localJsonIOS.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("ip");
            return name;
        } else {
            MyLogger.log.info("Environment is other than Windows and Mac. Please revise getOS method");
            throw new Exception("Setup is ran on other environment; no Windows or Mac could be identified");
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

    public static String getAppLocation() throws Exception {
        if (isWindows() && isAndroidFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src\\test\\resources\\LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("appLocation");
            return name;
        } else if (isMac() && isAndroidFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("appLocation");
            return name;
        } else if (isMac() && isIosFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/localJsonIOS.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("appLocation");
            return name;
        } else {
            MyLogger.log.info("Environment is other than Windows and Mac. Please revise getOS method");
            throw new Exception("Setup is ran on other environment; no Windows/Mac or android/ios could be identified");
        }
    }

    public static String getAutomationName() throws Exception {
        if (isWindows() && isAndroidFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src\\test\\resources\\LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("automationName");
            return name;
        } else if (isMac() && isAndroidFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("automationName");
            return name;
        } else if (isMac() && isIosFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/localJsonIOS.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("automationName");
            return name;
        } else {
            MyLogger.log.info("Environment is other than Windows and Mac. Please revise getOS method");
            throw new Exception("Setup is ran on other environment; no Windows/Mac or android/ios could be identified");
        }
    }

    public static String getPlatformName() throws Exception {
        if (isWindows() && isAndroidFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src\\test\\resources\\LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("platformName");
            return name;
        } else if (isMac() && isAndroidFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/LocalJsonAndroid.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("platformName");
            return name;
        } else if (isMac() && isIosFromJason()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(
                    "src/test/resources/localJsonIOS.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("platformName");
            return name;
        } else {
            MyLogger.log.info("Environment is other than Windows and Mac. Please revise getOS method");
            throw new Exception("Setup is ran on other environment; no Windows/Mac or android/ios could be identified");
        }
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