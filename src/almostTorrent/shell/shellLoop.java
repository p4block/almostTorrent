package almostTorrent.shell;

import almostTorrent.peer.peerMain;
import almostTorrent.utils.docUtils;
import almostTorrent.utils.lifeCycleUtils;

import static almostTorrent.utils.docUtils.*;
import static almostTorrent.utils.ioUtils.*;
import static almostTorrent.utils.lifeCycleUtils.*;

public class shellLoop {

    private static String[] mParams;

    public static void startCliLoop() {
        ep("Entering almostTorrent master shell");
        docUtils.printHelp("masterShell");

        while (true) {
            System.out.print("% ");

            String[] params = mKbScanner.nextLine().toLowerCase().split(" ");

            // entropy++ force array to have at least 2 arguments
            if (params.length < 2) {
                params = new String[]{params[0], " "};
            }

            mParams = params;

            switch (mParams[0]) {
                case "start":
                    switch (params[1]) {
                        case "peer":
                            startPeer(params);
                            break;
                        case "tracker":
                            startTracker(params);
                            break;
                        default:
                            docUtils.printHelp("start");
                    }
                    break;
                case "stop":
                    switch(params[1]){
                        case "peer":
                            stopPeer();
                            break;
                        case "tracker":
                            stopTracker();
                            break;
                        default:
                            docUtils.printHelp("stop");
                    }
                    break;
                case "shell":
                    switch (params[1]) {
                        case "peer":
                            peerShell();
                            break;
                        case "tracker":
                            trackerShell();
                            break;
                        default:
                            docUtils.printHelp("shell");
                    }
                    break;
                case "log":
                    switch (params[1]) {
                        case "peer":
                            ep(logRead("peer"));
                            break;
                        case "tracker":
                            ep(logRead("tracker"));
                            break;
                        default:
                            docUtils.printHelp("log");
                    }
                    break;
                case "exit":
                    exitMainLoop(0);
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

    // Peer CLI
    private static void peerShell() {
        boolean shellActive = true;
        startPeer(mParams);
        while (shellActive) {
            System.out.print("peer% ");
            String[] params = mKbScanner.nextLine().toLowerCase().split(" ");
            switch (params[0]) {
                case "ping":
                    peerMain.pingServer(params);
                    break;
                case "log":
                    logRead("peer");
                    break;
                case "exit":
                    shellActive = false;
                    break;
                case "help":
                    docUtils.printHelp("peerShell");
                    break;
                case "?":
                    docUtils.printHelp("peerShell");
                    break;
                default:
                    printHelp("peerShell");
            }

        }
    }

    // Tracker CLI
    private static void trackerShell() {
        boolean shellActive = true;
        startTracker(mParams);
        while (shellActive) {
            System.out.print("tracker% ");

            switch (mKbScanner.nextLine().toLowerCase()) {
                case "log":
                    logRead("tracker");
                    break;
                case "exit":
                    shellActive = false;
                    break;
                case "help":
                    docUtils.printHelp("trackerShell");
                    break;
                case "?":
                    docUtils.printHelp("trackerShell");
                    break;
                default:
                    printHelp("trackerShell");
            }

        }

        // Make sure that command output is not sent to current line
        System.out.print("\n");
    }



}
