package docx;

import model.*;
import model.GuideCallout.CalloutType;
import model.GuideNumberList.NumberType;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

public class DocxGuideReader {
  public static final String NUMBER_FORMAT_BULLET = "bullet";

  private final BuilderStack builders = new BuilderStack();
  private final XWPFDocument doc;

  public DocxGuideReader(InputStream input) {
    try {
      doc = new XWPFDocument(input);
    } catch (IOException e) {
      throw new DocxReadException(e);
    }
  }

  public Guide read() {
    return read(new DocxReaderConfig());
  }

  public Guide read(DocxReaderConfig config) {
    Guide.Builder guideBuilder = Guide.builder();
    builders.push(guideBuilder);
    setDocumentFrontMatter(config, guideBuilder);
    processDocument(config);
    return guideBuilder.build();
  }

  private String getOutlineLevel(DocxReaderConfig config, XWPFParagraph paragraph) {
    CTP ctp = paragraph.getCTP();
    if (ctp != null) {
      CTPPr ppr = ctp.getPPr();
      if (ppr != null) {
        CTDecimalNumber outlineLvl = ppr.getOutlineLvl();
        if (outlineLvl != null) {
          BigInteger val = outlineLvl.getVal();
          if (val != null) {
            return val.toString();
          }
        }
      }
    }
    return "-";
  }

  private void makeBulletListItem(int level, int id, XWPFParagraph paragraph) {
    if (builders.popUntilList(level, id)) {
      builders.push(GuideBulletList.builder()
                                   .level(level)
                                   .id(id));
    }
    builders.add(GuideBulletListItem.builder()
                                    .level(level)
                                    .add(GuideParagraph.builder()
                                                       .paragraph(Paragraph.builder()
                                                                           .add(TextRun.create(paragraph.getText()))
                                                                           .build())
                                                       .build())
                                    .build());
  }

  private void makeCallout(
      DocxReaderConfig config, CalloutType calloutType, String type, Iterator<IBodyElement> iterator) {
    GuideCallout.Builder callout = GuideCallout.builder()
                                               .calloutType(calloutType)
                                               .title(type);
    builders.push(callout);
    processElements(config, iterator);
    builders.pop();
  }

  private void makeNumberList(int level, int id, String numFmt) {
    NumberType numberType = numberFormatToNumberedType(numFmt);
    builders.push(GuideNumberList.builder()
                                 .level(level)
                                 .id(id)
                                 .numberType(numberType));
  }

  private void makeNumberListItem(int level, int id, String numFmt, XWPFParagraph paragraph) {
    if (builders.popUntilList(level, id)) {
      makeNumberList(level, id, numFmt);
    }
    builders.add(GuideNumberListItem.builder()
                                    .level(level)
                                    .add(GuideParagraph.builder()
                                                       .paragraph(Paragraph.builder()
                                                                           .add(TextRun.create(paragraph.getText()))
                                                                           .build())
                                                       .build())
                                    .build());
  }

  private boolean isOn(CTOnOff flag) {
    if (flag != null) {
      return flag.isSetVal();
    }
    return false;
  }

  private void makeParagraph(XWPFParagraph paragraph) {
    Paragraph.Builder paraBuilder = Paragraph.builder();
    List<XWPFRun> runs = paragraph.getRuns();
    for (XWPFRun run : runs) {
      CTRPr rPr = run.getCTR()
                     .getRPr();
      String text = run.text();
      if (text.contains("ENSURE")) {
        System.out.println();
      }
      if (rPr == null) {
        paraBuilder.add(TextRun.create(run.text()));
      } else {
        CTOnOff b = rPr.getB();
        if (b != null && b.isSetVal()) {
          System.out.println();
        }
        paraBuilder.add(TextRun.create(run.text(), isOn(rPr.getI()), isOn(b)));
      }
    }
    paraBuilder.add(TextRun.create(paragraph.getText()));
    GuideParagraph builder = GuideParagraph.builder()
                                           .paragraph(paraBuilder.build())
                                           .build();
    builders.add(builder);
  }

  private NumberType numberFormatToNumberedType(String numberFormat) {
    return switch (numberFormat) {
      case "lowerLetter" -> NumberType.LowerCase;
      case "upperLetter" -> NumberType.UpperCase;
      case "lowerRoman" -> NumberType.LowerRoman;
      case "upperRoman" -> NumberType.UpperRoman;
      default -> NumberType.Numbers;
    };
  }

  private void processCallout(DocxReaderConfig config, XWPFTable table) {
    XWPFTableRow row = table.getRow(0);
    List<XWPFTableCell> cells = row.getTableCells();
    if (cells.size() == 1) {
      XWPFTableCell cell = cells.get(0);
      List<IBodyElement> elements = cell.getBodyElements();
      Iterator<IBodyElement> iterator = elements.iterator();
      XWPFParagraph firstParagraph = null;
      while (iterator.hasNext()) {
        IBodyElement element = iterator.next();
        if (element instanceof XWPFParagraph) {
          firstParagraph = (XWPFParagraph) element;
          break;
        }
      }
      if (firstParagraph == null) {
        return;
      }
      String type = firstParagraph.getText();
      if (type.equalsIgnoreCase(config.noteCalloutText())) {
        makeCallout(config, CalloutType.Note, type, iterator);
      } else if (type.equalsIgnoreCase(config.warningCalloutText())) {
        makeCallout(config, CalloutType.Warning, type, iterator);
      } else if (type.equalsIgnoreCase(config.cautionCalloutText())) {
        makeCallout(config, CalloutType.Caution, type, iterator);
      } else {
        makeCallout(config, CalloutType.Other, type, iterator);
      }
    }
  }

  private void processDocument(DocxReaderConfig config) {
    List<IBodyElement> elements = doc.getBodyElements();
    Iterator<IBodyElement> iterator = elements.iterator();
    processElements(config, iterator);
    builders.close(); // Clear out all open sections
  }

  private void processElements(DocxReaderConfig config, Iterator<IBodyElement> iterator) {
    while (iterator.hasNext()) {
      IBodyElement element = iterator.next();
      if (element instanceof XWPFParagraph) {
        processParagraph(config, (XWPFParagraph) element);
      } else if (element instanceof XWPFTable) {
        processTable(config, (XWPFTable) element);
      }
    }
  }

  private void processParagraph(DocxReaderConfig config, XWPFParagraph paragraph) {
    String style = paragraph.getStyle();
    if (paragraph.getNumFmt() != null) {
      String numFmt = paragraph.getNumFmt();
      int level = paragraph.getNumIlvl()
                           .intValue();
      int id = paragraph.getNumID()
                        .intValue();
      if (numFmt.equalsIgnoreCase(NUMBER_FORMAT_BULLET)) {
        makeBulletListItem(level, id, paragraph);
      } else {
        makeNumberListItem(level, id, numFmt, paragraph);
      }
    } else {
      if (style != null) {
        if (style.equalsIgnoreCase(config.sectionLevel1Style())) {
          processSection(config, 1, "%1$d.0", paragraph);
        } else if (style.equalsIgnoreCase(config.sectionLevel2Style())) {
          processSection(config, 2, "%1$d.%2$d", paragraph);
        } else {
          makeParagraph(paragraph);
        }
      } else {
        makeParagraph(paragraph);
      }
    }
  }

  private void processSection(
      DocxReaderConfig config, int level, String nameFormat, XWPFParagraph paragraph) {
    builders.popUntilLevel(level - 1);
    Integer[] sectionCounts = builders.sectionCounts();
    for (int i = 0; i < sectionCounts.length; i++) {
      sectionCounts[i]++; // Convert 0-based to 1-based
    }
    GuideSection.Builder sectionBuilder = GuideSection.builder()
                                                      .level(level)
                                                      .name(String.format(nameFormat, (Object[]) sectionCounts))
                                                      .title(paragraph.getText());
    builders.push(sectionBuilder);
  }

  private void processTable(DocxReaderConfig config, XWPFTable table) {
    if (table.getNumberOfRows() == 1) {
      processCallout(config, table);
    } else {
      builders.push(GuideTable.builder());
      for (XWPFTableRow row : table.getRows()) {
        builders.push(GuideTableRow.builder());
        for (XWPFTableCell cell : row.getTableCells()) {
          builders.push(GuideTableCell.builder());
          processElements(config,
                          cell.getBodyElements()
                              .iterator());
          builders.pop();
        }
        builders.pop();
      }
      builders.pop();
    }
  }

  private void setDocumentFrontMatter(DocxReaderConfig config, Guide.Builder guideBuilder) {
    doc.getParagraphs()
       .stream()
       .filter(p -> config.frontMatter()
                          .contains(p.getStyle()))
       .forEach(p -> {
         String style = p.getStyle();
         String text = p.getText();
         if (config.titleStyle()
                   .equalsIgnoreCase(style)) {
           guideBuilder.title(text);
         } else if (config.subtitleStyle()
                          .equalsIgnoreCase(style)) {
           guideBuilder.subTitle(text);
         } else if (config.documentNumberStyle()
                          .equalsIgnoreCase(style)) {
           guideBuilder.documentNumber(text);
         } else if (config.documentRevisionStyle()
                          .equalsIgnoreCase(style)) {
           guideBuilder.revision(text);
         }
       });
  }
}
