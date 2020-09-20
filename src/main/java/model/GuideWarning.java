package model;

/**
 * Represents a Warning callout.
 */
public class GuideWarning extends GuideCallout {
  public static Builder builder() {
    return new Builder();
  }

  public GuideWarning(Iterable<GuideParagraph> items) {
    super(GuideType.Warning, items);
  }

  public static GuideWarning create(String text) {
    return create(GuideParagraph.create(text).asList());
  }

  public static GuideWarning create(Iterable<GuideParagraph> items) {
    return new GuideWarning(items);
  }

  public static class Builder
      extends GuideParagraphListBase.BuilderBase<GuideWarning.Builder, GuideWarning, GuideParagraph> {
    @Override
    public GuideWarning build() {
      return new GuideWarning(items);
    }
  }
}
