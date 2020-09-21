package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a Caution callout.
 */
public class GuideCaution extends GuideParagraphListBase {
  public GuideCaution(@NotNull Iterable<GuideParagraph> iterable) {
    super(GuideType.Caution, iterable);
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

  public static class Builder extends GuideParagraphListBase.BuilderBase<GuideCaution, Builder> {
    @Override
    public @NotNull GuideCaution build() {
      return new GuideCaution(items);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }
  }
}
