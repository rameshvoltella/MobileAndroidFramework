package core;

import java.io.PrintWriter;

public class MultiThread implements Runnable {

    private Thread t;
    private String threadName;

    MultiThread(String name) {
        threadName = name;
        System.out.println("Creating " + threadName);
    }

    public void run() {
        System.out.println("Running " + threadName);
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
//            stdin.flush();
            stdin.close();
            p.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + threadName + " exiting.");
    }

    public void start() {
        MyLogger.log.info("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
            MyLogger.log.info(threadName + " Has started");
        } else {
//            try {
//                MyLogger.log.info("trying to interrupt the thread: " + threadName);
//                t.interrupt();
//            } catch (Throwable e1) {
//                e1.getMessage();
//                MyLogger.log.info("trying to interrupt the thread: " + threadName);
//            }
        }
    }
}

