package almostTorrent.peer;

import almostTorrent.communication.messagePacket;
import almostTorrent.tracker.trackerMain;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static almostTorrent.utils.ioUtils.ep;
import static almostTorrent.utils.otherUtils.logAdd;

public class peerListenerRunnable implements Runnable {

    private Socket mSocket = null;
    private long id = 0;

    public Socket getmSocket() {
        return mSocket;
    }

    public void setmSocket(Socket mSocket) {
        this.mSocket = mSocket;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    peerListenerRunnable(Socket socket) {
        this.mSocket = socket;
        this.id = System.currentTimeMillis();

    }

    public void run() {
        try {
            logAdd("peer","Peer thread " + id + ": handling connection on port " + String.valueOf(mSocket.getPort()));

            //ObjectOutputStream mObjectOutputStream = new ObjectOutputStream(mSocket.getOutputStream());
            ObjectInputStream mObjectInputStream = new ObjectInputStream(mSocket.getInputStream());

            try {
                logAdd("peer","Receiving object");
                messagePacket mReceivedPacket = (messagePacket) mObjectInputStream.readObject();
                //System.out.print(mReceivedPacket.toString());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                mObjectInputStream.close();
                //mObjectOutputStream.close();
                peerMain.cleanSocket(mSocket,id);
                mSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
