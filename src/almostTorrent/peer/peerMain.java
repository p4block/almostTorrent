package almostTorrent.peer;

import almostTorrent.utils.docUtils;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

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
import static almostTorrent.utils.otherUtils.exitSoftware;

public class peerMain {

    static String lastMessage = "";
    static List<Socket> mSocketList = new ArrayList<Socket>();

    public static void main(String[] args) {
        ep("Starting peer client");

        Runnable r = () -> {
            ExecutorService mExecutorService = Executors.newCachedThreadPool();

            try {
                ServerSocket mServerSocket = new ServerSocket(50000);


                while (true) {
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

        Thread t = new Thread(r);
        t.start();
        System.out.println("Peer client started\n");


    }

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
