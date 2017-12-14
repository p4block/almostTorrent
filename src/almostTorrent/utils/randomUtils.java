package almostTorrent.utils;

import java.util.Scanner;

public class randomUtils {

    // Help helper
    public static void printHelp(String input){
        switch(input){
            case "jar":
                ep("java -jar randomUtils.jar -s/-c/-i");
                ep("-s server \n-c for client \n-i for interactive shell");
                break;
            case "shell":
                ep("");
                break;
        }
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
