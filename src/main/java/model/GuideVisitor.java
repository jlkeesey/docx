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

  default void start(GuideBulletList element, int index) {
  }

  default void end(GuideBulletList element, int index) {
  }

  default void start(GuideBulletListItem element, int index) {
  }

  default void end(GuideBulletListItem element, int index) {
  }

  default void start(GuideCallout element, int index) {
  }

  default void end(GuideCallout element, int index) {
  }

  default void start(GuideParagraph element, int index) {
  }

  default void end(GuideParagraph element, int index) {
  }

  default void start(GuideNumberList element, int index) {
  }

  default void end(GuideNumberList element, int index) {
  }

  default void start(GuideNumberListItem element, int index) {
  }

  default void end(GuideNumberListItem element, int index) {
  }

  default void start(GuideTable element, int index) {
  }

  default void end(GuideTable element, int index) {
  }

  default void start(GuideTableRow element, int index) {
  }

  default void end(GuideTableRow element, int index) {
  }

  default void start(GuideTableCell element, int index) {
  }

  default void end(GuideTableCell element, int index) {
  }

  default void start(Paragraph element, int index) {
  }

  default void end(Paragraph element, int index) {
  }

  default void visit(TextRun element, int index) {
  }
}
