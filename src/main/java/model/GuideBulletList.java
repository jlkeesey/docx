package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a list of bulleted items.
 */
public class GuideBulletList extends GuideBase {
  public static final int MAX_LEVELS = 10;

  private final int level;

  public GuideBulletList(int level, @NotNull Iterable<GuideBase> iterable) {
    super(iterable);
    this.level = level;
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  public int level() {
    return this.level;
  }

  @Override
  public void visit(@NotNull GuideVisitor visitor, int index) {
    visitor.start(this, index);
    visitItems(visitor);
    visitor.end(this, index);
  }

  public static class Builder extends GuideBase.BuilderBase<GuideBulletList, Builder> {
    private int level = 0;

    public @NotNull GuideBulletList build() {
      return new GuideBulletList(level, items);
    }

    @Override
    public boolean accept(GuideBase item) {
      return Acceptance.listAcceptance(item, level);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }

    public Builder level(int level) {
      this.level = level;
      return this;
    }

    public int level() {
      return level;
    }
  }
}
