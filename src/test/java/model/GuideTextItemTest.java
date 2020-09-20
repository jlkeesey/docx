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
//
//  @Test
//  void formatSimple() {
//    GuideTextItem item1 = GuideTextItem.create("A test");
//    StringBuilder builder = new StringBuilder();
//
//    item1.format(builder);
//
//    assertEquals("A test", builder.toString());
//  }
//
//  @Test
//  void formatItalic() {
//    GuideTextItem item1 = GuideTextItem.create("A test", true, false);
//    StringBuilder builder = new StringBuilder();
//
//    item1.format(builder);
//
//    assertEquals("_A test_", builder.toString());
//  }
//
//  @Test
//  void formatBold() {
//    GuideTextItem item1 = GuideTextItem.create("A test", false, true);
//    StringBuilder builder = new StringBuilder();
//
//    item1.format(builder);
//
//    assertEquals("**A test**", builder.toString());
//  }
//
//  @Test
//  void formatBoldItalic() {
//    GuideTextItem item1 = GuideTextItem.create("A test", true, true);
//    StringBuilder builder = new StringBuilder();
//
//    item1.format(builder);
//
//    assertEquals("**_A test_**", builder.toString());
//  }
}