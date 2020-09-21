package model;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a numbered list.
 */
public class GuideNumbered extends GuideBase implements Iterable<GuideNumberedItem> {
  private final ImmutableList<GuideNumberedItem> items;
  private final NumberedType numberedType;

  public GuideNumbered(NumberedType numberedType, @NotNull Iterable<GuideNumberedItem> iterable) {
    super(GuideType.Numbered);
    this.numberedType = numberedType;
    this.items = ImmutableList.copyOf(iterable);
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  @Override
  public void visit(@NotNull GuideVisitor visitor, int index) {
    visitor.start(this, index);
    for (int i = 0; i < items.size(); i++) {
      items.get(i).visit(visitor, i);
    }
    visitor.end(this, index);
  }

  public NumberedType numberedType() {
    return numberedType;
  }

  public enum NumberedType {
    LowerCase,
    UpperCase,
    LowerRoman,
    UpperRoman,
    Numbers
  }

  @NotNull
  @Override
  public Iterator<GuideNumberedItem> iterator() {
    return items.iterator();
  }

  public static class Builder {
    private NumberedType type = NumberedType.Numbers;
    protected @NotNull List<GuideNumberedItem> items = new ArrayList<>();

    public @NotNull GuideNumbered build() {
      return new GuideNumbered(type, items);
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

    public NumberedType type() {
      return type;
    }

    public @NotNull Builder type(NumberedType type) {
      this.type = type;
      return this;
    }
  }
}