package almostTorrent.peer;

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

    static String lastMessage = "";
    private static List<Socket> mSocketList = new ArrayList<>();

    public static void main(String[] args) {
        if (!peerThreadRunning) {
            ep("Starting peer client");

            Thread peerThreadManagerThread = new Thread(peerThreadManagerRunnable);
            peerThreadManagerThread.start();

        } else {
            ep("Peer client already running");
        }
    }

    private static Runnable peerThreadManagerRunnable = () -> {
        ExecutorService mExecutorService = Executors.newCachedThreadPool();

        try {
            ServerSocket mServerSocket = new ServerSocket(50000);

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

    public static void broadcast(){
        for(Socket socket : mSocketList){
            try {
                new PrintWriter(socket.getOutputStream(), true).println(lastMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void cleanSocket(Socket socket){
        System.out.println("Garbage useless socket closed!");
        mSocketList.remove(socket);

    }


    // CLI Shell function
    public static void shell(){
        boolean shellActive = true;
        while (shellActive) {
            System.out.print("\npeer% ");
            switch (mKbScanner.nextLine().toLowerCase()) {
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
}
