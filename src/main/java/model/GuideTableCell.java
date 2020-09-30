package model;

import org.jetbrains.annotations.NotNull;

public class GuideTableCell extends GuideBase {
  protected GuideTableCell(@NotNull Iterable<GuideBase> iterable) {
    super(iterable);
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public void visit(GuideVisitor visitor, int index) {
    visitor.start(this, index);
    visitItems(visitor);
    visitor.end(this, index);
  }

  public static class Builder extends GuideBase.BuilderBase<GuideTableCell, Builder> {

    @Override
    public @NotNull GuideTableCell build() {
      return new GuideTableCell(items);
    }

    @Override
    public boolean accept(GuideBase item) {
      return !(item instanceof Guide) &&
             !(item instanceof GuideSection) &&
             !(item instanceof GuideTable) &&
             !(item instanceof GuideTableRow) &&
             !(item instanceof GuideTableCell);
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }
  }
}
