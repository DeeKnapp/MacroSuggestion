package com.dustin.knapp.project.macrosuggestion.models.barcode_objects;

/**
 * Created by dknapp on 4/24/17
 */
public class Base {

  private String attr_id;
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getAttr_id() {
    return attr_id;
  }

  public void setAttr_id(String attr_id) {
    this.attr_id = attr_id;
  }
}
