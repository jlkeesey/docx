package model;

import org.jetbrains.annotations.NotNull;
import util.IndentAppendable;

public class GuideTextFormatVisitor implements GuideVisitor {
  private final @NotNull IndentAppendable appendable;

  public GuideTextFormatVisitor(Appendable appendable) {
    this.appendable = new IndentAppendable(appendable);
  }

  @Override
  public void end(Guide element) {
    appendable.endLine();
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
  public void end(GuideStep element, String name, int index) {
    appendable.endLine();
  }

  @Override
  public void end(GuideBullets element, int index) {
    appendable.endLine();
  }

  @Override
  public void start(GuideNumberedItem element, int index) {
    appendable.append(String.valueOf(index + 1)).append(". ");
  }

  @Override
  public void end(GuideNumberedItem element, int index) {
    appendable.endLine();
  }

  @Override
  public void end(GuideWarning element, int index) {
    appendable.endLine();
  }

  @Override
  public void end(GuideParagraph element, int index) {
    appendable.endLine();
  }

  @Override
  public void start(GuideBulletItem element, int index) {
    appendable.append("o ");
  }

  @Override
  public void end(GuideBulletItem element, int index) {
    appendable.endLine();
  }

  @Override
  public void end(GuideCaution element, int index) {
    appendable.endLine();
  }

  @Override
  public void end(GuideDescription element, int index) {
    appendable.endLine();
  }

  @Override
  public void end(GuideNote element, int index) {
    appendable.endLine();
  }

  @Override
  public void end(GuideNumbered element, int index) {
    appendable.endLine();
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
