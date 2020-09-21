package model;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuideTextItemTest {

  @Test
  void createString() {
    GuideTextItem item = GuideTextItem.create("A test");

    assertEquals("A test", item.text());
    assertFalse(item.isBold());
    assertFalse(item.isItalic());
  }

  @Test
  void createStringWithAttributed() {
    GuideTextItem item = GuideTextItem.create("A test", true, true);

    assertEquals("A test", item.text());
    assertTrue(item.isBold());
    assertTrue(item.isItalic());
  }

  @Test
  void asList() {
    ImmutableList<GuideTextItem> item = GuideTextItem.create("A test").asList();

    assertNotNull(item);
    assertEquals(1, item.size());
    assertEquals("A test", item.get(0).text());
  }

  @Test
  void builderEmpty() {
    GuideTextItem item = GuideTextItem.builder().build();

    assertEquals("", item.text());
    assertFalse(item.isBold());
    assertFalse(item.isItalic());
  }

  @Test
  void builderText() {
    GuideTextItem item = GuideTextItem.builder().text("A test").build();

    assertEquals("A test", item.text());
    assertFalse(item.isBold());
    assertFalse(item.isItalic());
  }

  @Test
  void builderTextItalic() {
    GuideTextItem item = GuideTextItem.builder().text("A test").italic(true).build();

    assertEquals("A test", item.text());
    assertFalse(item.isBold());
    assertTrue(item.isItalic());
  }

  @Test
  void builderTextBold() {
    GuideTextItem item = GuideTextItem.builder().text("A test").bold(true).build();

    assertEquals("A test", item.text());
    assertTrue(item.isBold());
    assertFalse(item.isItalic());
  }

  @Test
  void visit() {
    GuideTextItem item = GuideTextItem.create("Test");
    TestVisitor visitor = new TestVisitor();

    item.visit(visitor, 1);
    item.visit(visitor, 20);

    ImmutableList<String> expected = ImmutableList.of("1: Test", "20: Test");
    assertIterableEquals(expected, visitor.calls);
  }
}