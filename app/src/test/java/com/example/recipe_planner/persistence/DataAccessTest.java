package com.example.recipe_planner.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.recipe_planner.application.Main;
import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.objects.measurements.Gram;
import com.example.recipe_planner.objects.measurements.Count;
import com.example.recipe_planner.objects.measurements.Cup;
import com.example.recipe_planner.objects.measurements.Tablespoon;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DataAccessTest {

    private static final double QUARTER = 1.0 / 4.0;
    private static final double DELTA = 0.001;
    private DataAccess dataAccess;

    @Before
    public void setUp() {
        System.out.println("\nStarting Persistence test DataAccess");
        dataAccess = new DataAccessStub();
        dataAccess.open(Main.dbName);
    }

    @After
    public void tearDown() {
        System.out.println("Finished Persistence test DataAccess (using stub)");
        Services.closeDataAccess();
    }

    @Test
    public void testDeleteInvalidRecipeIndex(){
        boolean deleted;

        deleted = dataAccess.deleteRecipe(-1);
        assertFalse(deleted);

        List<Recipe> recipes;
        recipes = dataAccess.getRecipes();
        assertNotNull(recipes);
        assertEquals(4, recipes.size());
    }

    @Test
    public void testDeleteDefaultRecipe() {
        boolean deleted;

        deleted = dataAccess.deleteRecipe(3);
        assertTrue(deleted);

        List<Recipe> recipes;
        recipes = dataAccess.getRecipes();
        assertNotNull(recipes);
        assertEquals(3, recipes.size());
    }

    @Test
    public void testGetDefaultRecipes() {
        List<Recipe> recipes;

        recipes = dataAccess.getRecipes();
        assertNotNull(recipes);
        assertEquals(4, recipes.size());

        Recipe recipe = recipes.get(0);
        assertNotNull(recipe);

        equalToGrilledChicken(recipe);
    }
        
    @Test
    public void testGetRecipesWithInvalidPartialName() {
        List<Recipe> recipes;
        recipes = dataAccess.getRecipesWithPartialName("Recipe that doesn't exist ^_^");
        assertNotNull(recipes);

        assertTrue(recipes.isEmpty());
    }

    @Test
    public void testGetRecipesWithValidPartialName() {
        List<Recipe> recipes;
        recipes = dataAccess.getRecipesWithPartialName("basil chicken");
        assertNotNull(recipes);
        assertEquals(1, recipes.size());

        Recipe recipe = recipes.get(0);
        assertNotNull(recipe);

        equalToGrilledChicken(recipe);
    }

    @Test
    public void testGetRecipesWithNullPartialName() {
        List<Recipe> recipes;
        recipes = dataAccess.getRecipesWithPartialName(null);
        assertNotNull(recipes);

        assertTrue(recipes.isEmpty());
    }

    @Test
    public void testGetRecipesWithPartialNameWithMultipleResults() {
        List<Recipe> recipes;
        String query = "heirloom";
        recipes = dataAccess.getRecipesWithPartialName(query);
        assertNotNull(recipes);

        assertEquals(2, recipes.size());

        Recipe recipe1 = recipes.get(0);
        assertNotNull(recipe1);
        String recipe1Name = recipe1.getName();
        assertNotNull(recipe1Name);
        assertTrue(recipe1Name.toLowerCase().contains(query));

        Recipe recipe2 = recipes.get(1);
        assertNotNull(recipe2);
        String recipe2Name = recipe2.getName();
        assertNotNull(recipe2Name);
        assertTrue(recipe2Name.toLowerCase().contains(query));
    }

    @Test
    public void testGetIngredientsFromInvalidRecipeIndex() {
        List<Ingredient> ingredients;

        ingredients = dataAccess.getRecipeIngredients(-1);
        assertEquals(0, ingredients.size());
    }

    @Test
    public void testGetDefaultIngredients() {
        List<Ingredient> ingredients;

        // get first default recipe
        ingredients = dataAccess.getRecipeIngredients(0);
        assertNotNull(ingredients);
        assertEquals(5, ingredients.size());

        // test all ingredients
        Ingredient ingredient = ingredients.get(0);
        assertNotNull(ingredient);
        assertEquals("Balsamic Vinegar", ingredient.getName());
        assertEquals(0.75, ingredient.getAmount(), DELTA);

        ingredient = ingredients.get(1);
        assertNotNull(ingredient);
        assertEquals("Basil Leaves", ingredient.getName());
        assertEquals(0.25, ingredient.getAmount(), DELTA);

        ingredient = ingredients.get(2);
        assertNotNull(ingredient);
        assertEquals("Olive Oil", ingredient.getName());
        assertEquals(2.0, ingredient.getAmount(), DELTA);

        ingredient = ingredients.get(3);
        assertNotNull(ingredient);
        assertEquals("Plum Tomatoes", ingredient.getName());
        assertEquals(4.0, ingredient.getAmount(), DELTA);

        ingredient = ingredients.get(4);
        assertNotNull(ingredient);
        assertEquals("Boneless Skinless Chicken Breast", ingredient.getName());
        assertEquals(4.0, ingredient.getAmount(), DELTA);
    }

    @Test
    public void testValidIngredientModification() {
        List<Ingredient> ingredients;
        Ingredient ingredient;

        // get first default recipe
        ingredients = dataAccess.getRecipeIngredients(0);
        assertNotNull(ingredients);
        assertEquals(5, ingredients.size());

        ingredient = ingredients.get(0);

        // modify the 1st ingredient with valid inputs
        dataAccess.updateIngredientQuantity(0, 45.0, ingredient.getName());
        assertEquals(45.0, ingredient.getAmount(), DELTA);

        ingredients = dataAccess.getRecipeIngredients(0);

        assertEquals(new Ingredient("Balsamic Vinegar", new Cup(45.0)), ingredients.get(0));
    }

    @Test
    public void testInvalidIngredientModification() {
        List<Ingredient> ingredients;
        Ingredient ingredient;

        // get a non-existent default recipe (out of bounds)
        ingredients = dataAccess.getRecipeIngredients(10);
        assertNotNull(ingredients);
        assertEquals(0, ingredients.size());

        // modify the ingredients in the invalid recipe with valid inputs
        try {
            ingredient = ingredients.get(0);
            dataAccess.updateIngredientQuantity(10, 45.0, ingredient.getName());
            assertEquals(45.0, ingredient.getAmount(), DELTA);
        }
        catch (Exception e){
            System.out.println("Null/invalid recipe's ingredients cannot be modified.");
        }

        // get second default recipe
        ingredients = dataAccess.getRecipeIngredients(1);
        assertNotNull(ingredients);
        assertEquals(8, ingredients.size());
        try{
            double testDouble = Double.parseDouble("");
            ingredient = ingredients.get(2);
        // modify the ingredients in a valid recipe with invalid inputs, this won't change the data
            dataAccess.updateIngredientQuantity(1, testDouble, ingredient.getName());
            assertEquals(2.0, ingredient.getAmount(), DELTA);
            }
        catch (NumberFormatException n){
           System.out.println("Invalid input, empty inputs or other non-double inputs will cause the program to error out.");
        }

    }

    @Test
    public void testValidIngredientDeletion() {
        List<Ingredient> ingredients;
        Ingredient ingredient;

        // get first default recipe
        ingredients = dataAccess.getRecipeIngredients(0);
        assertNotNull(ingredients);
        assertEquals(5, ingredients.size());

        ingredient = ingredients.get(0);
        String unit = ingredient.getUnit().getClass().getSimpleName();

        // delete the first ingredient from the recipe
        System.out.println(dataAccess.deleteIngredient(0,ingredient.getName(), ingredient.getAmount(), unit));
        ingredients = dataAccess.getRecipeIngredients(0);
        assertEquals(4, ingredients.size());
    }

    @Test
    public void testInvalidIngredientDeletion() {
        List<Ingredient> ingredients;
        Ingredient ingredient;

        // get first default recipe
        ingredients = dataAccess.getRecipeIngredients(0);
        assertNotNull(ingredients);
        assertEquals(5, ingredients.size());

        try
        {
        ingredient = ingredients.get(5);
        String unit = ingredient.getUnit().getClass().getSimpleName();

        // delete the an invalid ingredient from the recipe
        System.out.println(dataAccess.deleteIngredient(0,ingredient.getName(), ingredient.getAmount(), unit));
        ingredients = dataAccess.getRecipeIngredients(0);
        assertEquals(4, ingredients.size());
        }
        catch (Exception e){
            System.out.println("Out of bounds deletions will be met with an OutofBoundsException.");
        }

            ingredient = ingredients.get(0);
            String unit = ingredient.getUnit().getClass().getSimpleName();

            // delete the an valid ingredient with invalid quantity from the recipe
            System.out.println(dataAccess.deleteIngredient(0,ingredient.getName(), 1000.0, unit));
            ingredients = dataAccess.getRecipeIngredients(0);
            assertEquals(5, ingredients.size());

    private void equalToGrilledChicken(Recipe recipe) {
        // check name
        assertEquals("Grilled Basil Chicken", recipe.getName());

        // check ingredients
        ArrayList<Ingredient> ingredients = recipe.getIngredients();
        assertEquals(5, ingredients.size());
        assertEquals(new Ingredient("Balsamic Vinegar", new Cup(3 * QUARTER)), ingredients.get(0));
        assertEquals(new Ingredient("Basil Leaves", new Cup(QUARTER)), ingredients.get(1));
        assertEquals(new Ingredient("Olive Oil", new Tablespoon(2)), ingredients.get(2));
        assertEquals(new Ingredient("Plum Tomatoes", new Count(4)), ingredients.get(3));
        assertEquals(
                new Ingredient("Boneless Skinless Chicken Breast", new Count(4)),
                ingredients.get(4));

        // check instructions
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
        assertEquals(instructions, recipe.getInstructions());

        // check if default
        assertTrue(recipe.isDefault());
    }
}
