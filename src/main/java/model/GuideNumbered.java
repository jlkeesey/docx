package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a numbered list. Currently we only support lists that are numbered with arabic numbers, sequentially,
 * starting with 1.
 */
public class GuideNumbered extends GuideParagraphListBase {

  public GuideNumbered(Iterable<GuideParagraph> iterable) {
    super(GuideType.Numbered, iterable);
  }

  public static class Builder extends GuideParagraphListBase.BuilderBase<GuideNumbered, Builder> {

    @Override
    public @NotNull GuideNumbered build() {
      return new GuideNumbered(items);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }
  }
}