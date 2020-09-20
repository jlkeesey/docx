package model;

import util.IndentAppendable;

/**
 * Represents a numbered list. Currently we only support lists that are numbered with arabic numbers, sequentially,
 * starting with 1.
 */
public class GuideNumbered extends GuideParagraphListBase<NumberedText> {

  public GuideNumbered(Iterable<NumberedText> items) {
    super(GuideType.Numbered, items);
  }

  @Override
  public void format(IndentAppendable appendable) {
    paragraphs.forEach(item -> item.format(appendable));
    appendable.endLine();
  }

  public static class Builder
      extends GuideParagraphListBase.BuilderBase<GuideNumbered.Builder, GuideNumbered, NumberedText> {

    @Override
    public Builder add(GuideParagraph paragraph) {
      items.add(new NumberedText(items.size(), paragraph));
      return this;
    }

    @Override
    public GuideNumbered build() {
      return new GuideNumbered(items);
    }
  }
}