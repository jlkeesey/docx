package model;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a list of bulleted items. We currently don't support tacking the type of bullet.
 */
public class GuideBullets extends GuideBase implements Iterable<GuideBulletItem> {
  private final ImmutableList<GuideBulletItem> items;

  public GuideBullets(Iterable<GuideBulletItem> iterable) {
    super(GuideType.Bullets);
    this.items = ImmutableList.copyOf(iterable);
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  @Override
  public void visit(GuideVisitor visitor, int index) {
    visitor.start(this, index);
    for (int i = 0; i < items.size(); i++) {
      items.get(i).visit(visitor, i);
    }
    visitor.end(this, index);
  }

  @NotNull
  @Override
  public Iterator<GuideBulletItem> iterator() {
    return items.iterator();
  }

  public static class Builder  {
    protected @NotNull List<GuideBulletItem> items = new ArrayList<>();

    public @NotNull GuideBullets build() {
      return new GuideBullets(items);
    }

    public @NotNull Builder add(GuideBulletItem value) {
      items.add(value);
      return this;
    }

    public @NotNull Builder addAll(@NotNull Iterable<GuideBulletItem> iterable) {
      for (GuideBulletItem p : iterable) {
        items.add(p);
      }
      return this;
    }

    public @NotNull Builder clear() {
      items.clear();
      return this;
    }
  }
}
