package model;

import util.IndentAppendable;

/**
 * An object that can format itself.
 */
public interface GuideFormatable {
  /**
   * Formats this object into the given {@link Appendable}.
   */
  default void format(Appendable appendable) {
    format(new IndentAppendable((appendable)));
  }

  /**
   * Formats this object into the given {@link IndentAppendable}.
   */
  void format(IndentAppendable appendable);
}
