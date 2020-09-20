package model;

/**
 * Represents a Note callout.
 */
public class GuideNote extends GuideCallout {
  public static Builder builder() {
    return new Builder();
  }

  public GuideNote(Iterable<GuideParagraph> items) {
    super(GuideType.Note, items);
  }

  public static GuideNote create(String text) {
    return create(GuideParagraph.create(text).asList());
  }

  public static GuideNote create(Iterable<GuideParagraph> items) {
    return new GuideNote(items);
  }

  public static class Builder extends GuideParagraphListBase.BuilderBase<GuideNote.Builder, GuideNote, GuideParagraph> {
    @Override
    public GuideNote build() {
      return new GuideNote(items);
    }
  }
}
