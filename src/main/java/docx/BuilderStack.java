package docx;

import com.google.common.collect.ImmutableList;
import model.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class BuilderStack {
  private final Stack<GuideBaseBuilder<?>> builders = new Stack<>();
  private final Stack<Integer> listLevels = new Stack<>();

  public void add(GuideBase item) {
    while (size() > 1 && !top().accept(item)) {
      pop();
    }
    top().add(item);
  }

  public void close() {
    while (size() > 1) {
      pop();
    }
  }

  public ImmutableList<ListInfo> openLists() {
    return builders.stream()
                   .filter(b -> b instanceof GuideListBuilder)
                   .map(b -> new ListInfo(((GuideListBuilder) b).level(), ((GuideListBuilder) b).id()))
                   .collect(ImmutableList.toImmutableList());
  }

  public GuideBaseBuilder<?> peek() {
    return builders.peek();
  }

  public void pop() {
    if (builders.size() > 1) {
      GuideBaseBuilder<?> builder = builders.pop();
      top().add(builder.build());
    }
  }

  public void popUntilLevel(int level) {
    while (size() > 1) {
      if (top() instanceof GuideSection.Builder) {
        if (((GuideSection.Builder) top()).level() <= level) {
          break;
        }
      }
      pop();
    }
  }

  public boolean popUntilList(int level, int id) {
    ImmutableList<ListInfo> listInfos = openLists();
    if (listInfos.contains(new ListInfo(level, id))) {
      while (size() > 1) {
        if (top() instanceof GuideListBuilder) {
          GuideListBuilder glb = (GuideListBuilder) top();
          if (glb.level() == level && glb.id() == id) {
            return false;
          }
        }
        pop();
      }
    }
    return true;
  }

  public void push(GuideBaseBuilder<?> builder) {
    builders.add(builder);
  }

  public Integer[] sectionCounts() {
    ArrayList<Integer> counts = new ArrayList<>();
    builders.stream()
            .map(GuideBaseBuilder::sectionCount)
            .filter(c -> c >= 0)
            .forEach(counts::add);
    return counts.toArray(new Integer[0]);
  }

  public int size() {
    return builders.size();
  }

  public GuideBaseBuilder<?> top() {
    return peek();
  }

  public static final class ListInfo {
    private final int id;
    private final int level;

    public ListInfo(int level, int id) {
      this.level = level;
      this.id = id;
    }

    @Override
    public int hashCode() {
      return Objects.hash(level, id);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      ListInfo listInfo = (ListInfo) o;
      return level == listInfo.level && id == listInfo.id;
    }

    public int id() {
      return id;
    }

    public int level() {
      return level;
    }
  }
}
