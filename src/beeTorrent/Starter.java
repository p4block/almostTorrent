package beeTorrent;

import beeTorrent.shell.Shell;
import beeTorrent.utils.*;

import static beeTorrent.utils.ioUtils.*;
import static beeTorrent.utils.lifeCycleUtils.*;

import org.apache.commons.cli.*;
import org.apache.commons.cli.ParseException;

public class Starter {

    private static String[] mArgs;
    private static Options options;

    public static void main(String[] args) {
        mArgs = args;

        initializeParserOptions();
        initializeStarter();
    }

    private static void initializeStarter() {
        CommandLineParser parser = new DefaultParser();
        CommandLine cli = null;
        try {
            cli = parser.parse(options, mArgs);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HelpFormatter formatter = new HelpFormatter();

        ioUtils.initializeHelpers();
        ep("\n=== beeTorrent CLI launcher ===\n");

        if (cli.hasOption("help")) {
            formatter.printHelp("jar launch", options);
        }
        if (cli.hasOption("peer")) {
            startPeer();
        }
        if (cli.hasOption("tracker")) {
            startTracker();
        }
        if (cli.hasOption("interactive")) {
            Shell.mainLoop();
        }
        if (!cli.hasOption("help") & !cli.hasOption("peer") & !cli.hasOption("tracker") & !cli.hasOption("interactive")) {
            ep("DEBUG: No se ha cumplido ninguna opcion");
            //Change to  HelpFormatter
            //docUtils.printHelp("jar");
            formatter.printHelp("Invalid argument" + cli.getOptions(), options);
        }


//        if (mArgs.length > 0) {
//            switch (mArgs[0]) {
//                case "-p":
//                    startPeer();
//                    break;
//                case "-t":
//                    startTracker();
//                    break;
//                case "-i":
//                    Shell.mainLoop();
//                    break;
//                default:
//                    docUtils.printHelp("jar");
//            }
//        } else {
//            docUtils.printHelp("jar");
//        }

    }

    private static void initializeParserOptions() {
        Option helpOption = Option.builder("h")
                .longOpt("help")
                .required(false)
                .desc("show this message")
                .build();

        Option peerOption = Option.builder("p")
                .longOpt("peer")
                .required(false)
                .desc("Start tracker on default port")
                .build();

        Option trackerOption = Option.builder("t")
                .longOpt("tracker")
                .required(false)
                .desc("Start tracker on default port")
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
}
