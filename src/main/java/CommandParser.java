import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.logging.Logger;

public class CommandParser {
    private static final Logger LOGGER = Logger.getLogger(Http.class.getName());

    public static Namespace parseArgs(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("Stack Exchange CLI").build().defaultHelp(true).description("Search stackexchange websites");


        parser.addArgument("-q", "--query").required(true).help("Search query used to " + "search a stack exchange website");

        parser.addArgument("-s", "--site").setDefault("stackoverflow").help("Stack " +
                "exchange website used to search the query on - default=stackoverflow");

        parser.addArgument("-t", "--tags").help("Tags used in stack exchange search");

        parser.addArgument("-i", "--interactive").help("Interactive search flag, used " +
                "to display search results and allow the user to interactive");

        Namespace arguments = null;

        try {
            arguments = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            LOGGER.info("Failed to parse arguments");
            parser.handleError(e);
            System.exit(1);
        }

        LOGGER.info(String.format("Command line arguments: (%s)", arguments.toString()));

        return arguments;
    }
}
