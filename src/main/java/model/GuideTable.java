package model;

import org.jetbrains.annotations.NotNull;

public class GuideTable extends GuideBase {
  private GuideTable(@NotNull Iterable<GuideBase> iterable) {
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

  public static class Builder extends GuideBase.BuilderBase<GuideTable, Builder> {

    @Override
    public @NotNull GuideTable build() {
      return new GuideTable(items);
    }

    @Override
    public boolean accept(GuideBase item) {
      return item instanceof GuideTableRow;
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }
  }
}
