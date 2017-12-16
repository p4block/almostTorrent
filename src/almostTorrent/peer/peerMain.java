package almostTorrent.peer;

import almostTorrent.communication.communicationThread;
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

public class peerMain {

    private static boolean peerThreadRunning = false;

    private static List<Socket> mSocketList = new ArrayList<>();

    private static ExecutorService mExecutorService;

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
                peerThread thread = new peerThread(socket);
                mExecutorService.execute(thread);
            }

        } catch (IOException e) {
            e.printStackTrace();
            ep("Port already in use, bailing");
        }
    };

    // Overridely send message to tracker
    public static void sendMessage(String message){
        communicationThread mCommunicationThread = new communicationThread("Hello GUYS!");
        mExecutorService.execute(mCommunicationThread);
    }

    public static void cleanSocket(Socket socket){
        System.out.println("Socket closed");
        mSocketList.remove(socket);

    }

    public static void pingServer(String[] params) {
        String pingMessage = "Ping!";
        if (params.length > 1) {
            pingMessage = params[1];
        }

        sendMessage(pingMessage);
    }

}
