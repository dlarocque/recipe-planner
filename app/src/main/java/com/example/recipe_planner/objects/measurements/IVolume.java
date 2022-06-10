package com.example.recipe_planner.objects.measurements;

public interface IVolume extends IUnit {
  /*
  Using common Canadian standards for cooking and baking.
      Cup is a US customary cup, or ~237ml
      Teaspoon is a US teaspoon, or ~5ml
      Tablespoon is a US tablespoon, or ~15ml
      Millilitre is THE millilitre, indisputably correct.
  */
  public double convertToMl();

  public double convertToCup();

  public double convertToTeaspoon();

  public double convertToTablespoon();
}
