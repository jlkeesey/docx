package docx;

import com.google.common.collect.ImmutableList;
import model.*;
import model.GuideNumbered.NumberedType;
import model.GuideParagraphListBase.GuideParagraphListBuilder;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class DocxGuideReader {
  public static final String TITLE_STYLE = "Title";
  public static final String SUBTITLE_STYLE = "Subtitle";
  public static final String DOCUMENT_NUMBER_STYLE = "DocumentNumber";
  public static final String DOCUMENT_REVISION_STYLE = "DocumentRevision";
  public static final String HEADING_1_STYLE = "heading1";
  public static final String HEADING_2_STYLE = "heading2";
  public static final String NUMBER_FORMAT_BULLET = "bullet";

  private static final ImmutableList<String> frontMatter = ImmutableList.of(TITLE_STYLE,
                                                                            SUBTITLE_STYLE,
                                                                            DOCUMENT_NUMBER_STYLE,
                                                                            DOCUMENT_REVISION_STYLE);

  private Guide.Builder guideBuilder;
  private final XWPFDocument doc;
  private final Stack<GuideSection.Builder> sectionBuilders = new Stack<>();
  private GuideBullets.Builder bulletsBuilder;
  private GuideNumbered.Builder numberedBuilder;

  public DocxGuideReader(InputStream input) {
    try {
      doc = new XWPFDocument(input);
    } catch (IOException e) {
      throw new DocxReadException(e);
    }
  }

  public Guide read() {
    guideBuilder = Guide.builder();
    setDocumentFrontMatter(guideBuilder);
    processDocument(guideBuilder);
    return guideBuilder.build();
  }

  private void processDocument(Guide.Builder guideBuilder) {
    List<IBodyElement> elements = doc.getBodyElements();
    for (IBodyElement element : elements) {
      if (element instanceof XWPFParagraph) {
        processParagraph((XWPFParagraph) element);
      } else if (element instanceof XWPFTable) {
        processTable((XWPFTable) element);
      }
    }
    popSectionUntil(0); // Clear out all open sections
  }

  private void processParagraph(XWPFParagraph paragraph) {
    String style = paragraph.getStyle();
    if (paragraph.getNumFmt() != null) {
      String numFmt = paragraph.getNumFmt();
      if (numFmt.equalsIgnoreCase(NUMBER_FORMAT_BULLET)) {
        if (bulletsBuilder == null) {
          bulletsBuilder = GuideBullets.builder();
        }
        bulletsBuilder.add(GuideBulletItem.builder()
                                          .add(GuideParagraph.builder()
                                                             .add(GuideTextItem.create(paragraph.getText()))
                                                             .build())
                                          .build());
      } else {
        int numberLevel = paragraph.getNumIlvl()
                                   .intValue();
        if (numberLevel == 0) { // Currently we only handle one level
          if (numberedBuilder == null) {
            numberedBuilder = GuideNumbered.builder();
            NumberedType numberedType = numberFormatToNumberedType(numFmt);
            numberedBuilder.type(numberedType);
          }
          numberedBuilder.add(GuideNumberedItem.builder()
                                               .add(GuideParagraph.builder()
                                                                  .add(GuideTextItem.create(paragraph.getText()))
                                                                  .build())
                                               .build());
        }
      }
    } else {
      if (bulletsBuilder != null) {
        addToSection(bulletsBuilder.build());
        bulletsBuilder = null;
      }
      if (numberedBuilder != null) {
        addToSection(numberedBuilder.build());
        numberedBuilder = null;
      }
      if (style != null) {
        if (style.equalsIgnoreCase(HEADING_1_STYLE)) {
          handleSection(paragraph, 0, "%1$d.0");
        } else if (style.equalsIgnoreCase(HEADING_2_STYLE)) {
          handleSection(paragraph, 1, "%1$d.%2$d");
        } else {
          processDescription(paragraph);
        }
      } else {
        processDescription(paragraph);
      }
    }
  }

  private void processDescription(XWPFParagraph paragraph) {
    addToSection(GuideDescription.builder()
                                 .add(GuideParagraph.builder()
                                                    .add(GuideTextItem.create(paragraph.getText()))
                                                    .build())
                                 .build());
  }

  private NumberedType numberFormatToNumberedType(String numberFormat) {
    return switch (numberFormat) {
      case "lowerLetter" -> NumberedType.LowerCase;
      case "upperLetter" -> NumberedType.UpperCase;
      case "lowerRoman" -> NumberedType.LowerRoman;
      case "upperRoman" -> NumberedType.UpperRoman;
      default -> NumberedType.Numbers;
    };
  }

  private String getOutlineLevel(XWPFParagraph paragraph) {
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

  private void handleSection(XWPFParagraph paragraph, int level, String nameFormat) {
    popSectionUntil(level);
    GuideSection.Builder sectionBuilder = GuideSection.builder();
    Integer[] sectionCounts = currentSectionCounts();
    for (int i = 0; i < sectionCounts.length; i++) {
      sectionCounts[i]++; // Convert 0-based to 1-based
    }
    pushSection(sectionBuilder);
    sectionBuilder.name(String.format(nameFormat, (Object[]) sectionCounts));
    sectionBuilder.title(paragraph.getText());
  }

  private Integer[] currentSectionCounts() {
    Integer[] results = new Integer[sectionBuilders.size() + 1];
    results[0] = guideBuilder.sectionCount();
    for (int i = 0; i < sectionBuilders.size(); i++) {
      results[i + 1] = sectionBuilders.get(i)
                                      .sectionCount();
    }
    return results;
  }

  private void pushSection(GuideSection.Builder sectionBuilder) {
    sectionBuilders.push(sectionBuilder);
  }

  private void popSectionUntil(int level) {
    while (sectionBuilders.size() > level) {
      popSection();
    }
  }

  private void popSection() {
    if (!sectionBuilders.empty()) {
      GuideSection.Builder sectionBuilder = sectionBuilders.pop();
      if (!sectionBuilders.empty()) {
        sectionBuilders.peek()
                       .add(sectionBuilder.build());
      } else {
        guideBuilder.add(sectionBuilder.build());
      }
    }
  }

  private void addToSection(GuideBase item) {
    if (!sectionBuilders.empty()) {
      sectionBuilders.peek()
                     .add(item);
    }
  }

  private void processTable(XWPFTable table) {
    if (table.getNumberOfRows() == 1) {
      XWPFTableRow row = table.getRow(0);
      List<XWPFTableCell> cells = row.getTableCells();
      if (cells.size() == 1) {
        XWPFTableCell cell = cells.get(0);
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        Iterator<XWPFParagraph> iterator = paragraphs.iterator();
        String type = iterator.next()
                              .getText();
        if (type.equalsIgnoreCase("note")) {
          GuideNote.Builder note = GuideNote.builder();
          consumeParagraphs(note, iterator);
          addToSection(note.build());
        } else if (type.equalsIgnoreCase("warning")) {
          GuideWarning.Builder warn = GuideWarning.builder();
          consumeParagraphs(warn, iterator);
          addToSection(warn.build());
        } else if (type.equalsIgnoreCase("caution")) {
          GuideCaution.Builder caution = GuideCaution.builder();
          consumeParagraphs(caution, iterator);
          addToSection(caution.build());
        }
      }
    }
  }

  private <T> void consumeParagraphs(GuideParagraphListBuilder<T> builder, Iterator<XWPFParagraph> iterator) {
    while (iterator.hasNext()) {
      XWPFParagraph para = iterator.next();
      builder.add(GuideParagraph.builder()
                                .add(GuideTextItem.create(para.getText()))
                                .build());
    }
  }

  private void setDocumentFrontMatter(Guide.Builder guideBuilder) {
    doc.getParagraphs()
       .stream()
       .filter(p -> frontMatter.contains(p.getStyle()))
       .forEach(p -> {
         String style = p.getStyle();
         String text = p.getText();
         if (TITLE_STYLE.equalsIgnoreCase(style)) {
           guideBuilder.title(text);
         } else if (SUBTITLE_STYLE.equalsIgnoreCase(style)) {
           guideBuilder.subTitle(text);
         } else if (DOCUMENT_NUMBER_STYLE.equalsIgnoreCase(style)) {
           guideBuilder.documentNumber(text);
         } else if (DOCUMENT_REVISION_STYLE.equalsIgnoreCase(style)) {
           guideBuilder.revision(text);
         }
       });
  }
}
