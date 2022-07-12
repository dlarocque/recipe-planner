package com.example.recipe_planner.persistence;

import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.objects.Schedule;
import com.example.recipe_planner.objects.measurements.Count;
import com.example.recipe_planner.objects.measurements.Cup;
import com.example.recipe_planner.objects.measurements.Gram;
import com.example.recipe_planner.objects.measurements.Ounce;
import com.example.recipe_planner.objects.measurements.Tablespoon;
import com.example.recipe_planner.objects.measurements.Teaspoon;
import com.example.recipe_planner.presentation.MealSchedule;
import com.example.recipe_planner.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataAccessStub implements DataAccess {
    private static final double QUARTER = 1.0 / 4.0;
    private static final double THIRD = 1.0 / 3.0;
    private static final double HALF = 1.0 / 2.0;
    private ArrayList<Recipe> recipes;
    private ArrayList<Recipe> hiddenRecipes;
    private Schedule schedule;

    public DataAccessStub() {
    }

    public void open(String dbPath) {
        recipes = new ArrayList<>();
        fillRecipes(recipes);
        hiddenRecipes = new ArrayList<>();
        schedule = new Schedule();
    }

    public void close() {}

    public Recipe getRecipe(int recipeId) {
        Recipe recipe = null;
        for (Recipe otherRecipe : recipes) {
            if (otherRecipe.getId() == recipeId) {
                recipe = otherRecipe;
                break;
            }
        }

        return recipe;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public ArrayList<Ingredient> getRecipeIngredients(int recipeId) {
        ArrayList<Ingredient> recipeIngredients = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getId() == recipeId) {
                recipeIngredients.addAll(recipe.getIngredients());
            }
        }
        return recipeIngredients;
    }

    public List<Recipe> getRecipesWithPartialName(String recipePartialName) {
        ArrayList<Recipe> recipesWithPartialName = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getName()
                    .toLowerCase(Locale.ROOT)
                    .contains(recipePartialName.toLowerCase(Locale.ROOT)))
                recipesWithPartialName.add(recipe);
        }

        return recipesWithPartialName;
    }

    // Returns true if the recipe exists in recipes.
    public boolean deleteRecipe(int recipeId) {
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getId() == recipeId) {
                if (recipes.get(i).isDefault()) {
                    hiddenRecipes.add(recipes.get(i));
                }
                recipes.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public DaySchedule getDaySchedule(Date date) {
        return schedule.getDayScheduleOrDefault(date);
    }

    @Override
    public void initializeDaySchedule(Date date) {
        schedule.setDaySchedule(date, new DaySchedule());
    }

    @Override
    public void setDayScheduleMeal(Date date, DaySchedule.Meal meal, Recipe recipe) {
        DaySchedule daySchedule = schedule.getDayScheduleOrDefault(date);
        daySchedule.setMeal(meal, recipe);
    }

    @Override
    public void setDayScheduleMealNull(Date date, DaySchedule.Meal meal) {
        DaySchedule daySchedule = schedule.getDayScheduleOrDefault(date);
        daySchedule.setMeal(meal, null);
    }

    private void fillRecipes(ArrayList<Recipe> recipes) {
        ArrayList<Ingredient> ingredients;
        ingredients =
                new ArrayList<>(
                        Arrays.asList(
                                new Ingredient("Balsamic Vinegar", new Cup(3 * QUARTER)),
                                new Ingredient("Basil Leaves", new Cup(QUARTER)),
                                new Ingredient("Olive Oil", new Tablespoon(2)),
                                new Ingredient("Plum Tomatoes", new Count(4)),
                                new Ingredient("Boneless Skinless Chicken Breast", new Count(4))));
        String instructions =
                "After washing basil and tomatoes, blot them dry with clean paper towel.\n"
                        + "\n"
                        + "Using a clean cutting board, cut tomatoes into quarters.\n"
                        + "\n"
                        + "For marinade, place first six ingredients in a blender. Cover and process until well blended.\n"
                        + "\n"
                        + "Place chicken breasts in a shallow dish; orange do not rinse raw poultry. Cover with marinade. Cover dish. Refrigerate about 1 hour, turning occasionally. Wash dish after touching raw poultry.\n"
                        + "\n"
                        + "orange quote icon Wash hands with soap and water after handling uncooked chicken.\n"
                        + "\n"
                        + "Place chicken on an oiled grill rack over medium heat. Do not reuse marinades used on raw foods. Grill chicken 4-6 minutes per side. Cook until internal temperature reaches 165 °F as measured with a food thermometer. ";
        recipes.add(new Recipe(0, "Grilled Basil Chicken", ingredients, instructions, true));

        ingredients =
                new ArrayList<>(
                        Arrays.asList(
                                new Ingredient("Water", new Cup(3 * QUARTER)),
                                new Ingredient("Honey", new Teaspoon(2)),
                                new Ingredient("Olive Oil", new Teaspoon(2)),
                                new Ingredient("Salt", new Teaspoon(2 * THIRD)),
                                new Ingredient("White Sugar", new Teaspoon(2 * THIRD)),
                                new Ingredient("Bread Flour", new Cup(2)),
                                new Ingredient("Active Yeast", new Teaspoon(3 * HALF)),
                                new Ingredient("Honey", new Cup(1))
                                ));

        instructions =
                "Add to your bread machine per manufacturer instructions.\n"
                        + "While bread is baking drizzle with honey if desired.";

        recipes.add(new Recipe(1, "Sweet Honey French Bread", ingredients, instructions, true));

        ingredients =
                new ArrayList<>(
                        Arrays.asList(
                                new Ingredient("Water", new Cup(3 * QUARTER)),
                                new Ingredient("Unpeeled Potato", new Gram(907)),
                                new Ingredient("Gorgonzola", new Ounce(2)),
                                new Ingredient("Pecan", new Cup(HALF)),
                                new Ingredient("Extra Virgin Olive Oil", new Cup(QUARTER)),
                                new Ingredient("Baby Arugula", new Cup(2)),
                                new Ingredient("Salt", new Teaspoon(HALF))));

        instructions =
                "Boil potatoes until they are just tender 2040 minutes depending on variety drain and then return them to pot.\n"
                        + "\n"
                        + "Using a large wooden spoon coarsely crush potatoes in pot.\n"
                        + "\n"
                        + "Stir in cheese nuts oil and arugula and toss to blend.\n"
                        + "\n"
                        + "Salt and pepper to taste.";

        recipes.add(new Recipe(2, "Crushed Heirloom Potatoes", ingredients, instructions, true));

        ingredients =
                new ArrayList<>(
                        Arrays.asList(
                                new Ingredient("Pastry Double Crust Pie", new Count(1)),
                                new Ingredient("Apple", new Count(6)),
                                new Ingredient("White Sugar", new Cup(THIRD)),
                                new Ingredient("Brown Sugar", new Cup(THIRD)),
                                new Ingredient("Flour", new Teaspoon(2)),
                                new Ingredient("Cinnamon", new Teaspoon(1)),
                                new Ingredient("Butter", new Tablespoon(1))));

        instructions =
                "1. Mix apples white and brown sugar flour and cinnamon all together in a large bowl and pour into pie crust in pan.\n"
                        + "\n"
                        + "2. Dot with butter and cover with top crust sealing and fluting edges.\n"
                        + "\n"
                        + "3. Slit a few holes in top crust.\n"
                        + "\n"
                        + "4. Brush cream lightly on top crust all over and sprinkle with sugar.\n"
                        + "\n"
                        + "5. Bake at 450 for 15 minutes.\n"
                        + "\n"
                        + "6. Reduce heat to 350 and bake another 5055 minutes until bubbly and apples are soft.\n"
                        + "\n"
                        + "7. Savor every bite.";

        recipes.add(new Recipe(3, "Heirloom Apple Pie", ingredients, instructions, true));
    }
}
