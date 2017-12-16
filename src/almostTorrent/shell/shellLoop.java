package almostTorrent.shell;

import almostTorrent.peer.peerMain;
import almostTorrent.tracker.trackerMain;
import almostTorrent.utils.docUtils;

import static almostTorrent.utils.docUtils.printHelp;
import static almostTorrent.utils.ioUtils.ep;
import static almostTorrent.utils.ioUtils.mKbScanner;
import static almostTorrent.utils.otherUtils.*;

public class shellLoop {

    static String[] mParams;

    public static void startCliLoop() {
        ep("Entering almostTorrent master shell");
        docUtils.printHelp("masterShell");

        while (true) {
            System.out.print("% ");

            String[] params = mKbScanner.nextLine().toLowerCase().split(" ");

            // entropy++ force array to have at least 2 arguments
            if(params.length < 2){
                params = new String[]{params[0]," "};
            }

            mParams = params;

            switch (mParams[0]) {
                case "start":
                    switch(params[1]){
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
                    switch(params[1]){
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

    // CLI Shell function
    private static void peerShell(){
        boolean shellActive = true;
        startPeer();
        while (shellActive) {
            System.out.print("peer% ");
            String[] params = mKbScanner.nextLine().toLowerCase().split(" ");
            switch (params[0]) {
                case "ping":
                    peerMain.pingServer(params);
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

    // CLI Shell function
    private static void trackerShell(){
        boolean shellActive = true;
        while (shellActive) {
            System.out.print("tracker% ");

            switch (mKbScanner.nextLine().toLowerCase()) {
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

    private static void startPeer() {
        peerMain.main(mParams);
    }

    private static void stopPeer() {
        peerMain.stop();
    }

    private static void startTracker() {
        trackerMain.main(mParams);
    }

    private static void stopTracker() {
        trackerMain.stop();
    }

}
