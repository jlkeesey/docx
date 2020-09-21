package model;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a complete guide. A guide contains a sequence of guide steps which can contain other descriptive
 * information about the step including sub-steps.
 */
public class Guide implements Iterable<GuideSection> {
  private final String title;

  public String subTitle() {
    return subTitle;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public String documentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public String revision() {
    return revision;
  }

  public void setRevision(String revision) {
    this.revision = revision;
  }

  private String subTitle;
  private String documentNumber;
  private String revision;
  private final ImmutableList<GuideSection> items;

  private Guide(
      String title,
      String subTitle,
      String documentNumber,
      String revision,
      @NotNull Iterable<GuideSection> iterable) {
    this.title = title;
    this.subTitle = subTitle;
    this.documentNumber = documentNumber;
    this.revision = revision;
    this.items = ImmutableList.copyOf(iterable);
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  /**
   * Returns the number of items in this guide.
   */
  public int size() {
    return items.size();
  }

  public void visit(@NotNull GuideVisitor visitor) {
    visitor.start(this);
    for (int i = 0; i < items.size(); i++) {
      items.get(i)
           .visit(visitor, i);
    }
    visitor.end(this);
  }

  @Override
  public @NotNull Iterator<GuideSection> iterator() {
    return items.iterator();
  }

  public String title() {
    return title;
  }

  public static class Builder {
    private String title = "";
    private String subTitle = "";
    private String documentNumber = "";
    private String revision = "";
    private final List<GuideSection> items = new ArrayList<>();

    public @NotNull Guide build() {
      return new Guide(title, subTitle, documentNumber, revision, items);
    }

    public int sectionCount() {
      return items.size();
    }

    public String title() {
      return this.title;
    }

    public void title(String title) {
      this.title = title;
    }

    /**
     * Adds a new item to the guide.
     */
    public @NotNull Builder add(GuideSection step) {
      items.add(step);
      return this;
    }

    /**
     * Adds a new item to the guide.
     */
    public @NotNull Builder addAll(@NotNull Iterable<GuideSection> iterable) {
      for (GuideSection step : iterable) {
        items.add(step);
      }
      return this;
    }

    public @NotNull Builder clear() {
      items.clear();
      return this;
    }

    public String subTitle() {
      return subTitle;
    }

    public @NotNull Builder subTitle(String subTitle) {
      this.subTitle = subTitle;
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
  }
}
