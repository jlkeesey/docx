package model;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Provides a single run of text items that comprises a paragraph.
 */
@Immutable
public class GuideParagraph implements Iterable<GuideTextItem> {
  private final ImmutableList<GuideTextItem> items;

  private GuideParagraph(@NotNull Iterable<GuideTextItem> iterable) {
    items = ImmutableList.copyOf(iterable);
  }

  /**
   * Returns a new Builder object for object construction.
   */
  public static @NotNull Builder builder() {
    return new Builder();
  }

  public @NotNull ImmutableList<GuideParagraph> asList() {
    return ImmutableList.of(this);
  }

  public int size() {
    return items.size();
  }

  @Override
  public @NotNull Iterator<GuideTextItem> iterator() {
    return items.iterator();
  }

  public void visit(@NotNull GuideVisitor visitor, int index) {
    visitor.start(this, index);
    for (int i = 0; i < items.size(); i++) {
      items.get(i).visit(visitor, i);
    }
    visitor.end(this, index);
  }

  public static class Builder {
    private final ArrayList<GuideTextItem> items = new ArrayList<>();

    public @NotNull GuideParagraph build() {
      return new GuideParagraph(items);
    }

    public @NotNull Builder add(GuideTextItem item) {
      items.add(item);
      return this;
    }

    public @NotNull Builder addAll(@NotNull Iterable<GuideTextItem> iterable) {
      for (GuideTextItem item : iterable) {
        items.add(item);
      }
      return this;
    }

    public @NotNull Builder clear() {
      items.clear();
      return this;
    }
  }
}
