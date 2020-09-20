package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a list of bulleted items. We currently don't support tacking the type of bullet.
 */
public class GuideBullets extends GuideParagraphListBase {
  public GuideBullets(Iterable<GuideParagraph> iterable) {
    super(GuideType.Bullets, iterable);
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  public static class Builder extends GuideParagraphListBase.BuilderBase<GuideBullets, Builder> {
    @Override
    public @NotNull GuideBullets build() {
      return new GuideBullets(items);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }
  }
}
