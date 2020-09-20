package util;

import java.io.IOException;

/**
 * Provides an {@link Appendable} that supports simple indentation.
 */
public class IndentAppendable implements Appendable {
  public static final int MAX_LEVELS = 20;

  private int level = 0;
  private String indentation = "  ";
  private String currentIndentation = "";
  private final Appendable target;
  private int lineLength = 0;
  private int wrapLength = 0;

  /**
   * Constructs the {@code IndentAppendable} using the given {@code Appendable} as the target.
   *
   * @param target the {@code Appendable} to write to.
   */
  public IndentAppendable(Appendable target) {
    this.target = target;
  }

  @Override
  public IndentAppendable append(CharSequence csq) {
    append(csq, 0, csq.length());
    return this;
  }

  @Override
  public IndentAppendable append(CharSequence csq, int start, int end) {
    for (int i = start; i < end; i++) {
      append(csq.charAt(i));
    }
    return this;
  }

  @Override
  public IndentAppendable append(char c) {
    if (c == '\n') {
      appendNewline();
    } else {
      appendChar(c);
    }
    return this;
  }

  /**
   * Handles appending one character (not a newline). If this is the first character added to a line then the current
   * indentation is appended first.
   *
   * @param c the character to append.
   */
  private void appendChar(char c) {
    try {
      if (lineLength == 0) {
        target.append(currentIndentation);
        lineLength += currentIndentation.length();
      }
      target.append(c);
      lineLength++;
      if (wrapLength > 0 && lineLength >= wrapLength) {
        appendNewline();
      }
    } catch (IOException e) {
      throw new IndentException(e);
    }
  }

  /**
   * Handles a newline. We append the newline an setup for indenting the next line if any.
   */
  private void appendNewline() {
    try {
      target.append('\n');
      lineLength = 0;
    } catch (IOException e) {
      throw new IndentException(e);
    }
  }

  /**
   * Returns the current indentation level. 0 indicates no indentation.
   */
  public int level() {
    return level;
  }

  /**
   * Increases the indentation level by 1. If the level is at {@link #MAX_LEVELS}, nothing happens.
   */
  public void indent() {
    if (level < MAX_LEVELS) {
      setLevel(level + 1);
    }
  }

  /**
   * Decreases the indentation level by 1. If the level is at zero, nothing happens.
   */
  public void outdent() {
    if (0 < level) {
      setLevel(level - 1);
    }
  }

  /**
   * Sets an absolute indentation level.
   *
   * @param level the new level in the range [0, {@link #MAX_LEVELS}].
   *
   * @throws IndentException if level is out of range.
   */
  public void setLevel(int level) {
    if (0 <= level && level <= MAX_LEVELS) {
      this.level = level;
      currentIndentation = indentation.repeat(level);
    } else {
      throw new IndentException("level out of range");
    }
  }

  /**
   * Returns the indent string for each level. This string will be replicated level times at the beginning of each
   * line.
   */
  public String indentation() {
    return indentation;
  }

  /**
   * Sets the indent string for each level. This will only affect any new lines added, existing lines will not be
   * updated.
   */
  public void setIndentation(String indentation) {
    this.indentation = indentation;
  }

  /**
   * Returns the current indent string. This is the indentation string repeated level times.
   */
  public String currentIndentation() {
    return currentIndentation;
  }

  /**
   * Returns the current line length including any indentation. Because of the way this object behaves, adding a single
   * character mey add more than one character.
   */
  public int lineLength() {
    return lineLength;
  }

  /**
   * Returns the current wrap length. If a line gets equal to or longer than this length a newline will be automatically
   * added.
   */
  public int wrapLength() {
    return wrapLength;
  }

  /**
   * Sets the wrap length. Set this to 0 to disable.
   */
  public void setWrapLength(int wrapLength) {
    if (wrapLength < 0) {
      throw new IndentException("wrapLength must be >= 0");
    }
    this.wrapLength = wrapLength;
  }

  /**
   * Ends the current line if there are any characters on it.
   */
  public IndentAppendable endLine() {
    if (lineLength > 0) {
      appendNewline();
    }
    return this;
  }
}
