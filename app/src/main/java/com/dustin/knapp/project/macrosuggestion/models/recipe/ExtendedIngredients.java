package com.dustin.knapp.project.macrosuggestion.models.recipe;

import com.google.gson.annotations.Expose;

/**
 * Created by dknapp on 12/12/17.
 */

public class ExtendedIngredients {
  @Expose private String amount;
  @Expose private String id;
  @Expose private String unit;
  @Expose private String[] metaInformation;
  @Expose private String unitShort;
  @Expose private String aisle;
  @Expose private String name;
  @Expose private String consistency;
  @Expose private String image;
  @Expose private String originalString;
  @Expose private String unitLong;

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String[] getMetaInformation() {
    return metaInformation;
  }

  public void setMetaInformation(String[] metaInformation) {
    this.metaInformation = metaInformation;
  }

  public String getUnitShort() {
    return unitShort;
  }

  public void setUnitShort(String unitShort) {
    this.unitShort = unitShort;
  }

  public String getAisle() {
    return aisle;
  }

  public void setAisle(String aisle) {
    this.aisle = aisle;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getConsistency() {
    return consistency;
  }

  public void setConsistency(String consistency) {
    this.consistency = consistency;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getOriginalString() {
    return originalString;
  }

  public void setOriginalString(String originalString) {
    this.originalString = originalString;
  }

  public String getUnitLong() {
    return unitLong;
  }

  public void setUnitLong(String unitLong) {
    this.unitLong = unitLong;
  }

  @Override public String toString() {
    return "ClassPojo [amount = "
        + amount
        + ", id = "
        + id
        + ", unit = "
        + unit
        + ", metaInformation = "
        + metaInformation
        + ", unitShort = "
        + unitShort
        + ", aisle = "
        + aisle
        + ", name = "
        + name
        + ", consistency = "
        + consistency
        + ", image = "
        + image
        + ", originalString = "
        + originalString
        + ", unitLong = "
        + unitLong
        + "]";
  }
}
