package core;

import java.io.PrintWriter;

public class CommandLine {

    private String ID;

    public CommandLine(String deviceID) {
        ID = deviceID;
    }

    public static void main(String[] args) throws Exception {
        String[] command =
                {
                        "cmd",
                };
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);

            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("cd D:\\AndroidStudioSDK\\emulator && emulator -avd Pixel_XL_API_26");
//            stdin.println("C: && cd C:\\Users\\lumihai\\Desktop && dir && pixelXL.bat");
            stdin.flush();
            stdin.close();
            p.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
