package almostTorrent.utils;

import java.util.Scanner;

public class randomUtils {

    // Help helper
    public static void printHelp(String input){
        switch(input){
            case "jar":
                ep("java -jar randomUtils.jar -t || -p || -i");
                ep("-t tracker mode \n-p peer mode \n-i interactive shell");
                break;
            case "masterShell":
                ep("Available commands:");
                ep("start (peer || tracker)");
                ep("exit");
                ep("help");

//                ep("--> tracker set <IP>:<Port>");
//                ep("--> tracker get");
//                ep("--> share file <Path>");
//                ep("--> share dir <Path>");
//                ep("--> show peers");
//                ep("--> mode set <Mode>");
//                ep("--> mode get");
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
