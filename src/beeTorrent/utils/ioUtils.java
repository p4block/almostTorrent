package beeTorrent.utils;

import beeTorrent.roles.Peer;
import beeTorrent.roles.Tracker;
import beeTorrent.roles.Bee;

import java.util.Scanner;

public class ioUtils {

    // Add to log objects
    public static void logAdd(Bee bee, String content){
        bee.mLog.add(content);
    }

    public static String logRead(Bee bee){
        return String.join("\n", bee.mLog);
    }

    public static String logRead(int i){
        Bee bee = lifeCycleUtils.mPeerList.get(i);
        return String.join("\n", bee.mLog);
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
