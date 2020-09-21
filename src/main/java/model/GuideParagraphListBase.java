package model;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An abstraction for a model object that contains/is a list of paragraphs.
 */
public abstract class GuideParagraphListBase extends GuideBase implements Iterable<GuideParagraph> {
  protected final ImmutableList<GuideParagraph> items;

  protected GuideParagraphListBase(GuideType type, @NotNull Iterable<GuideParagraph> iterable) {
    super(type);
    this.items = ImmutableList.copyOf(iterable);
  }

  /**
   * Returns the number of paragraphs.
   */
  public int size() {
    return items.size();
  }

  /**
   * Returns a iterator over the {@code GuideTextItem} objects.
   */
  @Override
  public @NotNull Iterator<GuideParagraph> iterator() {
    return items.iterator();
  }

  protected void visitItems(GuideVisitor visitor) {
    for (int i = 0; i < items.size(); i++) {
      items.get(i).visit(visitor, i);
    }
  }

  protected static abstract class BuilderBase<T, B> {
    protected @NotNull List<GuideParagraph> items = new ArrayList<>();

    public abstract @NotNull T build();

    public @NotNull B add(GuideParagraph value) {
      items.add(value);
      return getThis();
    }

    /**
     * Returns this downcasted to the child type. Helper to get around the downcasting problems with generics in Java.
     */
    protected abstract @NotNull B getThis();

    public @NotNull B addAll(@NotNull Iterable<GuideParagraph> iterable) {
      for (GuideParagraph p : iterable) {
        items.add(p);
      }
      return getThis();
    }

    public @NotNull B clear() {
      items.clear();
      return getThis();
    }
  }
}
