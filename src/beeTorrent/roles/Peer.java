package beeTorrent.roles;

import beeTorrent.communication.communicationThread;
import beeTorrent.utils.lifeCycleUtils;

public class Peer extends Bee {

    public Peer(){
        super(lifeCycleUtils.HiveRole.PEER);
    }

    // Overridely send message to tracker
    private void sendMessage(String message){
        communicationThread mCommunicationThread = new communicationThread(message);
        super.mExecutorService.execute(mCommunicationThread);
    }

    public void pingServer(String[] params) {
        String pingMessage = "Ping!";
        if (params.length > 1) {
            pingMessage = params[1];
        }

        sendMessage(pingMessage);
    }

}
