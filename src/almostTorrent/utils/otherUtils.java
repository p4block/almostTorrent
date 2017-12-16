package almostTorrent.utils;

import almostTorrent.peer.peerMain;
import almostTorrent.tracker.trackerMain;

public class otherUtils {

    // Clean up sockets before exiting program
    public static void exitMainLoop(int i){
        //
        // mSocket...

        System.exit(i);
    }

    // Add to log objects
    public static void logAdd(String who, String content){
        switch (who) {
            case "tracker":
                peerMain.mLog.add(content);
                break;
            case "peer":
                trackerMain.mLog.add(content);
        }
    }

}
