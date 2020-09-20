package model;

import com.google.common.collect.ImmutableList;
import util.IndentAppendable;

import javax.annotation.concurrent.Immutable;

@Immutable
public class GuideTextItem implements GuideFormatable {
  private final String text;
  private final boolean italic;
  private final boolean bold;

  private GuideTextItem(String text, boolean italic, boolean bold) {
    this.text = text;
    this.italic = italic;
    this.bold = bold;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static GuideTextItem create(String text) {
    return create(text, false, false);
  }

  public static GuideTextItem create(String text, boolean italic, boolean bold) {
    return new GuideTextItem(text, italic, bold);
  }

  public ImmutableList<GuideTextItem> asList() {
    return ImmutableList.of(this);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    format(builder);
    return builder.toString();
  }

  @Override
  public void format(IndentAppendable appendable) {
    if (isBold()) {
      appendable.append("**");
    }
    if (isItalic()) {
      appendable.append('_');
    }
    appendable.append(text());
    if (isItalic()) {
      appendable.append('_');
    }
    if (isBold()) {
      appendable.append("**");
    }
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

  public static class Builder {
    private String text = "";
    private boolean italic;
    private boolean bold;

    public GuideTextItem build() {
      return new GuideTextItem(text, italic, bold);
    }

    public Builder text(String value) {
      this.text = value;
      return this;
    }

    public Builder italic(boolean value) {
      this.italic = value;
      return this;
    }

    public Builder bold(boolean value) {
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
