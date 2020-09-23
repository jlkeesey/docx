package model;

class Acceptance {
  static boolean listItemAcceptance(GuideBase item, int level) {
    if (item instanceof GuideBulletList) {
      return (((GuideBulletList) item).level() == level + 1);
    } else if (item instanceof GuideNumberList) {
      return (((GuideNumberList) item).level() == level + 1);
    }
    return !(item instanceof GuideBulletListItem) &&
           !(item instanceof GuideNumberListItem) &&
           !(item instanceof GuideSection);
  }

  static boolean listAcceptance(GuideBase item, int level) {
    if (item instanceof GuideBulletListItem) {
      return (((GuideBulletListItem) item).level() == level);
    } else if (item instanceof GuideNumberListItem) {
      return (((GuideNumberListItem) item).level() == level);
    }
    return !(item instanceof GuideSection);
  }
}
