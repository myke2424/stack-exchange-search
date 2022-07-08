package stackexchange.util;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommandParser {
    private static final Logger logger = LoggerFactory.getLogger(CommandParser.class);

    public static Namespace parseArgs(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("Stack Exchange CLI").build().defaultHelp(true).description("Search stackexchange websites");


        parser.addArgument("-q", "--query").required(true).help("Search query used to " +
                "search a stack exchange website");

        parser.addArgument("-s", "--site").setDefault("stackoverflow").help("Stack " +
                "exchange website used to search the query on - default=stackoverflow");

        parser.addArgument("-t", "--tags").help("Space seperated tags used in stack " +
                "exchange search -" +
                " " +
                "Example: --tags='python dictionary recursion'");

        parser.addArgument("-i", "--interactive").setDefault(false).help("Interactive " +
                "search flag, used " +
                "to display search results and allow the user to interactive");

        Namespace arguments = null;

        try {
            arguments = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            logger.error("Failed to parse arguments... Exiting");
            parser.handleError(e);
            System.exit(1);
        }

        logger.info(String.format("Command line arguments: (%s)", arguments.toString()));

        return arguments;
    }
}
