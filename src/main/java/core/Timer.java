package core;

import java.util.Date;

/**
 * Created by maiky on 6/29/2017.
 */
public class Timer {

    public long startStamp;

    public static long getTimeStamp() {
        return new Date().getTime();
    }

    public void start() {
        startStamp = getTimeStamp();
    }

    public boolean expired(int seoonds) {
        int difference = (int) ((getTimeStamp() - startStamp) / 1000);
        return difference > seoonds;
    }
}
