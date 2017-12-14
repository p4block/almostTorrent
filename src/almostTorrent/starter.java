package almostTorrent;

import almostTorrent.client.clientMain;
import almostTorrent.server.serverMain;
import almostTorrent.utils.randomUtils;
import static almostTorrent.utils.randomUtils.*;

public class starter {

    public static void main(String[] args){

        // Start all
        randomUtils.initializeHelpers();

        ep("== almostTorrent cli launcher ==");

        for(String i : args){
            if(i.equals("-c")){
                clientMain.main(args);
            } else if(i.equals("-s")) {
                serverMain.main(args);
            } else if(i.equals("-i")){
                startCliLoop();
            } else {
                randomUtils.printHelp("jar");
            }
        }


    }

    private static void startCliLoop(){
        ep("Entering almostTorrent master shell");

        while(true){
            switch (mKbScanner.nextLine()){
                case "help":
                    randomUtils.printHelp("shell");
            }
        }

    }
}
