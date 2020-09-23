package model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a complete guide. A guide contains a sequence of guide steps which can contain other descriptive
 * information about the step including sub-steps.
 */
public class Guide extends GuideBase {
  private final String documentNumber;
  private final String revision;
  private final String subTitle;
  private final String title;

  private Guide(
      String title, String subTitle, String documentNumber, String revision, @NotNull Iterable<GuideBase> iterable) {
    super(iterable);
    this.title = title;
    this.subTitle = subTitle;
    this.documentNumber = documentNumber;
    this.revision = revision;
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  public String documentNumber() {
    return documentNumber;
  }

  public String revision() {
    return revision;
  }

  public String subTitle() {
    return subTitle;
  }

  public String title() {
    return title;
  }

  public void visit(@NotNull GuideVisitor visitor) {
    visit(visitor, 0);
  }

  public void visit(@NotNull GuideVisitor visitor, int index) {
    visitor.start(this);
    visitItems(visitor);
    visitor.end(this);
  }

  public static class Builder extends GuideBase.BuilderBase<Guide, Builder> {
    private String documentNumber = "";
    private String revision = "";
    private String subTitle = "";
    private String title = "";

    public @NotNull Guide build() {
      return new Guide(title, subTitle, documentNumber, revision, items);
    }

    @Override
    public boolean accept(GuideBase item) {
      return item instanceof GuideSection;
    }

    @Override
    public int sectionCount() {
      return items.size();
    }

    @Override
    protected @NotNull Builder getThis() {
      return this;
    }

    public String documentNumber() {
      return documentNumber;
    }

    public @NotNull Builder documentNumber(String documentNumber) {
      this.documentNumber = documentNumber;
      return this;
    }

    public String revision() {
      return revision;
    }

    public @NotNull Builder revision(String revision) {
      this.revision = revision;
      return this;
    }

    public String subTitle() {
      return subTitle;
    }

    public @NotNull Builder subTitle(String subTitle) {
      this.subTitle = subTitle;
      return this;
    }

    public String title() {
      return this.title;
    }

    public void title(String title) {
      this.title = title;
    }
  }
}
