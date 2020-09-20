package util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class IndentAppendableTest {
  @Test
  void sequenceWithIndentation() throws IOException {
    StringBuilder builder = new StringBuilder();
    IndentAppendable ia = new IndentAppendable(builder);

    ia.append("<div>\n");
    ia.indent();
    ia.append("<p>\n");
    ia.indent();
    ia.append("Text\n");
    ia.outdent();;
    ia.append("</p>\n");
    ia.outdent();
    ia.append("</div>");

    final String expected = """
        <div>
          <p>
            Text
          </p>
        </div>""";

    assertEquals(expected, builder.toString());
  }

  @Test
  void appendWithWrap() throws IOException {
    StringBuilder builder = new StringBuilder();
    IndentAppendable ia = new IndentAppendable(builder);
    ia.setWrapLength(20);

    ia.append("1234567890123456789");

    assertEquals("1234567890123456789", builder.toString());

    ia.append("0");

    assertEquals("12345678901234567890\n", builder.toString());

    ia.append("1");

    assertEquals("12345678901234567890\n1", builder.toString());
  }

  @Test
  void appendSeq() throws IOException {
    StringBuilder builder = new StringBuilder();
    IndentAppendable ia = new IndentAppendable(builder);

    ia.append("This is a string");

    assertEquals("This is a string", builder.toString());

    ia.append("\nThis is another string");

    assertEquals("This is a string\nThis is another string", builder.toString());
  }

  @Test
  void appendSubSeq() throws IOException {
    StringBuilder builder = new StringBuilder();
    IndentAppendable ia = new IndentAppendable(builder);

    ia.append("xxxxThis is a stringzzzz", 4, 20);

    assertEquals("This is a string", builder.toString());
  }

  @Test
  void appendChar() throws IOException {
    StringBuilder builder = new StringBuilder();
    IndentAppendable ia = new IndentAppendable(builder);

    assertEquals("", builder.toString());

    ia.append('a');

    assertEquals("a", builder.toString());

    ia.append('b');
    ia.append('c');
    ia.append('d');

    assertEquals("abcd", builder.toString());

    ia.append('\n');
    ia.append('z');

    assertEquals("abcd\nz", builder.toString());
  }

  @Test
  void level() {
    StringBuilder builder = new StringBuilder();
    IndentAppendable ia = new IndentAppendable(builder);

    assertEquals(0, ia.level());
  }

  @Test
  void indent() {
    StringBuilder builder = new StringBuilder();
    IndentAppendable ia = new IndentAppendable(builder);

    ia.indent();

    assertEquals(1, ia.level());
    assertEquals("  ", ia.currentIndentation());

    ia.indent();

    assertEquals(2, ia.level());
    assertEquals("    ", ia.currentIndentation());
  }

  @Test
  void outdent() {
    StringBuilder builder = new StringBuilder();
    IndentAppendable ia = new IndentAppendable(builder);

    ia.indent();
    ia.indent();

    ia.outdent();

    assertEquals(1, ia.level());
    assertEquals("  ", ia.currentIndentation());

    ia.indent();
    ia.outdent();
    ia.outdent();

    assertEquals(0, ia.level());
    assertEquals("", ia.currentIndentation());

    ia.outdent();

    assertEquals(0, ia.level());
    assertEquals("", ia.currentIndentation());
  }

  @Test
  void setLevel() {
    StringBuilder builder = new StringBuilder();
    IndentAppendable ia = new IndentAppendable(builder);

    ia.setLevel(5);

    assertEquals(5, ia.level());
    assertEquals("  ".repeat(5), ia.currentIndentation());

    assertThrows(IndentException.class, () -> ia.setLevel(-1));
    assertThrows(IndentException.class, () -> ia.setLevel(IndentAppendable.MAX_LEVELS + 1));
  }

  @Test
  void setIndentation() {
    StringBuilder builder = new StringBuilder();
    IndentAppendable ia = new IndentAppendable(builder);

    assertEquals("  ", ia.indentation());

    ia.setIndentation(">>>>");

    assertEquals(">>>>", ia.indentation());
  }
}