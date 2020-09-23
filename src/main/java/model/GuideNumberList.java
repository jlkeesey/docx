package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a numbered list.
 */
public class GuideNumberList extends GuideBase {
  public static final int MAX_LEVELS = 10;

  private final int level;

  private final NumberType numberType;

  public GuideNumberList(int level, NumberType numberType, @NotNull Iterable<GuideBase> iterable) {
    super(iterable);
    this.level = level;
    this.numberType = numberType;
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  public int level() {
    return this.level;
  }

  public NumberType numberedType() {
    return numberType;
  }

  @Override
  public void visit(@NotNull GuideVisitor visitor, int index) {
    visitor.start(this, index);
    visitItems(visitor);
    visitor.end(this, index);
  }

  public enum NumberType {
    LowerCase, UpperCase, LowerRoman, UpperRoman, Numbers
  }

  public static class Builder extends GuideBase.BuilderBase<GuideNumberList, Builder> {
    private int level = 0;
    private NumberType type = NumberType.Numbers;

    public @NotNull GuideNumberList build() {
      return new GuideNumberList(level, type, items);
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

    public NumberType numberType() {
      return type;
    }

    public @NotNull Builder numberType(NumberType type) {
      this.type = type;
      return this;
    }
  }
}