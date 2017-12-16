package almostTorrent;

import almostTorrent.peer.peerMain;
import almostTorrent.shell.shellLoop;
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

    private static void initializeStarter() {
        // Start all
        ioUtils.initializeHelpers();

        ep("\n=== almostTorrent CLI launcher ===");

        if (mArgs.length > 0) {
            switch (mArgs[0]) {
                case "-p":
                    peerMain.main(mArgs);
                    break;
                case "-t":
                    trackerMain.main(mArgs);
                    break;
                case "-i":
                    shellLoop.startCliLoop();
                    break;
                default:
                    docUtils.printHelp("jar");
            }
        } else {
            docUtils.printHelp("jar");
        }

    }
}
