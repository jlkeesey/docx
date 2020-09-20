package model;

import util.IndentAppendable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a complete guide. A guide contains a sequence of guide steps which can contain other descriptive
 * information about the step including sub-steps.
 */
public class Guide implements Iterable<GuideStep>, GuideFormatable {
  private final List<GuideStep> items = new ArrayList<>();

  /**
   * Adds a new item to the guide.
   */
  public void add(GuideStep step) {
    items.add(step);
  }

  /**
   * Returns the number of items in this guide.
   */
  public int size() {
    return items.size();
  }

  @Override
  public Iterator<GuideStep> iterator() {
    return items.iterator();
  }

  @Override
  public void format(IndentAppendable appendable) {
    items.forEach(item -> item.format(appendable));
  }
}
