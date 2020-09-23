package docx;

import model.*;

import java.util.ArrayList;
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

  public GuideBaseBuilder<?> peek() {
    return builders.peek();
  }

  public void pop() {
    if (builders.size() > 1) {
      GuideBaseBuilder<?> builder = builders.pop();
      top().add(builder.build());
      if (builder instanceof GuideBulletList.Builder) {
        bulletLevel = ((GuideBulletList.Builder) builder).level() - 1;
      } else if (builder instanceof GuideNumberList.Builder) {
        numberLevel = ((GuideNumberList.Builder) builder).level() - 1;
      }
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

  public void push(GuideBaseBuilder<?> builder) {
    builders.add(builder);
    if (builder instanceof GuideBulletList.Builder) {
      bulletLevel = ((GuideBulletList.Builder) builder).level();
    } else if (builder instanceof GuideNumberList.Builder) {
      numberLevel = ((GuideNumberList.Builder) builder).level();
    }
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
}
