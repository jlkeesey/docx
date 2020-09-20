package model;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import util.IndentAppendable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides a single run of text items that comprises a paragraph.
 */
public class GuideParagraph implements Iterable<GuideTextItem>, GuideFormatable {
  private final ImmutableList<GuideTextItem> items;

  /**
   * Returns a new Builder object for object construction.
   */
  public static Builder builder() {
    return new Builder();
  }

  public static @NotNull GuideParagraph create(String text) {
    return create(GuideTextItem.create(text));
  }

  public static @NotNull GuideParagraph create(@NotNull GuideTextItem item) {
    return new GuideParagraph(item.asList());
  }

  private GuideParagraph(@NotNull Iterable<GuideTextItem> iterable) {
    items = ImmutableList.copyOf(iterable);
  }

  public ImmutableList<GuideParagraph> asList() {
    return ImmutableList.of(this);
  }

  public int size() {
    return items.size();
  }

  @Override
  public @NotNull Iterator<GuideTextItem> iterator() {
    return items.iterator();
  }

  @Override
  public @NotNull String toString() {
    StringBuilder builder = new StringBuilder();
    format(builder);
    return builder.toString();
  }

  @Override
  public void format(IndentAppendable appendable) {
    items.forEach(item -> item.format(appendable));
    appendable.endLine();
  }

  public static class Builder {
    private final List<GuideTextItem> items = new ArrayList<>();

    public @NotNull GuideParagraph build() {
      return new GuideParagraph(items);
    }

    public @NotNull Builder add(GuideTextItem item) {
      items.add(item);
      return this;
    }
  }
}
