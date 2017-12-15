package almostTorrent.peer;

import almostTorrent.utils.docUtils;

import static almostTorrent.utils.docUtils.printHelp;
import static almostTorrent.utils.ioUtils.ep;
import static almostTorrent.utils.ioUtils.mKbScanner;
import static almostTorrent.utils.otherUtils.exitSoftware;

public class peerMain {

    public static void main(String[] args) {
        ep("Starting peer client");
    }

    public static void shell(){
        boolean shellActive = true;
        while (shellActive) {
            System.out.print("\npeer% ");
            switch (mKbScanner.nextLine().toLowerCase()) {
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
}
