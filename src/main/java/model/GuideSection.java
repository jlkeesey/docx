package model;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a section in a guide.
 */
public class GuideSection extends GuideBase implements Iterable<GuideBase> {
  private final String name;
  private final String title;
  private final ImmutableList<GuideBase> items;

  /**
   * Creates a section. The name is the unique section "number" while the title is any descriptive text associated.
   */
  public GuideSection(String name, String title, @NotNull Iterable<GuideBase> iterable) {
    super(GuideType.Section);
    this.name = name;
    this.title = title;
    this.items = ImmutableList.copyOf(iterable);
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  /**
   * Returns the name or "number" of the step.
   */
  public String name() {
    return name;
  }

  /**
   * Returns the title of the step.
   */
  public String title() {
    return title;
  }

  @Override
  public @NotNull Iterator<GuideBase> iterator() {
    return items.iterator();
  }

  @Override
  public void visit(@NotNull GuideVisitor visitor, int index) {
    visitor.start(this, name, index);
    for (int i = 0; i < items.size(); i++) {
      items.get(i)
           .visit(visitor, i);
    }
    visitor.end(this, name, index);
  }

  public static class Builder {
    private String name = "";
    private String title = "";
    private final ArrayList<GuideBase> items = new ArrayList<>();

    public @NotNull GuideSection build() {
      return new GuideSection(name, title, items);
    }

    public int sectionCount() {
      return Math.toIntExact(items.stream()
                                  .filter(i -> i.type() == GuideType.Section)
                                  .count());
    }

    public String name() {
      return name;
    }

    public @NotNull Builder name(String name) {
      this.name = name;
      return this;
    }

    public String title() {
      return title;
    }

    public @NotNull Builder title(String title) {
      this.title = title;
      return this;
    }

    public @NotNull Builder add(GuideBase item) {
      items.add(item);
      return this;
    }

    public @NotNull Builder addAll(@NotNull Iterable<GuideBase> iterable) {
      iterable.forEach(items::add);
      return this;
    }

    public @NotNull Builder addAll(@NotNull Iterator<GuideBase> iterator) {
      iterator.forEachRemaining(items::add);
      return this;
    }

    public @NotNull Builder clear() {
      items.clear();
      return this;
    }
  }
}
