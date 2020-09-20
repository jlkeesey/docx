package model;

import util.IndentAppendable;

public class NumberedText implements GuideFormatable {
  public final int index;
  public final GuideParagraph text;

  public NumberedText(int index, GuideParagraph text) {
    this.index = index;
    this.text = text;
  }

  @Override
  public void format(IndentAppendable appendable) {
    appendable.append(String.valueOf(index));
    appendable.append(". ");
    text.format(appendable);
    appendable.endLine();
  }
}
