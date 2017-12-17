package beeTorrent;

import beeTorrent.shell.Shell;
import beeTorrent.utils.*;

import static beeTorrent.utils.ioUtils.*;
import static beeTorrent.utils.lifeCycleUtils.*;

public class Starter {

    private static String[] mArgs;

    public static void main(String[] args) {
        mArgs = args;

        initializeStarter();
    }

    private static void initializeStarter() {
        // Start all
        ioUtils.initializeHelpers();

        ep("\n=== beeTorrent CLI launcher ===");

        if (mArgs.length > 0) {
            switch (mArgs[0]) {
                case "-p":
                    startPeer();
                    break;
                case "-t":
                    startTracker();
                    break;
                case "-i":
                    Shell.mainLoop();
                    break;
                default:
                    docUtils.printHelp("jar");
            }
        } else {
            docUtils.printHelp("jar");
        }

    }
}
