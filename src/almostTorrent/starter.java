package almostTorrent;

import almostTorrent.client.clientMain;
import almostTorrent.server.serverMain;
import almostTorrent.utils.randomUtils;

import static almostTorrent.utils.randomUtils.*;

public class starter {

    public static void main(String[] args) {

        // Start all
        randomUtils.initializeHelpers();

        ep("\n=== almostTorrent CLI launcher ===");

        for (String i : args) {
            switch (i) {
                case "-c":
                    clientMain.main(args);
                    break;
                case "-s":
                    serverMain.main(args);
                    break;
                case "-i":
                    startCliLoop();
                    break;
                default:
                    randomUtils.printHelp("jar");
                    break;
            }
        }
    }

    private static void startCliLoop() {
        ep("Entering almostTorrent master shell");

        while (true) {
            switch (mKbScanner.nextLine().toLowerCase()) {
                case "help":
                    randomUtils.printHelp("shell");
                    break;
                case "?":
                    randomUtils.printHelp("shell");
                    break;
//                case "tracker":
//                    if (mKbScanner.nextLine().equals("set")) {
//                        ep("Set tracker now");
//                        break;
//                    } else if (mKbScanner.nextLine().equals("get")) {
//                        ep("Get tracker");
//                        break;
//                    } else {
//                        ep("Invalid command. Use help or ?.");
//                        break;
//                    }
                case "tracker set":
                    ep("Set tracker");
//                default:
//                    printHelp("shell");
            }
        }

    }
}
