import docx.DocxGuideReader;
import html.HtmlFormatVisitor;
import model.Guide;

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
}
