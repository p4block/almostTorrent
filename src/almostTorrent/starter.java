package almostTorrent;

import almostTorrent.peer.peerMain;
import almostTorrent.tracker.trackerMain;

import almostTorrent.utils.*;
import static almostTorrent.utils.ioUtils.*;
import static almostTorrent.utils.otherUtils.*;
import static almostTorrent.utils.docUtils.*;

public class starter {

    private static String[] mArgs;

    public static void main(String[] args) {
        mArgs = args;

        initializeStarter();
    }

    private static void initializeStarter(){
        // Start all
        ioUtils.initializeHelpers();

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
                    docUtils.printHelp("jar");
            }
        }

        // We only reach this if bad options were given
        docUtils.printHelp("jar");

    }

    private static void startCliLoop() {
        ep("Entering almostTorrent master shell");
        docUtils.printHelp("masterShell");

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
                    exitSoftware(0);
                    break;
                case "help":
                    docUtils.printHelp("masterShell");
                    break;
                case "?":
                    docUtils.printHelp("masterShell");
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
