package beeTorrent;

import beeTorrent.shell.Shell;
import beeTorrent.utils.*;

import static beeTorrent.utils.ioUtils.ep;
import static beeTorrent.utils.lifeCycleUtils.*;

import org.apache.commons.cli.*;
import org.apache.commons.cli.ParseException;

public class Starter {

    private static int DEFAULT_PORT = 6969;

    private static String[] mArgs;
    private static int mPort;
    private static Options options;
    private static CommandLine cli;
    private static HelpFormatter formatter;

    public static void main(String[] args) {
        mArgs = args;

        initializeParserOptions();
        initializeStarter();
    }

    private static void initializeStarter() {
        CommandLineParser parser = new DefaultParser();
        formatter = new HelpFormatter();
        cli = null;

        try {
            cli = parser.parse(options, mArgs);
        } catch (ParseException e) {
            docUtils.printHelp("welcome");
            formatter.printHelp("invalid arguments", options);
            System.exit(0);
        } finally {
            ioUtils.initializeHelpers();
            docUtils.printHelp("welcome");
        }

        if (cli.hasOption("help")) {
            formatter.printHelp("jar launch", options);
        }

        if (cli.hasOption("peer")) {
            parsePortAndStart("peer");
        }

        if (cli.hasOption("tracker")) {
            parsePortAndStart("tracker");
        }

        if (cli.hasOption("interactive")) {
            Shell.mainLoop();
        }

        //formatter.printHelp("no arguments", options);

    }

    private static void initializeParserOptions() {
        Option helpOption = Option.builder("h")
                .longOpt("help")
                .required(false)
                .desc("show this message")
                .build();

        Option peerOption = Option.builder("p")
                .longOpt("peer")
                .numberOfArgs(1)
                .argName("port")
                .optionalArg(true)
                .required(false)
                .desc("Start tracker on selected port")
                .build();

        Option trackerOption = Option.builder("t")
                .longOpt("tracker")
                .numberOfArgs(1)
                .argName("port")
                .optionalArg(true)
                .required(false)
                .desc("Start tracker on selected port")
                .build();

        Option shellOption = Option.builder("i")
                .longOpt("interactive")
                .required(false)
                .desc("Enter to the interactive shell")
                .build();

        options = new Options();
        options.addOption(helpOption);
        options.addOption(peerOption);
        options.addOption(trackerOption);
        options.addOption(shellOption);
    }

    private static void parsePortAndStart(String option) {
        try {
            if (cli.getParsedOptionValue(option) == null) {
                mPort = DEFAULT_PORT;
                if (option == "peer") {
                    startPeer(mPort);
                } else {
                    startTracker(mPort);
                }

            } else {
                mPort = Integer.parseInt((String) cli.getParsedOptionValue(option));
                if (mPort > 0 & mPort < 65535) {
                    if (option == "peer") {
                        startPeer(mPort);
                    } else {
                        startTracker(mPort);
                    }
                } else {
                    ep("Invalid port. Setting default port");
                    mPort = DEFAULT_PORT;
                    if (option == "peer") {
                        startPeer(mPort);
                    } else {
                        startTracker(mPort);
                    }
                }
            }
        } catch (ParseException e) {
            formatter.printHelp("Couldn't parse arguments", options);
        }
    }
}
