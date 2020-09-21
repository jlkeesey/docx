package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a block of descriptive text. Each block/paragraph will be represented by a separate object.
 */
public class GuideDescription extends GuideParagraphListBase {
  private GuideDescription(@NotNull Iterable<GuideParagraph> iterable) {
    super(GuideType.Description, iterable);
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

  public static class Builder extends GuideParagraphListBase.BuilderBase<GuideDescription, Builder> {
    @Override
    public @NotNull GuideDescription build() {
      return new GuideDescription(items);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }
  }
}
