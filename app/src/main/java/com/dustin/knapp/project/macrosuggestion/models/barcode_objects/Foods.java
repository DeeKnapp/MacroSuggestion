package com.dustin.knapp.project.macrosuggestion.models.barcode_objects;

/**
 * Created by dknapp on 4/24/17
 */
public class Foods
{
  public String serving_unit;

  public String nf_total_carbohydrate;

  public String nf_sodium;

  public String nf_p;

  public String alt_measures;

  public String nf_sugars;

  public String nf_calories;

  public String brand_name;

  public Object metadata;

  public String tags;

  public String food_name;

  public String ndb_no;

  public String nf_saturated_fat;

  public String nf_protein;

  public String nf_total_fat;

  public String nf_dietary_fiber;

  public String nf_cholesterol;

  public String nf_ingredient_statement;

  public Photo photo;

  public String nf_potassium;

  public String nix_brand_name;

  public Base[] full_nutrients;

  public String nix_item_id;

  public String updated_at;

  public String nix_item_name;

  public String source;

  public String serving_weight_grams;

  public String nix_brand_id;

  public String serving_qty;

  @Override
  public String toString()
  {
    return "ClassPojo [serving_unit = "+serving_unit+", nf_total_carbohydrate = "+nf_total_carbohydrate+", nf_sodium = "+nf_sodium+", nf_p = "+nf_p+", alt_measures = "+alt_measures+", nf_sugars = "+nf_sugars+", nf_calories = "+nf_calories+", brand_name = "+brand_name+", metadata = "+metadata+", tags = "+tags+", food_name = "+food_name+", ndb_no = "+ndb_no+", nf_saturated_fat = "+nf_saturated_fat+", nf_protein = "+nf_protein+", nf_total_fat = "+nf_total_fat+", nf_dietary_fiber = "+nf_dietary_fiber+", nf_cholesterol = "+nf_cholesterol+", nf_ingredient_statement = "+nf_ingredient_statement+", photo = "+photo+", nf_potassium = "+nf_potassium+", nix_brand_name = "+nix_brand_name+", full_nutrients = "+full_nutrients+", nix_item_id = "+nix_item_id+", updated_at = "+updated_at+", nix_item_name = "+nix_item_name+", source = "+source+", serving_weight_grams = "+serving_weight_grams+", nix_brand_id = "+nix_brand_id+", serving_qty = "+serving_qty+"]";
  }
}

