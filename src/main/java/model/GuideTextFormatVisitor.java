package model;

import org.jetbrains.annotations.NotNull;
import util.IndentAppendable;

public class GuideTextFormatVisitor implements GuideVisitor {
  private final @NotNull IndentAppendable appendable;

  public GuideTextFormatVisitor(Appendable appendable) {
    this.appendable = new IndentAppendable(appendable);
  }

  @Override
  public void start(GuideStep element, String name, int index) {

//    @Override
//    public void format(IndentAppendable appendable) {
//      appendable.append(name()).append(": ").append(title()).endLine();
//      appendable.indent();
//      for (GuideBase item : items) {
//        item.format(appendable);
//      }
//      appendable.outdent();
//    }
  }

  @Override
  public void start(GuideBullets element, int index) {

//    @Override
//    public void format(IndentAppendable appendable) {
//      paragraphs.forEach(item -> {
//        appendable.append("o ");
//        item.format(appendable);
//        appendable.endLine();
//      });
//      appendable.endLine();
//    }

  }

  @Override
  public void visit(@NotNull GuideTextItem element, int index) {
    if (element.isBold()) {
      appendable.append("**");
    }
    if (element.isItalic()) {
      appendable.append('_');
    }
    appendable.append(element.text());
    if (element.isItalic()) {
      appendable.append('_');
    }
    if (element.isBold()) {
      appendable.append("**");
    }
  }
}
