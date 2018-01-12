package com.dustin.knapp.project.macrosuggestion.models.recipe;

import com.google.gson.annotations.Expose;

/**
 * Created by dknapp on 12/12/17.
 */

public class Equipment {
  private String id;

  @Expose private String name;

  @Expose private String image;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @Override public String toString() {
    return "ClassPojo [id = " + id + ", name = " + name + ", image = " + image + "]";
  }
}