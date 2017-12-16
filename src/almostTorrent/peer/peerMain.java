package almostTorrent.peer;

import almostTorrent.communication.communicationThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static almostTorrent.utils.ioUtils.ep;
import static almostTorrent.utils.otherUtils.logAdd;

public class peerMain {

    // Thread manager
    private static ExecutorService mExecutorService;

    // Statelessless
    private static boolean peerThreadRunning = false;

    // List of active open sockets
    private static List<Socket> mSocketList = new ArrayList<>();

    // Event log
    public static List<String> mLog = new ArrayList<>();

    //// Basic Functions

    public static void main(String[] args) {
        if (!peerThreadRunning) {
            ep("Starting peer daemon...");

            // Initialize thread manager
            mExecutorService = Executors.newCachedThreadPool();

            // Start server socket listener
            mExecutorService.execute(peerThreadManagerRunnable);
        } else {
            ep("Peer daemon already running");
        }
    }

    private static Runnable peerThreadManagerRunnable = () -> {
        try {
            ServerSocket mServerSocket = new ServerSocket(50001);
            peerThreadRunning = true;

            while (peerThreadRunning) {
                Socket socket = mServerSocket.accept();
                mSocketList.add(socket);
                peerListenerRunnable mPeerListenerRunnable = new peerListenerRunnable(socket);
                mExecutorService.execute(mPeerListenerRunnable);
            }

        } catch (IOException e) {
            e.printStackTrace();
            ep("Port already in use, bailing");
        }
    };

    public static void cleanSocket(Socket socket, long id){
        logAdd("peer","Thread " + id + ": ended connection on port " + socket.getPort() );
        mSocketList.remove(socket);
    }

    public static void stop(){
        peerThreadRunning = false;
        mExecutorService.shutdownNow();
        mExecutorService = null;
    }

    // Overridely send message to tracker
    public static void sendMessage(String message){
        communicationThread mCommunicationThread = new communicationThread(message);
        mExecutorService.execute(mCommunicationThread);
    }

    public static void pingServer(String[] params) {
        String pingMessage = "Ping!";
        if (params.length > 1) {
            pingMessage = params[1];
        }

        sendMessage(pingMessage);
    }

}
