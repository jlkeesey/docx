package model;

public abstract class GuideBase implements GuideFormatable {

  private final GuideType type;

  public GuideBase(GuideType type) {
    this.type = type;
  }

  public GuideType type() {
    return this.type;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    format(builder);
    return builder.toString();
  }
}
