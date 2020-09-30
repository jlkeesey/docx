package html;

import com.google.common.collect.ImmutableMap;
import model.*;
import org.jetbrains.annotations.NotNull;
import util.IndentAppendable;

import java.util.Map;
import java.util.Stack;

public class HtmlFormatVisitor implements GuideVisitor {
  private final @NotNull IndentAppendable appendable;
  private final Stack<String> openTags = new Stack<>();

  private int sectionLevel = 0;

  public HtmlFormatVisitor(Appendable appendable) {
    this.appendable = new IndentAppendable(appendable);
  }

  @Override
  public void start(Guide element) {
    String header = """
                    <!doctype html>
                    <html lang="en">
                      <head>
                        <meta charset="utf-8">
                        <title>%1$s</title>
                        <meta name="description" content="%1$s">
                        <style>
                          h1 {
                            font-size: 1.5em;
                          }
                          .frontMatter {
                            text-align: center;
                            font-weight: bold;
                            width: 100%%;
                          }
                          .title {
                            font-size: 3em;
                            margin-top: 3in;
                          }
                          .subtitle {
                            font-size: 2em;
                            margin-top: 0.5in;
                          }
                          .documentNumber {
                            font-size: 1.25em;
                            margin-top: 1in;
                          }
                          .revision {
                            font-size: 1em;
                            margin-top: 0.5in;
                          }
                          .section-name {
                            margin-right: 4pt;
                          }
                          .note {
                            border: 1pt solid black;
                          }
                          .callout {
                            border: 1pt solid black;
                            padding: 2pt 20pt;
                            margin: 0.1in 0.76in;
                          }
                          .callout-title {
                            text-align: center;
                            font-weight: bold;
                            font-size: larger;
                            width: 100%%;
                          }
                          .callout-caution {
                            background: #bb0;
                          }
                          .callout-warning {
                            background: #b00;
                          }
                          table {
                            width: 100%%;
                            border-collapse: collapse;
                          }
                          td {
                            padding: 0px 10px;
                            border: 1px solid;
                          }
                        </style>
                      </head>
                      <body>
                    """;
    appendable.append(String.format(header, element.title()));
    appendable.indent()
              .indent();
    addTextDiv(element.title(), "title frontMatter");
    addTextDiv(element.subTitle(), "subtitle frontMatter");
    addTextDiv(element.documentNumber(), "documentNumber frontMatter");
    addTextDiv(element.revision(), "revision frontMatter");
  }

  private void addTextDiv(String text, String classNames) {
    if (text != null && text.length() > 0) {
      openTag("div", ImmutableMap.of("class", classNames));
      appendable.indent();
      appendable.append(text);
      appendable.outdent();
      endTag();
    }
  }

  @Override
  public void end(Guide element) {
    String footer = """
                      </body>
                    </html> 
                    """;
    appendable.outdent()
              .outdent();
    appendable.append(footer);
    appendable.endLine();
  }

  @Override
  public void start(GuideSection element, String name, int index) {
    if (++sectionLevel > 9) {
      throw new IllegalStateException("Too many section levels.");
    }
    openTag(String.format("h%d", sectionLevel), ImmutableMap.of("na-type", "section", "na-name", element.name()));

    openTagInline("span", ImmutableMap.of("class", "section-name"));
    appendable.append(element.name());
    endTagInline();

    openTagInline("span", ImmutableMap.of("class", "section-title"));
    appendable.append(element.title());
    endTagInline();

    endTag();

    openTag("div");
  }

  @Override
  public void end(GuideSection element, String name, int index) {
    sectionLevel--;
    endTag();
  }

  @Override
  public void start(GuideBulletList element, int index) {
    openTag("ul");
  }

  @Override
  public void end(GuideBulletList element, int index) {
    endTag();
  }

  @Override
  public void start(GuideBulletListItem element, int index) {
    openTag("li");
  }

  @Override
  public void end(GuideBulletListItem element, int index) {
    endTag();
  }

  @Override
  public void start(GuideCallout element, int index) {
    String className = switch (element.calloutType()) {
      case Note -> "callout-note";
      case Caution -> "callout-caution";
      case Warning -> "callout-warning";
      case Other -> "callout-other";
    };
    String title = switch (element.calloutType()) {
      case Note -> "Note";
      case Caution -> "Caution";
      case Warning -> "Warning";
      case Other -> element.title();
    };
    openTag("div", ImmutableMap.of("class", "callout " + className));
    openTagInline("div", ImmutableMap.of("class", "callout-title"));
    appendable.append(title);
    endTagInline();
  }

  @Override
  public void end(GuideCallout element, int index) {
    endTag();
  }

  @Override
  public void start(GuideParagraph element, int index) {
    //openTag("div", ImmutableMap.of("class", "paragraph"));
  }

  @Override
  public void end(GuideParagraph element, int index) {
    //endTag();
  }

  @Override
  public void start(GuideNumberList element, int index) {
    String type = switch (element.numberedType()) {
      case LowerCase -> "a";
      case UpperCase -> "A";
      case LowerRoman -> "i";
      case UpperRoman -> "I";
      case Numbers -> "1";
    };
    openTag("ol", ImmutableMap.of("type", type));
  }

  @Override
  public void end(GuideNumberList element, int index) {
    endTag();
  }

  @Override
  public void start(GuideNumberListItem element, int index) {
    openTag("li");
  }

  @Override
  public void end(GuideNumberListItem element, int index) {
    endTag();
  }

  @Override
  public void start(Paragraph element, int index) {
    openTag("p");
  }

  @Override
  public void end(Paragraph element, int index) {
    endTag();
  }

  @Override
  public void start(GuideTable element, int index) {
    openTag("table");
  }

  @Override
  public void end(GuideTable element, int index) {
    endTag();
  }

  @Override
  public void start(GuideTableRow element, int index) {
    openTag("tr");
  }

  @Override
  public void end(GuideTableRow element, int index) {
    endTag();
  }

  @Override
  public void start(GuideTableCell element, int index) {
    openTag("td");
  }

  @Override
  public void end(GuideTableCell element, int index) {
    endTag();
  }

  @Override
  public void visit(@NotNull TextRun element, int index) {
    if (element.isBold()) {
      openTagInline("b");
    }
    if (element.isItalic()) {
      openTagInline("i");
    }
    appendable.append(element.text());
    if (element.isItalic()) {
      endTagInline();
    }
    if (element.isBold()) {
      endTagInline();
    }
  }

  private void addAttrs(Map<String, String> attrs) {
    for (Map.Entry<String, String> entry : attrs.entrySet()) {
      appendable.append(" ");
      appendable.append(entry.getKey());
      appendable.append("='");
      appendable.append(entry.getValue());
      appendable.append("'");
    }
  }

  private void openTagInline(String name) {
    openTagInline(name, ImmutableMap.of());
  }

  private void openTagInline(String name, Map<String, String> attrs) {
    openTags.push(name);
    appendable.append("<")
              .append(name);
    addAttrs(attrs);
    appendable.append(">");
  }

  private void openTag(String name) {
    openTag(name, ImmutableMap.of());
  }

  private void openTag(String name, Map<String, String> attrs) {
    appendable.endLine();
    openTagInline(name, attrs);
    appendable.endLine();
    appendable.indent();
  }

  private void endTagInline() {
    String name = openTags.pop();
    appendable.append("</")
              .append(name)
              .append(">");
  }

  private void endTag() {
    appendable.outdent();
    appendable.endLine();
    endTagInline();
    appendable.endLine();
  }
}
