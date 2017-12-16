package almostTorrent.tracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static almostTorrent.utils.ioUtils.*;
import static almostTorrent.utils.otherUtils.*;

public class trackerMain {

    // Thread manager
    private static ExecutorService mExecutorService;

    // Statelessless
    private static boolean trackerThreadRunning = false;

    // List of active open sockets
    private static ServerSocket mServerSocket;
    private static List<Socket> mSocketList = new ArrayList<>();

    // Event log
    public static List<String> mLog = new ArrayList<>();

    //// Basic Functions

    public static void main(String[] args) {
        if (!trackerThreadRunning) {
            ep("Starting tracker daemon...");

            // Initialize thread manager
            mExecutorService = Executors.newCachedThreadPool();

            // Start server socket listener
            mExecutorService.execute(trackerThreadManagerRunnable);
        } else {
            ep("Tracker already running");
        }
    }

    // Thread that blocks the server port and spawns socket connections for incoming clients
    private static Runnable trackerThreadManagerRunnable = () -> {
        try {
            mServerSocket = new ServerSocket(50000);
            trackerThreadRunning = true;

            try {
                while (trackerThreadRunning) {
                    Socket socket = mServerSocket.accept();
                    mSocketList.add(socket);
                    mExecutorService.execute(new trackerListenerRunnable(socket));
                }
            } catch (IOException e) {
                ep("Stopped tracker daemon");
            }

        } catch (IOException e) {
            e.printStackTrace();
            ep("Port already in use, bailing");
        }
    };

    public static void cleanSocket(Socket socket, long id){
        logAdd("tracker","Thread " + id + ": ended connection on port " + socket.getPort() );
        mSocketList.remove(socket);
    }

    public static void stop() {
        if (trackerThreadRunning) {
            try {
                trackerThreadRunning = false;
                mServerSocket.close();
                mExecutorService.shutdownNow();
            } catch (IOException e) {
                // Socket closed successfully, false exception. Do nothing.
            }
        }
    }


}
