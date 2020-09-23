package docx;

import com.google.common.collect.ImmutableList;

public class DocxReaderConfig {
  public static final String CAUTION_CALLOUT_TEXT = "Caution";
  public static final String DOCUMENT_NUMBER_STYLE = "DocumentNumber";
  public static final String DOCUMENT_REVISION_STYLE = "DocumentRevision";
  public static final String HEADING_1_STYLE = "heading1";
  public static final String HEADING_2_STYLE = "heading2";
  public static final String NOTE_CALLOUT_TEXT = "Note";
  public static final String SUBTITLE_STYLE = "Subtitle";
  public static final String TITLE_STYLE = "Title";
  public static final String WARNING_CALLOUT_TEXT = "Warning";

  private String cautionCalloutText = CAUTION_CALLOUT_TEXT;
  private String documentNumberStyle = DOCUMENT_NUMBER_STYLE;
  private String documentRevisionStyle = DOCUMENT_REVISION_STYLE;
  private ImmutableList<String> frontMatter;
  private String noteCalloutText = NOTE_CALLOUT_TEXT;
  private String sectionLevel1Style = HEADING_1_STYLE;
  private String sectionLevel2Style = HEADING_2_STYLE;
  private String subtitleStyle = SUBTITLE_STYLE;
  private String titleStyle = TITLE_STYLE;
  private String warningCalloutText = WARNING_CALLOUT_TEXT;

  public DocxReaderConfig() {
    setupFrontMatter();
  }

  public String cautionCalloutText() {
    return cautionCalloutText;
  }

  public void cautionCalloutText(String cautionCalloutText) {
    this.cautionCalloutText = cautionCalloutText;
  }

  public String documentNumberStyle() {
    return documentNumberStyle;
  }

  public void documentNumberStyle(String documentNumberStyle) {
    this.documentNumberStyle = documentNumberStyle;
    setupFrontMatter();
  }

  public String documentRevisionStyle() {
    return documentRevisionStyle;
  }

  public void documentRevisionStyle(String documentRevisionStyle) {
    this.documentRevisionStyle = documentRevisionStyle;
    setupFrontMatter();
  }

  public ImmutableList<String> frontMatter() {
    return frontMatter;
  }

  public String noteCalloutText() {
    return noteCalloutText;
  }

  public void noteCalloutText(String noteCalloutText) {
    this.noteCalloutText = noteCalloutText;
  }

  public String sectionLevel1Style() {
    return sectionLevel1Style;
  }

  public void sectionLevel1Style(String sectionLevel1Style) {
    this.sectionLevel1Style = sectionLevel1Style;
  }

  public String sectionLevel2Style() {
    return sectionLevel2Style;
  }

  public void sectionLevel2Style(String sectionLevel2Style) {
    this.sectionLevel2Style = sectionLevel2Style;
  }

  public void subtitleStyle(String subtitleStyle) {
    this.subtitleStyle = subtitleStyle;
    setupFrontMatter();
  }

  public String subtitleStyle() {
    return subtitleStyle;
  }

  public void titleStyle(String titleStyle) {
    this.titleStyle = titleStyle;
    setupFrontMatter();
  }

  public String titleStyle() {
    return titleStyle;
  }

  public String warningCalloutText() {
    return warningCalloutText;
  }

  public void warningCalloutText(String warningCalloutText) {
    this.warningCalloutText = warningCalloutText;
  }

  private void setupFrontMatter() {
    frontMatter = ImmutableList.of(titleStyle,
                                   subtitleStyle,
                                   documentNumberStyle,
                                   documentRevisionStyle);
  }
}
