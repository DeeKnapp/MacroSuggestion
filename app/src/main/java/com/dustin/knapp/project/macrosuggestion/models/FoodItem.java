package com.dustin.knapp.project.macrosuggestion.models;

/**
 * Created by dknapp on 4/11/17
 */
public class FoodItem
{
  private String id;

  private String title;

  private String imageType;

  private String protein;

  private String fat;

  private String calories;

  private String image;

  private String carbs;

  public String getId ()
  {
    return id;
  }

  public void setId (String id)
  {
    this.id = id;
  }

  public String getTitle ()
  {
    return title;
  }

  public void setTitle (String title)
  {
    this.title = title;
  }

  public String getImageType ()
  {
    return imageType;
  }

  public void setImageType (String imageType)
  {
    this.imageType = imageType;
  }

  public String getProtein ()
  {
    return protein;
  }

  public void setProtein (String protein)
  {
    this.protein = protein;
  }

  public String getFat ()
  {
    return fat;
  }

  public void setFat (String fat)
  {
    this.fat = fat;
  }

  public String getCalories ()
  {
    return calories;
  }

  public void setCalories (String calories)
  {
    this.calories = calories;
  }

  public String getImage ()
  {
    return image;
  }

  public void setImage (String image)
  {
    this.image = image;
  }

  public String getCarbs ()
  {
    return carbs;
  }

  public void setCarbs (String carbs)
  {
    this.carbs = carbs;
  }
}
