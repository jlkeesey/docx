package model;

import com.google.common.collect.ImmutableList;
import util.IndentAppendable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An abstraction for a model object that contains/is a list of paragraphs.
 */
public abstract class GuideParagraphListBase<T extends GuideFormatable> extends GuideBase implements Iterable<T> {
  protected final ImmutableList<T> paragraphs;

  protected GuideParagraphListBase(GuideType type, Iterable<T> iterable) {
    super(type);
    this.paragraphs = ImmutableList.copyOf(iterable);
  }

  /**
   * Returns the number of paragraphs.
   */
  public int size() {
    return paragraphs.size();
  }

  /**
   * Returns a iterator over the {@code GuideTextItem} objects.
   */
  @Override
  public Iterator<T> iterator() {
    return paragraphs.iterator();
  }

  @Override
  public void format(IndentAppendable appendable) {
    paragraphs.forEach(item -> item.format(appendable));
    appendable.endLine();
  }

  protected static abstract class BuilderBase<B extends BuilderBase<B,C,T>,C,T> {
    protected List<T> items = new ArrayList<>();

    public abstract C build();

    public B add(String text) {
      return add(GuideParagraph.create(text));
    }

    public abstract B add(GuideParagraph paragraph);

    public B add(T value) {
      items.add(value);
      return (B)this;
    }
  }
}
