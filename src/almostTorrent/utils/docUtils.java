package almostTorrent.utils;

import static almostTorrent.utils.ioUtils.*;

public class docUtils {

    // Help helper
    public static void printHelp(String input){
        switch(input){
            case "jar":
                ep("java -jar ioUtils.jar -t || -p || -i");
                ep("-t tracker mode \n-p peer mode \n-i interactive shell");
                break;
            case "masterShell":
                ep("Available commands:");
                ep("start (peer || tracker)");
                ep("shell (peer || tracker)");
                ep("exit");
                ep("help");
                break;
            case "start":
                ep("Invalid syntax's. Use 'start (peer || tracker)'");
                break;
            case "shell":
                ep("Invalid syntax's. Use 'shell (peer || tracker)'");
                break;
            case "peerShell":
                ep("Available commands:");
                ep("ping");
                ep("exit");
                ep("help");
                break;
            case "trackerShell":
                ep("Available commands:");
                ep("ping");
                ep("exit");
                ep("help");
                break;
        }
    }
}
