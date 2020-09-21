package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a step in a guide. Steps can be nested inside other steps to create sub-steps. Steps contain items like
 * {@link GuideDescription} and {@link GuideCaution} to describe the step.
 */
public class GuideStep extends GuideBase implements Iterable<GuideBase> {
  private final List<GuideBase> items = new ArrayList<>();
  private final String name;
  private final String title;

  /**
   * Creates a Step. The name is the unique step "number" while the title is any descriptive text associated. For a
   * sub-step, the name may or may not include the parent step name as a prefix.
   */
  public GuideStep(String name, String title) {
    super(GuideType.Step);
    this.name = name;
    this.title = title;
  }

  /**
   * Adds a new item to the step.
   */
  public void add(GuideBase item) {
    items.add(item);
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
  public void visit(GuideVisitor visitor, int index) {
    visitor.start(this, name, index);
    for (int i = 0; i < items.size(); i++) {
      items.get(i).visit(visitor, i);
    }
    visitor.end(this, name, index);
  }
}
