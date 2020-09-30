package model;

import org.jetbrains.annotations.NotNull;

public class GuideTableRow extends GuideBase {
  protected GuideTableRow(@NotNull Iterable<GuideBase> iterable) {
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

  public static class Builder extends GuideBase.BuilderBase<GuideTableRow, Builder> {

    @Override
    public @NotNull GuideTableRow build() {
      return new GuideTableRow(items);
    }

    @Override
    public boolean accept(GuideBase item) {
      return item instanceof GuideTableCell;
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }
  }
}
