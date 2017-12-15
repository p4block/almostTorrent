package almostTorrent.tracker;

import almostTorrent.utils.docUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static almostTorrent.utils.docUtils.printHelp;
import static almostTorrent.utils.ioUtils.ep;
import static almostTorrent.utils.ioUtils.mKbScanner;

public class trackerMain {

    private static boolean trackerThreadRunning = false;

    static String lastMessage = "";
    private static List<Socket> mSocketList = new ArrayList<>();

    public static void main(String[] args) {
        if (!trackerThreadRunning) {
            ep("Starting tracker");
            trackerThreadRunning = true;

            Thread peerThreadManagerThread = new Thread(trackerThreadManager);
            peerThreadManagerThread.start();

        } else {
            ep("Tracker already running");
        }
    }

    // Thread that blocks the server port and spawns socket connections for incoming clients
    private static Runnable trackerThreadManager = () -> {
        ExecutorService mExecutorService = Executors.newCachedThreadPool();

        try {
            ServerSocket mServerSocket = new ServerSocket(50000);

            while (trackerThreadRunning) {
                Socket socket = mServerSocket.accept();
                mSocketList.add(socket);
                trackerThread thread = new trackerThread(socket);
                mExecutorService.execute(thread);
            }

        } catch (IOException e) {
            e.printStackTrace();
            ep("Port already in use, bailing");
        }
    };

    public static void broadcast(){
        for(Socket socket : mSocketList){
            try {
                new PrintWriter(socket.getOutputStream(), true).println(lastMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void cleanSocket(Socket socket, long id){
        ep("Thread " + id + ": ended connection on port " + socket.getPort() );
        mSocketList.remove(socket);
    }

    // CLI Shell function
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
