import docx.DocxGuideReader;
import html.HtmlFormatVisitor;
import model.Guide;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.*;

public class DocxConverter {
  private static final boolean useReader = true;

  public static void main(String[] args) {
    File input = new File("./test2.docx");
    try {
      FileInputStream stream = new FileInputStream(input);
      DocxGuideReader reader = new DocxGuideReader(stream);
      Guide guide = reader.read();
      StringBuilder builder = new StringBuilder();
      HtmlFormatVisitor visitor = new HtmlFormatVisitor(builder);
      guide.visit(visitor);
      String guideHTML = builder.toString();
      System.out.println(guideHTML);

      BufferedWriter writer = new BufferedWriter(new FileWriter("./output.html"));
      writer.write(guideHTML);
      writer.close();

    } catch (FileNotFoundException e) {
      System.err.printf("Unable to open file '%s\n  because %s\n", input.getAbsoluteFile(), e.getMessage());
    } catch (IOException e) {
      System.err.printf("Unable to write file '%s\n  because %s\n", input.getAbsoluteFile(), e.getMessage());
    }
  }

  private static void processTable(XWPFTable table) {
    System.out.printf("TBL: rows=%d\n", table.getNumberOfRows());
  }

  private static void processParagraph(XWPFParagraph paragraph) {
//    String lvl = "-";
//    CTP ctp = paragraph.getCTP();
//    if (ctp != null) {
//      CTPPr ppr = ctp.getPPr();
//      if (ppr != null) {
//        CTDecimalNumber outlineLvl = ppr.getOutlineLvl();
//        if (outlineLvl != null) {
//          BigInteger val = outlineLvl.getVal();
//          if (val != null) {
//            lvl = val.toString();
//          }
//        }
//      }
//    }
    System.out.printf("p[%s] (%s) lvl:%s: '%s'\n",
                      paragraph.getStyle(),
                      paragraph.getNumFmt(),
                      "-",
                      paragraph.getText());
    System.out.printf("   nf:'%s' ni:%s nl:%s nlT:'%s' \n",
                      paragraph.getNumFmt(),
                      paragraph.getNumID(),
                      paragraph.getNumIlvl(),
                      paragraph.getNumLevelText());
  }
}
