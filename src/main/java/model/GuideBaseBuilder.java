package model;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface GuideBaseBuilder<B> {
  boolean accept(GuideBase item);

  @NotNull B add(GuideBase value);

  @NotNull B addAll(@NotNull Iterator<GuideBase> iterator);

  @NotNull B addAll(@NotNull Iterable<GuideBase> iterable);

  @NotNull GuideBase build();

  @NotNull B clear();

  /**
   * Returns the number of sections in this builder. If this builder cannot have sections then -1 is returned.
   * <p>
   * This is kind of a cheat to reduce the instance testing for a specific problem but it simplifies the code.
   */
  default int sectionCount() {
    return -1;
  }
}
