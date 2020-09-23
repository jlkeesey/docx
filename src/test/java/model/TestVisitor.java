package model;

import java.util.ArrayList;

public class TestVisitor implements GuideVisitor {
  public ArrayList<String> calls = new ArrayList<>();

  @Override
  public void start(Guide element) {
    calls.add("Start Guide");
  }

  @Override
  public void end(Guide element) {
    calls.add("End Guide");
  }

  @Override
  public void start(GuideSection element, String name, int index) {
    calls.add(String.format("%d: Start section '%s'", index, element.name()));
  }

  @Override
  public void end(GuideSection element, String name, int index) {
    calls.add(String.format("%d: End section '%s'", index, element.name()));
  }

  @Override
  public void start(GuideBulletList element, int index) {
    calls.add(String.format("%d: Start bullets", index));
  }

  @Override
  public void end(GuideBulletList element, int index) {
    calls.add(String.format("%d: End bullets", index));
  }

  @Override
  public void start(GuideBulletListItem element, int index) {
    calls.add(String.format("%d: Start bullet item", index));
  }

  @Override
  public void end(GuideBulletListItem element, int index) {
    calls.add(String.format("%d: End bullet item", index));
  }

  @Override
  public void start(GuideCallout element, int index) {
    calls.add(String.format("%d: Start callout: %s",
                            index,
                            element.calloutType()
                                   .name()));
  }

  @Override
  public void end(GuideCallout element, int index) {
    calls.add(String.format("%d: End caution: %s",
                            index,
                            element.calloutType()
                                   .name()));
  }

  @Override
  public void start(GuideParagraph element, int index) {
    calls.add(String.format("%d: Start description", index));
  }

  @Override
  public void end(GuideParagraph element, int index) {
    calls.add(String.format("%d: End description", index));
  }

  @Override
  public void start(GuideNumberList element, int index) {
    calls.add(String.format("%d: Start numbered", index));
  }

  @Override
  public void end(GuideNumberList element, int index) {
    calls.add(String.format("%d: End numbered", index));
  }

  @Override
  public void start(GuideNumberListItem element, int index) {
    calls.add(String.format("%d: Start numbered item", index));
  }

  @Override
  public void end(GuideNumberListItem element, int index) {
    calls.add(String.format("%d: End numbered item", index));
  }

  @Override
  public void start(Paragraph element, int index) {
    calls.add(String.format("%d: Start paragraph", index));
  }

  @Override
  public void end(Paragraph element, int index) {
    calls.add(String.format("%d: End paragraph", index));
  }

  @Override
  public void visit(TextRun element, int index) {
    calls.add(String.format("%d: %s", index, element.text()));
  }
}
