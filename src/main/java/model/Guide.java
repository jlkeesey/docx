package model;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a complete guide. A guide contains a sequence of guide steps which can contain other descriptive
 * information about the step including sub-steps.
 */
public class Guide implements Iterable<GuideStep> {
  private final ImmutableList<GuideStep> items;

  public Guide(Iterable<GuideStep> iterable) {
    this.items = ImmutableList.copyOf(iterable);
  }

  /**
   * Returns the number of items in this guide.
   */
  public int size() {
    return items.size();
  }

  @Override
  public @NotNull Iterator<GuideStep> iterator() {
    return items.iterator();
  }

  public static class Builder {
    private final List<GuideStep> items = new ArrayList<>();

    public Guide build() {
      return new Guide(items);
    }

    /**
     * Adds a new item to the guide.
     */
    public Builder add(GuideStep step) {
      items.add(step);
      return this;
    }

    /**
     * Adds a new item to the guide.
     */
    public Builder addAll(Iterable<GuideStep> iterable) {
      for (GuideStep step : iterable) {
        items.add(step);
      }
      return this;
    }

    public Builder clear() {
      items.clear();
      return this;
    }
  }
}
