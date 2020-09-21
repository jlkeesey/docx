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
  public void start(GuideBullets element, int index) {
    calls.add(String.format("%d: Start bullets", index));
  }

  @Override
  public void end(GuideBullets element, int index) {
    calls.add(String.format("%d: End bullets", index));
  }

  @Override
  public void start(GuideBulletItem element, int index) {
    calls.add(String.format("%d: Start bullet item", index));
  }

  @Override
  public void end(GuideBulletItem element, int index) {
    calls.add(String.format("%d: End bullet item", index));
  }

  @Override
  public void start(GuideCaution element, int index) {
    calls.add(String.format("%d: Start caution", index));
  }

  @Override
  public void end(GuideCaution element, int index) {
    calls.add(String.format("%d: End caution", index));
  }

  @Override
  public void start(GuideDescription element, int index) {
    calls.add(String.format("%d: Start description", index));
  }

  @Override
  public void end(GuideDescription element, int index) {
    calls.add(String.format("%d: End description", index));
  }

  @Override
  public void start(GuideNote element, int index) {
    calls.add(String.format("%d: Start note", index));
  }

  @Override
  public void end(GuideNote element, int index) {
    calls.add(String.format("%d: End note", index));
  }

  @Override
  public void start(GuideNumbered element, int index) {
    calls.add(String.format("%d: Start numbered", index));
  }

  @Override
  public void end(GuideNumbered element, int index) {
    calls.add(String.format("%d: End numbered", index));
  }

  @Override
  public void start(GuideNumberedItem element, int index) {
    calls.add(String.format("%d: Start numbered item", index));
  }

  @Override
  public void end(GuideNumberedItem element, int index) {
    calls.add(String.format("%d: End numbered item", index));
  }

  @Override
  public void start(GuideWarning element, int index) {
    calls.add(String.format("%d: Start warning", index));
  }

  @Override
  public void end(GuideWarning element, int index) {
    calls.add(String.format("%d: End warning", index));
  }

  @Override
  public void start(GuideParagraph element, int index) {
    calls.add(String.format("%d: Start paragraph", index));
  }

  @Override
  public void end(GuideParagraph element, int index) {
    calls.add(String.format("%d: End paragraph", index));
  }

  @Override
  public void visit(GuideTextItem element, int index) {
    calls.add(String.format("%d: %s", index, element.text()));
  }
}
