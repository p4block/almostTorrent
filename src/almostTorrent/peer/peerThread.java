package almostTorrent.peer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class peerThread implements Runnable {

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

    peerThread(Socket socket) {
        this.mSocket = socket;
        this.id = System.currentTimeMillis();

    }

    public void run() {
        try {
            System.out.println("Accepted connection on port " + String.valueOf(mSocket.getPort()) + " and thread with id " + String.valueOf(id));

            PrintWriter mConnection_out = new PrintWriter(mSocket.getOutputStream(), true);
            BufferedReader mConnection_in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));


            try {
                String receivedLine;
                while ((receivedLine = mConnection_in.readLine()) != null) {
                    System.out.println("Received: " + receivedLine);

                    if (receivedLine.equals("exit")) {
                        mConnection_out.println("exit");
                        break;
                    } else {
                        receivedLine = String.valueOf(id) + ": " + receivedLine;
                        peerMain.lastMessage = receivedLine;
                        peerMain.broadcast();
                        //mConnection_out.println(receivedLine);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mConnection_in.close();
                mConnection_out.close();
                peerMain.cleanSocket(getmSocket());
                mSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
