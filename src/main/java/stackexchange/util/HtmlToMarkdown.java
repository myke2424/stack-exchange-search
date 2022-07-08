package stackexchange.util;

import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import com.vladsch.flexmark.util.data.MutableDataSet;

/**
 * Parse HTML into Markdown for better console output... stack exchange responses are all HTML by
 * default
 */
public class HtmlToMarkdown {

  private static MutableDataSet options = new MutableDataSet();

  public static String parse(String html) {
    return FlexmarkHtmlConverter.builder(options).build().convert(html);
  }
}
