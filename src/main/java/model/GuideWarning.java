package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a Warning callout.
 */
public class GuideWarning extends GuideParagraphListBase {
  public GuideWarning(Iterable<GuideParagraph> iterable) {
    super(GuideType.Warning, iterable);
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  public static class Builder extends GuideParagraphListBase.BuilderBase<GuideWarning, Builder> {
    @Override
    public @NotNull GuideWarning build() {
      return new GuideWarning(items);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }
  }
}
