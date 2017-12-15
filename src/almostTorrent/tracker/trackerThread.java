package almostTorrent.tracker;

import java.io.*;
import java.net.Socket;

import almostTorrent.communication.messagePacket;
import static almostTorrent.utils.ioUtils.*;

public class trackerThread implements Runnable {

    private Socket mSocket = null;
    private long id = 0;

    public Socket getSocket() {
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

    trackerThread(Socket socket) {
        this.mSocket = socket;
        this.id = System.currentTimeMillis();

    }

    public void run() {
        try {
            ep("Tracker thread " + id + ": handling connection on port " + String.valueOf(mSocket.getPort()));

            ObjectInputStream mObjectInputStream = new ObjectInputStream(mSocket.getInputStream());
           // ObjectOutputStream mObjectOutputStream = null;

            try {
                ep("Tracker receiving incoming connection");
                messagePacket mReceivedPacket = (messagePacket) mObjectInputStream.readObject();
                ep(mReceivedPacket.messageContent);
                ObjectOutputStream mObjectOutputStream = new ObjectOutputStream(mSocket.getOutputStream());
                mObjectOutputStream.writeObject(new messagePacket(mReceivedPacket.messageContent));

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                mObjectInputStream.close();
                //mObjectOutputStream.close();
                trackerMain.cleanSocket(mSocket,id);
                mSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
