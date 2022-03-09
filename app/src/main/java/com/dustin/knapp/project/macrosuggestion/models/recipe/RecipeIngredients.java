package com.dustin.knapp.project.macrosuggestion.models.recipe;

import com.google.gson.annotations.Expose;

/**
 * Created by dknapp on 12/12/17.
 */

public class RecipeIngredients {
  private transient String instructions;

  @Expose private String preparationMinutes;

  private transient String ketogenic;

  private transient String[] diets;

  private transient String sustainable;

  @Expose private String sourceUrl;

  private transient String[] dishTypes;

  private transient String veryHealthy;

  private transient String id;

  @Expose private String servings;

  private transient String title;

  private transient String lowFodmap;

  private transient String sourceName;

  private transient String vegetarian;

  private transient String healthScore;

  private transient String[] occasions;

  private transient String spoonacularScore;

  private transient String[] cuisines;

  private transient String dairyFree;

  private transient String creditText;

  private transient String whole30;

  private transient String aggregateLikes;

  @Expose private AnalyzedInstructions[] analyzedInstructions;

  private transient String image;

  @Expose private String readyInMinutes;

  private transient String spoonacularSourceUrl;

  private transient String pricePerServing;

  @Expose private String cookingMinutes;

  private transient String glutenFree;

  private transient String gaps;

  @Expose private transient String weightWatcherSmartPoints;

  private transient String vegan;

  private transient String winePairing;

  private transient String imageType;

  private transient String cheap;

  private transient String veryPopular;

  @Expose private ExtendedIngredients[] extendedIngredients;

  public String getInstructions() {
    return instructions;
  }

  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }

  public String getPreparationMinutes() {
    return preparationMinutes;
  }

  public void setPreparationMinutes(String preparationMinutes) {
    this.preparationMinutes = preparationMinutes;
  }

  public String getKetogenic() {
    return ketogenic;
  }

  public void setKetogenic(String ketogenic) {
    this.ketogenic = ketogenic;
  }

  public String[] getDiets() {
    return diets;
  }

  public void setDiets(String[] diets) {
    this.diets = diets;
  }

  public String getSustainable() {
    return sustainable;
  }

  public void setSustainable(String sustainable) {
    this.sustainable = sustainable;
  }

  public String getSourceUrl() {
    return sourceUrl;
  }

  public void setSourceUrl(String sourceUrl) {
    this.sourceUrl = sourceUrl;
  }

  public String[] getDishTypes() {
    return dishTypes;
  }

  public void setDishTypes(String[] dishTypes) {
    this.dishTypes = dishTypes;
  }

  public String getVeryHealthy() {
    return veryHealthy;
  }

  public void setVeryHealthy(String veryHealthy) {
    this.veryHealthy = veryHealthy;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getServings() {
    return servings;
  }

  public void setServings(String servings) {
    this.servings = servings;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLowFodmap() {
    return lowFodmap;
  }

  public void setLowFodmap(String lowFodmap) {
    this.lowFodmap = lowFodmap;
  }

  public String getSourceName() {
    return sourceName;
  }

  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }

  public String getVegetarian() {
    return vegetarian;
  }

  public void setVegetarian(String vegetarian) {
    this.vegetarian = vegetarian;
  }

  public String getHealthScore() {
    return healthScore;
  }

  public void setHealthScore(String healthScore) {
    this.healthScore = healthScore;
  }

  public String[] getOccasions() {
    return occasions;
  }

  public void setOccasions(String[] occasions) {
    this.occasions = occasions;
  }

  public String getSpoonacularScore() {
    return spoonacularScore;
  }

  public void setSpoonacularScore(String spoonacularScore) {
    this.spoonacularScore = spoonacularScore;
  }

  public String[] getCuisines() {
    return cuisines;
  }

  public void setCuisines(String[] cuisines) {
    this.cuisines = cuisines;
  }

  public String getDairyFree() {
    return dairyFree;
  }

  public void setDairyFree(String dairyFree) {
    this.dairyFree = dairyFree;
  }

  public String getCreditText() {
    return creditText;
  }

  public void setCreditText(String creditText) {
    this.creditText = creditText;
  }

  public String getWhole30() {
    return whole30;
  }

  public void setWhole30(String whole30) {
    this.whole30 = whole30;
  }

  public String getAggregateLikes() {
    return aggregateLikes;
  }

  public void setAggregateLikes(String aggregateLikes) {
    this.aggregateLikes = aggregateLikes;
  }

  public AnalyzedInstructions[] getAnalyzedInstructions() {
    return analyzedInstructions;
  }

  public void setAnalyzedInstructions(AnalyzedInstructions[] analyzedInstructions) {
    this.analyzedInstructions = analyzedInstructions;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getReadyInMinutes() {
    return readyInMinutes;
  }

  public void setReadyInMinutes(String readyInMinutes) {
    this.readyInMinutes = readyInMinutes;
  }

  public String getSpoonacularSourceUrl() {
    return spoonacularSourceUrl;
  }

  public void setSpoonacularSourceUrl(String spoonacularSourceUrl) {
    this.spoonacularSourceUrl = spoonacularSourceUrl;
  }

  public String getPricePerServing() {
    return pricePerServing;
  }

  public void setPricePerServing(String pricePerServing) {
    this.pricePerServing = pricePerServing;
  }

  public String getCookingMinutes() {
    return cookingMinutes;
  }

  public void setCookingMinutes(String cookingMinutes) {
    this.cookingMinutes = cookingMinutes;
  }

  public String getGlutenFree() {
    return glutenFree;
  }

  public void setGlutenFree(String glutenFree) {
    this.glutenFree = glutenFree;
  }

  public String getGaps() {
    return gaps;
  }

  public void setGaps(String gaps) {
    this.gaps = gaps;
  }

  public String getWeightWatcherSmartPoints() {
    return weightWatcherSmartPoints;
  }

  public void setWeightWatcherSmartPoints(String weightWatcherSmartPoints) {
    this.weightWatcherSmartPoints = weightWatcherSmartPoints;
  }

  public String getVegan() {
    return vegan;
  }

  public void setVegan(String vegan) {
    this.vegan = vegan;
  }

  public String getWinePairing() {
    return winePairing;
  }

  public void setWinePairing(String winePairing) {
    this.winePairing = winePairing;
  }

  public String getImageType() {
    return imageType;
  }

  public void setImageType(String imageType) {
    this.imageType = imageType;
  }

  public String getCheap() {
    return cheap;
  }

  public void setCheap(String cheap) {
    this.cheap = cheap;
  }

  public String getVeryPopular() {
    return veryPopular;
  }

  public void setVeryPopular(String veryPopular) {
    this.veryPopular = veryPopular;
  }

  public ExtendedIngredients[] getExtendedIngredients() {
    return extendedIngredients;
  }

  public void setExtendedIngredients(ExtendedIngredients[] extendedIngredients) {
    this.extendedIngredients = extendedIngredients;
  }

  @Override public String toString() {
    return "ClassPojo [instructions = "
        + instructions
        + ", preparationMinutes = "
        + preparationMinutes
        + ", ketogenic = "
        + ketogenic
        + ", diets = "
        + diets
        + ", sustainable = "
        + sustainable
        + ", sourceUrl = "
        + sourceUrl
        + ", dishTypes = "
        + dishTypes
        + ", veryHealthy = "
        + veryHealthy
        + ", id = "
        + id
        + ", servings = "
        + servings
        + ", title = "
        + title
        + ", lowFodmap = "
        + lowFodmap
        + ", sourceName = "
        + sourceName
        + ", vegetarian = "
        + vegetarian
        + ", healthScore = "
        + healthScore
        + ", occasions = "
        + occasions
        + ", spoonacularScore = "
        + spoonacularScore
        + ", cuisines = "
        + cuisines
        + ", dairyFree = "
        + dairyFree
        + ", creditText = "
        + creditText
        + ", whole30 = "
        + whole30
        + ", aggregateLikes = "
        + aggregateLikes
        + ", analyzedInstructions = "
        + analyzedInstructions
        + ", image = "
        + image
        + ", readyInMinutes = "
        + readyInMinutes
        + ", spoonacularSourceUrl = "
        + spoonacularSourceUrl
        + ", pricePerServing = "
        + pricePerServing
        + ", cookingMinutes = "
        + cookingMinutes
        + ", glutenFree = "
        + glutenFree
        + ", gaps = "
        + gaps
        + ", weightWatcherSmartPoints = "
        + weightWatcherSmartPoints
        + ", vegan = "
        + vegan
        + ", winePairing = "
        + winePairing
        + ", imageType = "
        + imageType
        + ", cheap = "
        + cheap
        + ", veryPopular = "
        + veryPopular
        + ", extendedIngredients = "
        + extendedIngredients
        + "]";
  }
}