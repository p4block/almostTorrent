package almostTorrent.communication;

import almostTorrent.communication.messagePacket;
import almostTorrent.tracker.trackerMain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static almostTorrent.utils.ioUtils.ep;

public class communicationThread implements Runnable {

    private Socket mSocket = null;
    private long id = 0;
    private String mMessage;

    public communicationThread(Socket socket, String message) {
        this.mSocket = socket;
        this.id = System.currentTimeMillis();
        this.mMessage = message;
    }

    public communicationThread(String message) {
        try {
            this.mSocket = new Socket("127.0.0.1", 50000);
            this.id = System.currentTimeMillis();
            this.mMessage = message;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            ObjectOutputStream mObjectOutputStream = new ObjectOutputStream(mSocket.getOutputStream());
            mObjectOutputStream.writeObject(new messagePacket(mMessage));
            ObjectInputStream mObjectInputStream = new ObjectInputStream(mSocket.getInputStream());
            messagePacket mReceivedPacket = (messagePacket) mObjectInputStream.readObject();
            ep(mReceivedPacket.messageContent);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
