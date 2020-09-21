package model;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a numbered list. Currently we only support lists that are numbered with arabic numbers, sequentially,
 * starting with 1.
 */
public class GuideNumbered extends GuideBase implements Iterable<GuideNumberedItem> {
  private final ImmutableList<GuideNumberedItem> items;

  public GuideNumbered(Iterable<GuideNumberedItem> iterable) {
    super(GuideType.Numbered);
    this.items = ImmutableList.copyOf(iterable);
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  @Override
  public void visit(GuideVisitor visitor, int index) {
    visitor.start(this, index);
    for (int i = 0; i < items.size(); i++) {
      items.get(i).visit(visitor, i);
    }
    visitor.end(this, index);
  }

  @NotNull
  @Override
  public Iterator<GuideNumberedItem> iterator() {
    return items.iterator();
  }

  public static class Builder {
    protected @NotNull List<GuideNumberedItem> items = new ArrayList<>();

    public @NotNull GuideNumbered build() {
      return new GuideNumbered(items);
    }

    public @NotNull Builder add(GuideNumberedItem value) {
      items.add(value);
      return this;
    }

    public @NotNull Builder addAll(@NotNull Iterable<GuideNumberedItem> iterable) {
      for (GuideNumberedItem p : iterable) {
        items.add(p);
      }
      return this;
    }

    public @NotNull Builder clear() {
      items.clear();
      return this;
    }
  }
}