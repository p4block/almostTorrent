package beeTorrent.communication;


import java.io.*;
import java.net.Socket;

import beeTorrent.roles.Bee;
import beeTorrent.roles.Tracker;
import beeTorrent.utils.lifeCycleUtils.HiveRole;

import static beeTorrent.utils.ioUtils.*;

public class connectionHandlerRunnable implements Runnable {

    private Socket mSocket = null;
    private long id = 0;

    private static Bee mCaller;
    private static HiveRole mRole;

    public connectionHandlerRunnable(Bee caller, HiveRole role, Socket socket) {
        mCaller = caller;
        mRole = role;
        mSocket = socket;
        id = System.currentTimeMillis();

    }

    public void run() {
        try {
            logAdd(mCaller,"Tracker thread " + id + ": handling connection on port " + String.valueOf(mSocket.getPort()));

            ObjectInputStream mObjectInputStream = new ObjectInputStream(mSocket.getInputStream());
            // ObjectOutputStream mObjectOutputStream = null;

            try {
                logAdd(mCaller,mRole.name() + "receiving incoming connection");
                messagePacket mReceivedPacket = (messagePacket) mObjectInputStream.readObject();
                logAdd(mCaller, mReceivedPacket.messageContent);
                ObjectOutputStream mObjectOutputStream = new ObjectOutputStream(mSocket.getOutputStream());
                mObjectOutputStream.writeObject(new messagePacket(mReceivedPacket.messageContent));

            } catch (IOException | ClassNotFoundException e) {

                e.printStackTrace();
            } finally {
                mObjectInputStream.close();
                //mObjectOutputStream.close();
                mCaller.cleanSocket(mSocket, id);
                mSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
