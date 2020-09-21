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

  private int stepLevel = 1;

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
                          .note {
                            border: 1pt solid black;
                          }
                          .caution {
                            border: 1pt solid black;
                            background: #055;
                          }
                          .warning {
                            border: 1pt solid black;
                            background: #500;
                          }
                        </style>
                      </head>
                      <body>
                    """;
    appendable.append(String.format(header, element.title()));
    appendable.indent().indent();
  }

  @Override
  public void end(Guide element) {
    String footer = """
                      </body>
                    </html> 
                    """;
    appendable.outdent().outdent();
    appendable.append(footer);
    appendable.endLine();
  }

  @Override
  public void start(GuideStep element, String name, int index) {
    openTag(String.format("<h%d>", stepLevel));

    openTagInline("span", ImmutableMap.of("class", "step-name"));
    appendable.append(element.name());
    endTagInline();

    openTagInline("span", ImmutableMap.of("class", "step-title"));
    appendable.append(element.title());
    endTagInline();

    endTag();

    openTag("div");
    appendable.indent();
    if (++stepLevel > 9) {
      throw new IllegalStateException("Too many step levels.");
    }
  }

  @Override
  public void end(GuideStep element, String name, int index) {
    stepLevel--;
    appendable.outdent();
    endTag();
  }

  @Override
  public void start(GuideBullets element, int index) {
    openTag("ul");
    appendable.indent();
  }

  @Override
  public void end(GuideBullets element, int index) {
    appendable.outdent();
    endTag();
  }

  @Override
  public void start(GuideBulletItem element, int index) {
    openTag("li");
    appendable.indent();
  }

  @Override
  public void end(GuideBulletItem element, int index) {
    appendable.outdent();
    endTag();
  }

  @Override
  public void start(GuideCaution element, int index) {
    openTag("div", ImmutableMap.of("class", "caution"));
    appendable.indent();
  }

  @Override
  public void end(GuideCaution element, int index) {
    appendable.outdent();
    endTag();
  }

  @Override
  public void start(GuideDescription element, int index) {
    openTag("div", ImmutableMap.of("class", "description"));
    appendable.indent();
  }

  @Override
  public void end(GuideDescription element, int index) {
    appendable.outdent();
    endTag();
  }

  @Override
  public void start(GuideNote element, int index) {
    openTag("div", ImmutableMap.of("class", "note"));
    appendable.indent();
  }

  @Override
  public void end(GuideNote element, int index) {
    appendable.outdent();
    endTag();
  }

  @Override
  public void start(GuideNumbered element, int index) {
    openTag("ol");
    appendable.indent();
  }

  @Override
  public void end(GuideNumbered element, int index) {
    appendable.outdent();
    endTag();
  }

  @Override
  public void start(GuideNumberedItem element, int index) {
    openTag("li");
    appendable.indent();
  }

  @Override
  public void end(GuideNumberedItem element, int index) {
    appendable.outdent();
    endTag();
  }

  @Override
  public void start(GuideWarning element, int index) {
    openTag("div", ImmutableMap.of("class", "warning"));
    appendable.indent();
  }

  @Override
  public void end(GuideWarning element, int index) {
    appendable.outdent();
    endTag();
  }

  @Override
  public void start(GuideParagraph element, int index) {
    openTag("p");
    appendable.indent();
  }

  @Override
  public void end(GuideParagraph element, int index) {
    appendable.outdent();
    endTag();
  }

  @Override
  public void visit(@NotNull GuideTextItem element, int index) {
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
    appendable.append("<").append(name);
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
  }

  private void endTagInline() {
    String name = openTags.pop();
    appendable.append("</").append(name).append(">");
  }

  private void endTag() {
    appendable.endLine();
    endTagInline();
    appendable.endLine();
  }
}
