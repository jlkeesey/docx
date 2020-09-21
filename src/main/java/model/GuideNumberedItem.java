package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an item in a numbered list.
 */
public class GuideNumberedItem extends GuideParagraphListBase {
  public GuideNumberedItem(Iterable<GuideParagraph> iterable) {
    super(GuideType.NumberedItem, iterable);
  }

  public static @NotNull GuideBulletItem.Builder builder() {
    return new GuideBulletItem.Builder();
  }

  @Override
  public void visit(GuideVisitor visitor, int index) {
    visitor.start(this, index);
    visitItems(visitor);
    visitor.end(this, index);
  }

  public static class Builder extends BuilderBase<GuideNumberedItem, Builder> {
    @Override
    public @NotNull GuideNumberedItem build() {
      return new GuideNumberedItem(items);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }
  }
}