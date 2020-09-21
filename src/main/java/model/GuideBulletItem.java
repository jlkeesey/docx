package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a list of bulleted items. We currently don't support tacking the type of bullet.
 */
public class GuideBulletItem extends GuideParagraphListBase {
  public GuideBulletItem(Iterable<GuideParagraph> iterable) {
    super(GuideType.BulletItem, iterable);
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  @Override
  public void visit(GuideVisitor visitor, int index) {
    visitor.start(this, index);
    visitItems(visitor);
    visitor.end(this, index);
  }

  public static class Builder extends BuilderBase<GuideBulletItem, Builder> {
    @Override
    public @NotNull GuideBulletItem build() {
      return new GuideBulletItem(items);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }
  }
}
