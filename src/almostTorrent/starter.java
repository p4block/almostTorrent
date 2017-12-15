package almostTorrent;

import almostTorrent.peer.peerMain;
import almostTorrent.tracker.trackerMain;
import almostTorrent.utils.randomUtils;

import static almostTorrent.utils.randomUtils.*;

public class starter {

    private static String[] mArgs;

    public static void main(String[] args) {
        mArgs = args;

        initializeStarter();
    }

    private static void initializeStarter(){
        // Start all
        randomUtils.initializeHelpers();

        ep("\n=== almostTorrent CLI launcher ===");

        for (String i : mArgs) {
            switch (i) {
                case "-p":
                    startPeer();
                    break;
                case "-t":
                    startTracker();
                    break;
                case "-i":
                    startCliLoop();
                    break;
                default:
                    randomUtils.printHelp("jar");
            }
        }

        // We only reach this if bad options were given
        randomUtils.printHelp("jar");

    }

    private static void startCliLoop() {
        ep("Entering almostTorrent master shell");
        randomUtils.printHelp("masterShell");

        while (true) {
            System.out.print("\n % ");

            switch (mKbScanner.nextLine().toLowerCase()) {
                case "start peer":
                    startPeer();
                    break;
                case "start tracker":
                    startTracker();
                    break;
                case "exit":
                    System.exit(0);
                case "help":
                    randomUtils.printHelp("masterShell");
                    break;
                case "?":
                    randomUtils.printHelp("masterShell");
                    break;
                default:
                    printHelp("masterShell");
            }

        }

    }

    private static void startPeer(){
        peerMain.main(mArgs);
    }

    private static void startTracker(){
        trackerMain.main(mArgs);
    }
}
