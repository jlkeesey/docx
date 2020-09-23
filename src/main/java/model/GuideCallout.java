package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a callout.
 */
public class GuideCallout extends GuideBase {
  private final CalloutType calloutType;
  private final String title;

  public GuideCallout(CalloutType calloutType, String title, @NotNull Iterable<GuideBase> iterable) {
    super(iterable);
    this.calloutType = calloutType;
    this.title = title;
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  public CalloutType calloutType() {
    return calloutType;
  }

  public String title() {
    return title;
  }

  @Override
  public void visit(@NotNull GuideVisitor visitor, int index) {
    visitor.start(this, index);
    visitItems(visitor);
    visitor.end(this, index);
  }

  public enum CalloutType {
    Note, Caution, Warning, Other
  }

  public static class Builder extends GuideBase.BuilderBase<GuideCallout, Builder> {
    private CalloutType calloutType = CalloutType.Other;
    private String title = "";

    @Override
    public @NotNull GuideCallout build() {
      return new GuideCallout(calloutType, title, items);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }

    public CalloutType calloutType() {
      return calloutType;
    }

    public Builder calloutType(CalloutType calloutType) {
      this.calloutType = calloutType;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public String title() {
      return title;
    }
  }
}
