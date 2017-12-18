package beeTorrent.utils;

import beeTorrent.roles.Peer;
import beeTorrent.roles.Tracker;
import beeTorrent.roles.Bee;

import java.util.Scanner;

public class ioUtils {

    // Add to log objects
    public static void logAdd(Bee bee, String content) {
        bee.mLog.add(content);
    }

    public static String logRead(Bee bee) {
        return String.join("\n", bee.mLog);
    }

    public static String logRead(int i) {
        Bee bee = lifeCycleUtils.mPeerList.get(i);
        return String.join("\n", bee.mLog);
    }

    // Keyboard helper
    public static Scanner mKbScanner;

    public static void initializeHelpers() {
        mKbScanner = new Scanner(System.in);
    }

    // Print helpers
    public static void ep(String s) {
        System.out.println(s);
    }

    public static void ep(int i) {
        System.out.println(String.valueOf(i));
    }

    // Select Ports
    public static int selectPort() {
        int mPort = 6969;
        ep("Introduce port to start the bee");
        System.out.print("Port: [Default = 6969] ");
        String input = mKbScanner.nextLine();
        if (input.isEmpty()) {
            return mPort;
        }
        int port = Integer.parseInt(input);
        if (port > 0 & port < 65535) {
            mPort = port;
            ep("Setting port value to " + port);
            return mPort;
        } else {
            ep("Setting port value to default " + port);
            return mPort;
        }

    }

}
