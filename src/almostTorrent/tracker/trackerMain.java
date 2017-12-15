package almostTorrent.tracker;

import almostTorrent.utils.docUtils;

import static almostTorrent.utils.docUtils.printHelp;
import static almostTorrent.utils.ioUtils.mKbScanner;

public class trackerMain {

    public static void main(String[] args) {
        System.out.println("Tracker running");

        //TODO: implement actual tracker
    }

    public static void shell(){
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
}
