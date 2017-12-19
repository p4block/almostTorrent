package beeTorrent.roles;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import beeTorrent.communication.connectionHandlerRunnable;
import beeTorrent.utils.lifeCycleUtils.*;

import static beeTorrent.utils.ioUtils.*;

public class Bee {

    // Role
    private HiveRole mRole;

    // Thread manager
    ExecutorService mExecutorService;

    // Event log
    public List<String> mLog = new ArrayList<>();

    // Statelessless
    private boolean threadRunning = false;

    // Socket objects
    private ServerSocket mServerSocket;
    public List<Socket> mSocketList = new ArrayList<>();

    // Configuration
    private int mPort = 50000;

    private Bee mThis;

    //// Constructors
    Bee (HiveRole role){
        mRole = role;
        start();
    }

    Bee (HiveRole role, int port){
        mRole = role;
        mPort = port;
        start();
    }

    // Our early steps
    private void start(){
        mThis = this;

        if (!threadRunning) {
            ep(mRole + " on port " + mPort + " starting");

            // Initialize thread manager
            mExecutorService = Executors.newCachedThreadPool();

            // Start server socket listener
            mExecutorService.execute(threadManagerRunnable);
        } else {
            ep( mRole + " on port " + mPort + " already running");
        }
    }

    private Runnable threadManagerRunnable = () -> {
        try {
            mServerSocket = new ServerSocket(mPort);
            threadRunning = true;

            while (threadRunning) {
                try {
                    Socket socket = mServerSocket.accept();
                    mSocketList.add(socket);
                    mExecutorService.execute(new connectionHandlerRunnable(mThis,mRole,socket));
                } catch (IOException e) {
                    ep(mRole + " on port " + mPort + " stopping");
                }
            }

        } catch (IOException e) {
            //e.printStackTrace();
            ep("Port " + mPort + " already in use. Unable to start.");
        }
    };

    public void stop(){
        if (threadRunning) try {
            threadRunning = false;
            mServerSocket.close();
            mExecutorService.shutdownNow();
        } catch (IOException e) {
            // Socket closed successfully, false exception. Do nothing.
        }
    }

    public void cleanSocket(Socket socket, long id){
        logAdd(this,"Thread " + id + ": ended connection on port " + socket.getPort() );
        mSocketList.remove(socket);
    }

}
