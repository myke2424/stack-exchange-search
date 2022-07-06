import java.util.logging.Logger;


//import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
//import com.vladsch.flexmark.util.ast.Node;
//import com.vladsch.flexmark.html.HtmlRenderer;
//import com.vladsch.flexmark.parser.Parser;
//import com.vladsch.flexmark.util.data.MutableDataSet;

// TODO Create exceptions folder

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {


        var arguments = CommandParser.parseArgs(args);

        SearchRequest request = new SearchRequest.Builder("Merge two dictionaries").site(
                "stackoverflow").accepted().build();

        StackExchange stackExchange = new StackExchange();
        var sr = stackExchange.search("Recursion limit", "stackoverflow", 10);
        System.out.println("A");

        String htmlStr = sr.get(0).question.body;


//        MutableDataSet options = new MutableDataSet();
//        String markdown = FlexmarkHtmlConverter.builder(options).build().convert(htmlStr);
//        System.out.println(markdown);


//
//        Parser parser = Parser.builder(options).build();
//        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
//
//        // You can re-use parser and renderer instances
//        Node document = parser.parse("This is *Sparta*");
//        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
//        System.out.println(html);
    }

    public static void run() {


    }
}
