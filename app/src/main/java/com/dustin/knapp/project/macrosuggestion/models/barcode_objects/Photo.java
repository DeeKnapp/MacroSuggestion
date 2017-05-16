package com.dustin.knapp.project.macrosuggestion.models.barcode_objects;

/**
 * Created by dknapp on 4/24/17
 */
public class Photo {
  private String highres;

  private String thumb;

  public String getHighres() {
    return highres;
  }

  public void setHighres(String highres) {
    this.highres = highres;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  @Override public String toString() {
    return "ClassPojo [highres = " + highres + ", thumb = " + thumb + "]";
  }
}
