package almostTorrent.shell;

import almostTorrent.peer.peerMain;
import almostTorrent.tracker.trackerMain;
import almostTorrent.utils.docUtils;

import static almostTorrent.utils.docUtils.printHelp;
import static almostTorrent.utils.ioUtils.ep;
import static almostTorrent.utils.ioUtils.mKbScanner;
import static almostTorrent.utils.otherUtils.exitSoftware;

public class shellLoop {

    static String[] mParams;

    public static void startCliLoop() {
        ep("Entering almostTorrent master shell");
        docUtils.printHelp("masterShell");

        while (true) {
            System.out.print("\n% ");

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

    // CLI Shell function
    private static void peerShell(){
        boolean shellActive = true;
        while (shellActive) {
            System.out.print("\npeer% ");
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
            System.out.print("\ntracker% ");

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
    }

    private static void startPeer() {
        peerMain.main(mParams);
    }

    private static void startTracker() {
        trackerMain.main(mParams);
    }

}
