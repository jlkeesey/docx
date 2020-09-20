import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class DocxConverter {
  public static void main(String[] args) {
    File input = new File("./test1.docx");
    try {
      FileInputStream stream = new FileInputStream(input);
      XWPFDocument doc = new XWPFDocument(stream);
      List<XWPFParagraph> paras = doc.getParagraphs();
      for (XWPFParagraph p : paras) {
        String lvl = "-";
        CTP ctp = p.getCTP();
        if (ctp != null) {
          CTPPr ppr = ctp.getPPr();
          if (ppr != null) {
            CTDecimalNumber outlineLvl = ppr.getOutlineLvl();
            if (outlineLvl != null) {
              BigInteger val = outlineLvl.getVal();
              if (val != null) {
                lvl = val.toString();
              }
            }
          }
        }
        System.out.printf("p[%s] (%s) lvl:%s: '%s'\n", p.getStyle(), p.getNumFmt(), lvl, p.getText());
        System.out.printf("   nf:'%s' ni:%s nl:%s nlT:'%s' \n", p.getNumFmt(), p.getNumID(), p.getNumIlvl(), p.getNumLevelText());
      }
    } catch (FileNotFoundException e) {
      System.err.printf("Unable to open file '%s\n  because %s\n", input.getAbsoluteFile(), e.getMessage());
    } catch (IOException e) {
      System.err.printf("Failed to read DOCX file %s\n  because %s\n", input.getAbsoluteFile(), e.getMessage());
    }
    System.out.print("Done.\n");
  }
}
