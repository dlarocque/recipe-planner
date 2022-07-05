package com.example.recipe_planner.persistence;

import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.objects.Schedule;
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
import java.util.Random;

public class DataAccessStub {
    private static final double QUARTER = 1.0 / 4.0;
    private static final double THIRD = 1.0 / 3.0;
    private static final double HALF = 1.0 / 2.0;
    private final String dbName;
    private final String dbType = "stub";
    private final Random random;
    private ArrayList<Recipe> recipes;
    private ArrayList<Recipe> hiddenRecipes;
    private Schedule schedule;

    public DataAccessStub(String dbName) {
        this.dbName = dbName;
        this.random = new Random();
        init();
    }

    public DataAccessStub() {
        this.dbName = "Recipes";
        this.random = new Random();
        init();
    }

    public void init() {
        recipes = new ArrayList<>();
        fillRecipes(recipes);
        hiddenRecipes = new ArrayList<>();

        schedule = new Schedule();
        fillSchedule(schedule);
    }

    public Recipe getRecipe(int index) {
        return recipes.get(index);
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public Recipe getRandomRecipe() {
        return recipes.get(random.nextInt(recipes.size()));
    }

    public List<Recipe> getRecipesWithIngredientName(String ingredientName) {
        ArrayList<Recipe> recipesWithIngredientName = new ArrayList<>();
        for (Recipe recipe : recipes) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                if (ingredient.getName().equals(ingredientName)) {
                    recipesWithIngredientName.add(recipe);
                    break;
                }
            }
        }

        return recipesWithIngredientName;
    }

    public List<Recipe> getRecipesWithName(String recipeName) {
        ArrayList<Recipe> recipesWithName = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(recipeName)) recipesWithName.add(recipe);
        }

        return recipesWithName;
    }

    public ArrayList<Ingredient> getIngredientsFromRecipe(String recipeName) {
        ArrayList<Ingredient> recipeIngredients = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(recipeName)) {
                recipeIngredients.addAll(recipe.getIngredients());
            }
        }
        return recipeIngredients;
    }

    public String getRecipeInstructions(String recipeName) {
        String instructions = "";
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(recipeName)) {
                instructions = instructions + (recipe.getInstructions());
                break;
            }
        }
        return instructions;
    }

    public void setRecipeInstructions(String editInstructions, String recipeName) {
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(recipeName)) {
                recipe.setInstructions(editInstructions);
                break;
            }
        }
    }

    public void setRecipeName(String recipeName, String editRecipe) {
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(recipeName)) {
                recipe.setName(editRecipe);
                break;
            }
        }
    }

    public void insertRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    // Returns true if recipes is changed as a result of this call
    public boolean insertRecipes(List<Recipe> newRecipes) {
        return recipes.addAll(newRecipes);
    }

    // Returns true if the recipe exists in recipes.
    public boolean deleteRecipe(Recipe recipe) {
        if (recipes.remove(recipe)) {
            if (recipe.isDefault()) {
                hiddenRecipes.add(recipe);
            }
            return true;
        }
        return false;
    }

    public List<Recipe> getHiddenRecipes() {
        return hiddenRecipes;
    }

    private void fillRecipes(ArrayList<Recipe> recipes) {
        ArrayList<Ingredient> ingredients;
        ingredients =
                new ArrayList<>(
                        Arrays.asList(
                                new Ingredient("Balsamic Vinegar", new Cup(3 * QUARTER)),
                                new Ingredient("Basil Leaves", new Cup(QUARTER)),
                                new Ingredient("Olive Oil", new Tablespoon(2)),
                                new Ingredient(
                                        "Plum Tomatoes", new Cup(4)), // TODO: Fix to quantity
                                new Ingredient(
                                        "Boneless Skinless Chicken Breast",
                                        new Cup(4)))); // TODO: Fix to quantity
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
        recipes.add(new Recipe("Grilled Basil Chicken", ingredients, instructions, true));

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
                                new Ingredient("Honey", new Cup(1)) // TODO: Fix to quantity
                        ));

        instructions =
                "Add to your bread machine per manufacturer instructions.\n"
                        + "While bread is baking drizzle with honey if desired.";

        recipes.add(new Recipe("Sweet Honey French Bread", ingredients, instructions, true));

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

        recipes.add(new Recipe("Crushed Heirloom Potatoes", ingredients, instructions, true));

        ingredients =
                new ArrayList<>(
                        Arrays.asList(
                                new Ingredient(
                                        "Pastry Double Crust Pie",
                                        new Cup(1)), // TODO: Fix to quantity
                                new Ingredient("Apple", new Cup(6)), // TODO: Fix to quantity
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

        recipes.add(new Recipe("Heirloom Apple Pie", ingredients, instructions, true));
    }

    public DaySchedule getDayScheduleOrDefault(Date date) {
        return this.schedule.getDayScheduleOrDefault(date);
    }

    public void fillSchedule(Schedule schedule) {
        DaySchedule daySchedule = new DaySchedule();
        assert (recipes.size() > 0);
        Recipe first_recipe = recipes.get(0);
        daySchedule.setMeal(DaySchedule.Meal.BREAKFAST, first_recipe);
        // Set the sample schedule to be for today's date
        schedule.setDaySchedule(Calendar.getInstance().getTime(), daySchedule);

        // Different meal on next day
        DaySchedule nextDaySchedule = new DaySchedule();
        Date nextDay = CalendarUtils.incrementDay(Calendar.getInstance().getTime(), MealSchedule.DAY_INCREMENT);
        Recipe lunch = recipes.get(1);
        nextDaySchedule.setMeal(DaySchedule.Meal.LUNCH, lunch);
        schedule.setDaySchedule(nextDay, nextDaySchedule);

        // Same meal on the same day
        daySchedule.setMeal(DaySchedule.Meal.DINNER, first_recipe);
        // Same meal on different days
        nextDaySchedule.setMeal(DaySchedule.Meal.DINNER, first_recipe);
    }

    public String getDbName() {
        return this.dbName;
    }

    public String getDbType() {
        return this.dbType;
    }
}
