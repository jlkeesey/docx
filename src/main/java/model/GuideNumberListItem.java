package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an item in a numbered list.
 */
public class GuideNumberListItem extends GuideBase {
  private final int level;

  public GuideNumberListItem(int level, @NotNull Iterable<GuideBase> iterable) {
    super(iterable);
    this.level = level;
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

  public int level() {
    return level;
  }

  public static class Builder extends GuideBase.BuilderBase<GuideNumberListItem, Builder> {
    private int level = 0;

    @Override
    public @NotNull GuideNumberListItem build() {
      return new GuideNumberListItem(level, items);
    }

    @Override
    public boolean accept(GuideBase item) {
      return Acceptance.listItemAcceptance(item, level);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }

    public int level() {
      return level;
    }

    public @NotNull Builder level(int level) {
      if (level < 0 || GuideNumberList.MAX_LEVELS < level) {
        throw new GuideException("GuideNumberList level out of range: " + level);
      }
      this.level = level;
      return this;
    }
  }
}