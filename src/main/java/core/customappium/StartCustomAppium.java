package core.customappium;

import java.io.File;
import java.io.IOException;

public class StartCustomAppium {

    public static AppiumDriverLocalService startLocalAppiumServer() {
        verifyEnvVars();
        AppiumDriverLocalService server;
        CustomAppiumServer customServer = new CustomAppiumServer();
        LOG.info("Detected request to start appium server from code");
        server = buildService(customServer);
        LOG.info("-------------STARTING APPIUM SERVER------------");
        ExecutionTimer.start();
        server.start();
        ExecutionTimer.stop();
        LOG.info(String.format("-------------APPIUM SERVER STARTED after %s seconds------------",
                ExecutionTimer.exactDuration()));
        LOG.info(String.format(
                "Appium server running for device with UDID=%s at %s using bootstrap port=%s and chromedriverport=%s",
                ConfigHelper.getInstance().getCapability(CapabilitiesConstants.CAPAB_UDID),
                ConfigHelper.getInstance().getHubUrl(), customServer.getBootstrapPort(),
                customServer.getChromedriverPort()));
        return server;
    }

    private static AppiumDriverLocalService buildService(CustomAppiumServer customServer) {
        LOG.info("-------------SETTING UP APPIUM SERVER PARAMETERS------------");
        File serverLogs = new File(customServer.getLogfilePath());
        serverLogs.getParentFile().mkdirs();
        try {
            serverLogs.createNewFile();
        } catch (IOException ioex) {
            LOG.error(ioex.getMessage(), ioex);
        }
        final File NODE_EXE = new File(System.getenv("NODE_HOME") + OsUtils.getNodeExecutable());
        LOG.info("NODE EXE IS " + NODE_EXE.getAbsolutePath());
        final File APPIUM_JS = new File(
                System.getenv("APPIUM_HOME") + "build/lib" + OsUtils.getOsSepparator() + "main.js");
        LOG.info("APPIUM JS IS " + APPIUM_JS.getAbsolutePath());
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(NODE_EXE)
                .withAppiumJS(APPIUM_JS)
                .withLogFile(serverLogs).usingPort(Integer.parseInt(customServer.getPort()))
                .withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, customServer.getChromedriverPort())
                .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, customServer.getBootstrapPort())
                .withArgument(GeneralServerFlag.LOG_LEVEL, customServer.getLogLevel())
                .withIPAddress(customServer.getIp()).withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(IOSServerFlag.WEBKIT_DEBUG_PROXY_PORT, customServer.getIosWebkitDebugProxyPort())
                .withCapabilities(ConfigHelper.getInstance().getDesiredCapabilities()));
    }
}
