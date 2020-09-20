package model;

/**
 * Represents a block of descriptive text. Each block/paragraph will be represented by a separate object.
 */
public class GuideDescription extends GuideParagraphListBase<GuideParagraph> {
  public static Builder builder() {
    return new Builder();
  }

  public static GuideDescription create(String text) {
    return create(GuideParagraph.create(text).asList());
  }

  public static GuideDescription create(Iterable<GuideParagraph> items) {
    return new GuideDescription(items);
  }

  private GuideDescription(Iterable<GuideParagraph> items) {
    super(GuideType.Description, items);
  }

  public static class Builder extends GuideParagraphListBase.BuilderBase<Builder, GuideDescription, GuideParagraph> {
    @Override
    public GuideDescription build() {
      return new GuideDescription(items);
    }
  }
}
