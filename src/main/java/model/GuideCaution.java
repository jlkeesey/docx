package model;

/**
 * Represents a Caution callout.
 */
public class GuideCaution extends GuideCallout {
  public static Builder builder() {
    return new Builder();
  }

  public GuideCaution(Iterable<GuideParagraph> items) {
    super(GuideType.Caution, items);
  }

  public static GuideCaution create(String text) {
    return create(GuideParagraph.create(text).asList());
  }

  public static GuideCaution create(Iterable<GuideParagraph> items) {
    return new GuideCaution(items);
  }

  public static class Builder extends GuideParagraphListBase.BuilderBase<GuideCaution.Builder, GuideCaution, GuideParagraph> {
    @Override
    public GuideCaution build() {
      return new GuideCaution(items);
    }
  }
}
