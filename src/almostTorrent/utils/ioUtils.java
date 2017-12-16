package almostTorrent.utils;

import almostTorrent.peer.peerMain;
import almostTorrent.tracker.trackerMain;

import java.util.Scanner;

public class ioUtils {

    // Add to log objects
    public static void logAdd(String who, String content){
        switch (who) {
            case "peer":
                peerMain.mLog.add(content);
                break;
            case "tracker":
                trackerMain.mLog.add(content);
                break;
        }
    }

    public static String logRead(String who){
        switch (who) {
            case "peer":
                return String.join("\n", peerMain.mLog);
            case "tracker":
                return String.join("\n",trackerMain.mLog);
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
