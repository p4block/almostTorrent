package beeTorrent.utils;

import beeTorrent.roles.Bee;
import beeTorrent.roles.Peer;
import beeTorrent.roles.Tracker;
import beeTorrent.shell.Shell;

import java.util.ArrayList;
import java.util.List;

public class lifeCycleUtils {

    // What a Bee object can be
    public enum HiveRole {
        TRACKER,
        PEER,
    }

    // Keep track of how many we got
    public static List<Peer> mPeerList = new ArrayList<Peer>();
    public static List<Tracker> mTrackerList = new ArrayList<Tracker>();

    // Clean up before exiting program
    public static void exitMainLoop(int i){
        // TODO: Do cleanup
        //
        System.exit(i);
    }

    // Starting roles
    public static void startPeer() {
        Peer mPeer = new Peer();
        mPeerList.add(mPeer);
    }

    public static void startTracker() {
        Tracker mTracker = new Tracker();
        mTrackerList.add(mTracker);
    }

    // Stopping roles, by direct reference (code) or by index (shell)
    public static void stopPeer(Peer peer) {
        peer.stop();
        mPeerList.remove(peer);
        peer = null;
    }

    public static void stopPeer(int i) {
        Peer peer = mPeerList.get(i);
        peer.stop();
        peer = null;
    }

    public static void stopTracker(Tracker tracker) {
        tracker.stop();
        mTrackerList.remove(tracker);
        tracker = null;
    }

    public static void stopTracker(int i) {
        Tracker tracker = mTrackerList.get(i);
        tracker.stop();
        tracker = null;
    }

    // Shell
    public static void startShell() {

    }

}
