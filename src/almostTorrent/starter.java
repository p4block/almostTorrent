package almostTorrent;

import almostTorrent.utils.randomUtils;
import static almostTorrent.utils.randomUtils.*;

public class starter {

    public static void main(String[] args){

        // Start all
        randomUtils.initializeHelpers();

        ep("== almostTorrent cli launcher ==");
        ep("== Press s for server, c for client ==");

        int myInt = mKbScanner.nextInt();
        ep(myInt);


    }
}
