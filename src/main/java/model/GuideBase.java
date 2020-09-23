package model;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class GuideBase implements Iterable<GuideBase> {
  protected @NotNull final ImmutableList<GuideBase> items;

  protected GuideBase(@NotNull Iterable<GuideBase> iterable) {
    this.items = ImmutableList.copyOf(iterable);
  }

  public abstract void visit(GuideVisitor visitor, int index);

  @NotNull
  @Override
  public Iterator<GuideBase> iterator() {
    return items.iterator();
  }

  protected void visitItems(@NotNull GuideVisitor visitor) {
    for (int i = 0; i < items.size(); i++) {
      items.get(i)
           .visit(visitor, i);
    }
  }

  protected static abstract class BuilderBase<T extends GuideBase, B> implements GuideBaseBuilder<B> {
    protected @NotNull List<GuideBase> items = new ArrayList<>();

    public abstract @NotNull T build();

    public @NotNull B add(GuideBase value) {
      if (accept(value)) {
        items.add(value);
      }
      return getThis();
    }

    public @NotNull B addAll(@NotNull Iterable<GuideBase> iterable) {
      iterable.forEach(this::add);
      return getThis();
    }

    public @NotNull B addAll(@NotNull Iterator<GuideBase> iterator) {
      iterator.forEachRemaining(this::add);
      return getThis();
    }

    public @NotNull B clear() {
      items.clear();
      return getThis();
    }

    public boolean accept(GuideBase item) {
      return !(item instanceof Guide);
    }

    public <U> int typeCount(Class<U> clazz) {
      return Math.toIntExact(items.stream()
                                  .filter(clazz::isInstance)
                                  .count());
    }

    public int sectionCount() {
      return typeCount(GuideSection.class);
    }

    /**
     * Returns this downcasted to the child type. Helper to get around the downcasting problems with generics in Java.
     */
    protected abstract @NotNull B getThis();
  }
}
