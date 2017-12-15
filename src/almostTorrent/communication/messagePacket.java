package almostTorrent.communication;

import java.io.Serializable;

public class messagePacket implements Serializable {
    public String messageContent;

    public messagePacket(String message) {
        messageContent = message;
    }

    public void setMessageContent(String message) {
        messageContent=message;
    }

    public void Print(){
        System.out.println("message = " + messageContent);
    }

}