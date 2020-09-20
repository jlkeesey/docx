package model;

import util.IndentAppendable;

/**
 * Represents a list of bulleted items. We currently don't support tacking the type of bullet.
 */
public class GuideBullets extends GuideParagraphListBase<GuideParagraph> {
  public static Builder builder() {
    return new Builder();
  }

  public GuideBullets(Iterable<GuideParagraph> paragraphs) {
    super(GuideType.Bullets, paragraphs);
  }

  public static GuideBullets create(String text) {
    return create(GuideParagraph.create(text).asList());
  }

  public static GuideBullets create(Iterable<GuideParagraph> items) {
    return new GuideBullets(items);
  }

  @Override
  public void format(IndentAppendable appendable) {
    paragraphs.forEach(item -> {
      appendable.append("o ");
      item.format(appendable);
      appendable.endLine();
    });
    appendable.endLine();
  }

  public static class Builder extends GuideParagraphListBase.BuilderBase<GuideBullets.Builder, GuideBullets, GuideParagraph> {
    @Override
    public GuideBullets build() {
      return new GuideBullets(items);
    }
  }
}
