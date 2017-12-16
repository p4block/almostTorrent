package almostTorrent.utils;

import almostTorrent.peer.peerMain;
import almostTorrent.tracker.trackerMain;

public class lifeCycleUtils {

    // Clean up sockets before exiting program
    public static void exitMainLoop(int i){

        System.exit(i);
    }

    // Lifecycle
    public static void startPeer(String[] params) {
        peerMain.main(params);
    }

    public static void stopPeer() {
        peerMain.stop();
    }

    public static void startTracker(String[] params) {
        trackerMain.main(params);
    }

    public static void stopTracker() {
        trackerMain.stop();
    }

}
