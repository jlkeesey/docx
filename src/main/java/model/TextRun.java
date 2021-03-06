package model;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.Immutable;

@Immutable
public class TextRun {
  private final String text;
  private final boolean italic;
  private final boolean bold;

  private TextRun(String text, boolean italic, boolean bold) {
    this.text = text;
    this.italic = italic;
    this.bold = bold;
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  public static @NotNull TextRun create(String text) {
    return create(text, false, false);
  }

  public static @NotNull TextRun create(String text, boolean italic, boolean bold) {
    return new TextRun(text, italic, bold);
  }

  public @NotNull ImmutableList<TextRun> asList() {
    return ImmutableList.of(this);
  }

  public boolean isBold() {
    return bold;
  }

  public boolean isItalic() {
    return italic;
  }

  public String text() {
    return text;
  }

  public void visit(@NotNull GuideVisitor visitor, int index) {
    visitor.visit(this, index);
  }

  public static class Builder {
    private String text = "";
    private boolean italic;
    private boolean bold;

    public @NotNull TextRun build() {
      return new TextRun(text, italic, bold);
    }

    public @NotNull Builder text(String value) {
      this.text = value;
      return this;
    }

    public @NotNull Builder italic(boolean value) {
      this.italic = value;
      return this;
    }

    public @NotNull Builder bold(boolean value) {
      this.bold = value;
      return this;
    }

    public String text() {
      return text;
    }

    public boolean isItalic() {
      return italic;
    }

    public boolean isBold() {
      return bold;
    }
  }
}
