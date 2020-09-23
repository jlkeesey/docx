package model;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a block of descriptive text. Each block/paragraph will be represented by a separate object.
 */
public class GuideParagraph extends GuideBase {
  private final Paragraph paragraph;

  private GuideParagraph(@NotNull Paragraph paragraph) {
    super(ImmutableList.of());
    this.paragraph = paragraph;
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  public String text() {
    return paragraph.text();
  }

  public Iterable<TextRun> textItemIterable() {
    return paragraph;
  }

  @Override
  public void visit(@NotNull GuideVisitor visitor, int index) {
    visitor.start(this, index);
    paragraph.visit(visitor, index);
    visitor.end(this, index);
  }

  public static class Builder extends GuideBase.BuilderBase<GuideParagraph, Builder> {
    private Paragraph paragraph = Paragraph.builder()
                                           .build();

    @Override
    public @NotNull GuideParagraph build() {
      return new GuideParagraph(paragraph);
    }

    @Override
    public boolean accept(GuideBase item) {
      return false;
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }

    public Paragraph paragraph() {
      return this.paragraph;
    }

    public Builder paragraph(Paragraph value) {
      this.paragraph = value;
      return this;
    }
  }
}
