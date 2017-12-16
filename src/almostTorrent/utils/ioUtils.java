package almostTorrent.utils;

import almostTorrent.peer.peerMain;
import almostTorrent.tracker.trackerMain;

import java.util.Scanner;

public class ioUtils {

    // Add to log objects
    public static void logAdd(String who, String content){
        switch (who) {
            case "tracker":
                peerMain.mLog.add(content);
                break;
            case "peer":
                trackerMain.mLog.add(content);
                break;
        }
    }

    public static String logRead(String who){
        switch (who) {
            case "tracker":
                return peerMain.mLog.toString();
            case "peer":
                return trackerMain.mLog.toString();
        }
        return null; // Java is bad m'kay?
    }

    // Keyboard helper
    public static Scanner mKbScanner;
    public static void initializeHelpers(){
        mKbScanner = new Scanner(System.in);
    }

    // Print helpers
    public static void ep(String s){
        System.out.println(s);
    }

    public static void ep(int i){
        System.out.println(String.valueOf(i));
    }

}
