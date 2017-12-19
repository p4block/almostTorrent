package beeTorrent.shell;

import beeTorrent.Starter;
import beeTorrent.roles.Peer;
import beeTorrent.roles.Tracker;
import beeTorrent.utils.docUtils;
import beeTorrent.utils.ioUtils;
import beeTorrent.utils.lifeCycleUtils;
import beeTorrent.utils.configUtils;

import static beeTorrent.utils.docUtils.*;
import static beeTorrent.utils.ioUtils.*;
import static beeTorrent.utils.lifeCycleUtils.*;

public class Shell {

    private static String[] mParams;
    private static int mPort;

    public static void mainLoop() {
        ep("Entering beeTorrent master shell");
        docUtils.printHelp("masterShell");

        while (true) {
            System.out.print("% ");

            String[] params = mKbScanner.nextLine().toLowerCase().split(" ");

            // entropy++ force array to have at least 3 arguments
            if (params.length < 2) {
                params = new String[]{params[0], " "};
            }

            mParams = params;

            switch (params[0]) {
                case "start":
                    switch (params[1]) {
                        case "peer":
                            try {
                                if (!params[2].equals(" ")) {
                                    int port = Integer.parseInt(params[2]);
                                    startPeer(configUtils.validatePort(port));
                                    break;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
//                                ep("Start " + params[1] + " on default port: " + configUtils.DEFAULT_PORT);
                                startPeer(configUtils.DEFAULT_PORT);
                                break;
                            }
                            catch (NumberFormatException e){
                                ep("Invalid syntax. Use numbers to define the port.");
                                break;
                            }
                        case "tracker":
                            try {
                                if (!params[2].equals(" ")) {
                                    int port = Integer.parseInt(params[2]);
                                    startTracker(configUtils.validatePort(port));
                                    break;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
//                                ep("Start " + params[1] + " on default port: " + configUtils.DEFAULT_PORT);
                                startTracker(configUtils.DEFAULT_PORT);
                                break;
                            }
                            catch (NumberFormatException e){
                                ep("Invalid syntax. Use numbers to define the port.");
                                break;
                            }
                        default:
                            docUtils.printHelp("start");
                    }
                    break;
                case "stop":
                    switch (params[1]) {
                        case "peer":
                            try {
                                if (!params[2].equals(" ")) {
                                    int port = Integer.parseInt(params[2]);
                                    stopPeer(configUtils.validatePort(port));
                                    break;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
//                                ep("Stopping " + params[1] + " on default port");
                                stopPeer(configUtils.DEFAULT_PORT);
                                break;
                            }
                            catch (NumberFormatException e){
                                ep("Invalid syntax. Use numbers to define the port.");
                                break;
                            }
                        case "tracker":
                            try {
                                if (!params[2].equals(" ")) {
                                    int port = Integer.parseInt(params[2]);
                                    stopTracker(configUtils.validatePort(port));
                                    break;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
//                                ep("Stopping " + params[1] + " on default port");
                                stopTracker(configUtils.DEFAULT_PORT);
                                break;
                            }
                            catch (NumberFormatException e){
                                ep("Invalid syntax. Use numbers to define the port.");
                                break;
                            }
                        default:
                            docUtils.printHelp("stop");
                    }
                    break;
                case "shell":
                    switch (params[1]) {
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
                    switch (params[1]) {
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

        Tracker tracker = lifeCycleUtils.mTrackerList.get(index);

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
