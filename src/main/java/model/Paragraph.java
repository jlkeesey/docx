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
public class Paragraph implements Iterable<TextRun> {
  private final ImmutableList<TextRun> items;

  private Paragraph(@NotNull Iterable<TextRun> iterable) {
    items = ImmutableList.copyOf(iterable);
  }

  /**
   * Returns a new Builder object for object construction.
   */
  public static @NotNull Builder builder() {
    return new Builder();
  }

  public @NotNull ImmutableList<Paragraph> asList() {
    return ImmutableList.of(this);
  }

  public int size() {
    return items.size();
  }

  public String text() {
    StringBuilder builder = new StringBuilder();
    forEach(i -> builder.append(i.text()));
    return builder.toString();
  }

  @Override
  public @NotNull Iterator<TextRun> iterator() {
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
    private final ArrayList<TextRun> items = new ArrayList<>();

    public @NotNull Paragraph build() {
      return new Paragraph(items);
    }

    public @NotNull Builder add(TextRun item) {
      items.add(item);
      return this;
    }

    public @NotNull Builder addAll(@NotNull Iterable<TextRun> iterable) {
      for (TextRun item : iterable) {
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
