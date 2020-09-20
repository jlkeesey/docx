package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a Caution callout.
 */
public class GuideCaution extends GuideParagraphListBase {
  public GuideCaution(Iterable<GuideParagraph> iterable) {
    super(GuideType.Caution, iterable);
  }

  public static @NotNull Builder builder() {
    return new Builder();
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
