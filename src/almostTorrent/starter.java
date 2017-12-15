package almostTorrent;

import almostTorrent.client.clientMain;
import almostTorrent.server.serverMain;
import almostTorrent.utils.randomUtils;

import static almostTorrent.utils.randomUtils.*;

public class starter {

    private static String[] mArgs;

    public static void main(String[] args) {
        mArgs = args;

        initializeStarter();
    }

    private static void initializeStarter(){
        // Start all
        randomUtils.initializeHelpers();

        ep("\n=== almostTorrent CLI launcher ===");

        for (String i : mArgs) {
            switch (i) {
                case "-c":
                    startClient();
                    break;
                case "-s":
                    startServer();
                    break;
                case "-i":
                    startCliLoop();
                    break;
                default:
                    randomUtils.printHelp("jar");
                    break;
            }
        }
    }

    private static void startCliLoop() {
        ep("Entering almostTorrent master shell");
        randomUtils.printHelp("masterShell");

        while (true) {
            System.out.print("\n % ");

            switch (mKbScanner.nextLine().toLowerCase()) {
                case "start client":
                    startClient();
                    break;
                case "start server":
                    startServer();
                    break;
                case "exit":
                    System.exit(0);
                case "help":
                    randomUtils.printHelp("masterShell");
                    break;
                case "?":
                    randomUtils.printHelp("masterShell");
                    break;
                default:
                    printHelp("masterShell");
            }

        }

    }

    private static void startClient(){
        clientMain.main(mArgs);
    }

    private static void startServer(){
        serverMain.main(mArgs);
    }
}
