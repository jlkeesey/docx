package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a section in a guide.
 */
public class GuideSection extends GuideBase {
  private final int level;
  private final String name;
  private final String title;

  /**
   * Creates a section.
   */
  public GuideSection(int level, String name, String title, @NotNull Iterable<GuideBase> iterable) {
    super(iterable);
    this.level = level;
    this.name = name;
    this.title = title;
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  public int level() {
    return level;
  }

  /**
   * Returns the name or "number" of the step.
   */
  public String name() {
    return name;
  }

  /**
   * Returns the title of the step.
   */
  public String title() {
    return title;
  }

  @Override
  public void visit(@NotNull GuideVisitor visitor, int index) {
    visitor.start(this, name, index);
    visitItems(visitor);
    visitor.end(this, name, index);
  }

  public static class Builder extends GuideBase.BuilderBase<GuideSection, Builder> {
    private int level = 0;
    private String name = "";
    private String title = "";

    public @NotNull GuideSection build() {
      return new GuideSection(level, name, title, items);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }

    public int level() {
      return level;
    }

    @SuppressWarnings("UnusedReturnValue")
    public Builder level(int level) {
      this.level = level;
      return this;
    }

    public String name() {
      return name;
    }

    @SuppressWarnings("UnusedReturnValue")
    public @NotNull Builder name(String name) {
      this.name = name;
      return this;
    }

    public String title() {
      return title;
    }

    @SuppressWarnings("UnusedReturnValue")
    public @NotNull Builder title(String title) {
      this.title = title;
      return this;
    }
  }
}
