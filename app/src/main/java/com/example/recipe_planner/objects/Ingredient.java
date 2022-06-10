package com.example.recipe_planner.objects;

import com.example.recipe_planner.objects.measurements.IUnit;

public class Ingredient {
  private String name;
  private IUnit amount;

  public Ingredient(String name, IUnit amount) {
    this.name = name;
    this.amount = amount;
  }

  // getters
  public String getName() {
    return this.name;
  }

  public double getAmount() {
    return this.amount.getAmount();
  }

  public IUnit getUnit() {
    return this.amount;
  }

  // setters
  public void setAmount(IUnit newAmount) {
    this.amount = newAmount;
  }
}
