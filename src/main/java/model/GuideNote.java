package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a Note callout.
 */
public class GuideNote extends GuideParagraphListBase {
  public GuideNote(@NotNull Iterable<GuideParagraph> iterable) {
    super(GuideType.Note, iterable);
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  @Override
  public void visit(@NotNull GuideVisitor visitor, int index) {
    visitor.start(this, index);
    visitItems(visitor);
    visitor.end(this, index);
  }

  public static class Builder extends GuideParagraphListBase.BuilderBase<GuideNote, Builder> {
    @Override
    public @NotNull GuideNote build() {
      return new GuideNote(items);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }
  }
}
