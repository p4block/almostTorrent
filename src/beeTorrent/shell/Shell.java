package beeTorrent.shell;

import beeTorrent.roles.Peer;
import beeTorrent.roles.Tracker;
import beeTorrent.utils.docUtils;
import beeTorrent.utils.lifeCycleUtils;

import static beeTorrent.utils.docUtils.*;
import static beeTorrent.utils.ioUtils.*;
import static beeTorrent.utils.lifeCycleUtils.*;

public class Shell {

    private static String[] mParams;

    public static void mainLoop() {
        ep("Entering beeTorrent master shell");
        docUtils.printHelp("masterShell");

        while (true) {
            System.out.print("% ");

            String[] params = mKbScanner.nextLine().toLowerCase().split(" ");

            // entropy++ force array to have at least 3 arguments
            if (params.length < 2) {
                params = new String[]{params[0], " ",""};
            }

            mParams = params;

            switch (mParams[0]) {
                case "start":
                    switch (mParams[1]) {
                        case "peer":
                            startPeer();
                            break;
                        case "tracker":
                            startTracker();
                            break;
                        default:
                            docUtils.printHelp("start");
                    }
                    break;
                case "stop":
                    switch(mParams[1]){
                        case "peer":
                            stopPeer(0);
                            break;
                        case "tracker":
                            stopTracker(0);
                            break;
                        default:
                            docUtils.printHelp("stop");
                    }
                    break;
                case "shell":
                    switch (mParams[1]) {
                        case "peer":
                            peerShell(0);
                            break;
                        case "tracker":
                            trackerShell(0);
                            break;
                        default:
                            docUtils.printHelp("shell");
                    }
                    break;
                case "log":
                    switch (mParams[1]) {
                        case "peer":
                            ep(logRead(0));
                            break;
                        case "tracker":
                            ep(logRead(0));
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
    private static void peerShell(int index) {
        boolean shellActive = true;

        Peer peer = lifeCycleUtils.mPeerList.get(index);

        while (shellActive) {
            System.out.print("peer% ");
            String[] params = mKbScanner.nextLine().toLowerCase().split(" ");
            switch (params[0]) {
                case "ping":
                    peer.pingServer(params);
                    break;
                case "log":
                    logRead(peer);
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
    private static void trackerShell(int index) {
        boolean shellActive = true;

        Tracker tracker  = lifeCycleUtils.mTrackerList.get(index);

        while (shellActive) {
            System.out.print("tracker% ");

            switch (mKbScanner.nextLine().toLowerCase()) {
                case "log":
                    logRead(tracker);
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
