package model;

/**
 * Defines te contract for a visitor over the objects in a Guide.
 */
public interface GuideVisitor {
  /**
   * Called once at the start of the {@link Guide}.
   *
   * @param element the {@code Guide} being visited.
   */
  default void start(Guide element) {
  }

  /**
   * Called once at the end of the {@link Guide}.
   *
   * @param element the {@code Guide} being visited.
   */
  default void end(Guide element) {
  }

  default void start(GuideSection element, String name, int index) {
  }

  default void end(GuideSection element, String name, int index) {
  }

  default void start(GuideBullets element, int index) {
  }

  default void end(GuideBullets element, int index) {
  }

  default void start(GuideBulletItem element, int index) {
  }

  default void end(GuideBulletItem element, int index) {
  }

  default void start(GuideCaution element, int index) {
  }

  default void end(GuideCaution element, int index) {
  }

  default void start(GuideDescription element, int index) {
  }

  default void end(GuideDescription element, int index) {
  }

  default void start(GuideNote element, int index) {
  }

  default void end(GuideNote element, int index) {
  }

  default void start(GuideNumbered element, int index) {
  }

  default void end(GuideNumbered element, int index) {
  }

  default void start(GuideNumberedItem element, int index) {
  }

  default void end(GuideNumberedItem element, int index) {
  }

  default void start(GuideWarning element, int index) {
  }

  default void end(GuideWarning element, int index) {
  }

  default void start(GuideParagraph element, int index) {
  }

  default void end(GuideParagraph element, int index) {
  }

  default void visit(GuideTextItem element, int index) {
  }
}
