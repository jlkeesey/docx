package model;

public abstract class GuideBase  {

  private final GuideType type;

  public GuideBase(GuideType type) {
    this.type = type;
  }

  public GuideType type() {
    return this.type;
  }

//  public abstract void visit
}
