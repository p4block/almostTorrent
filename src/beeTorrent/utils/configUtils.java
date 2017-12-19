package beeTorrent.utils;

import beeTorrent.Starter;

import static beeTorrent.utils.ioUtils.ep;

public class configUtils {
    public static int DEFAULT_PORT = 6969;
    public static int MIN_PORT = 1024;
    public static int MAX_PORT = 49151;

    // Select Ports
    public static int validatePort(int port) {
        if (port > MIN_PORT & port < MAX_PORT) {
            return port;
        } else {
            ep("Invalid port. Only available ports on range 1024 <-> 49151");
            ep("Setting default values");
            return DEFAULT_PORT;
        }
    }
}
