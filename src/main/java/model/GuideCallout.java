package model;

import util.IndentAppendable;

/**
 * Abstraction of a callout item like a warning or a caution. Each has a type and a list of paragraphs.
 */
public abstract class GuideCallout extends GuideParagraphListBase<GuideParagraph> {

  protected GuideCallout(GuideType type, Iterable<GuideParagraph> paragraphs) {
    super(type, paragraphs);
  }

  @Override
  public void format(IndentAppendable appendable) {
    appendable.append(type().name());
    appendable.append(": ");
    paragraphs.forEach(item -> item.format(appendable));
    appendable.endLine();
  }
}
