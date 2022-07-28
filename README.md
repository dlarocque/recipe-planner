# Recipe Planner

**[GitHub Repository](https://github.com/dlarocque/recipe-planner)**

## Outline

[Team Members](#team-members)

[Features](#features)

[Architecture](#architecture)

[Project Structure](#project-structure)

[Testing](#testing)

[Changelog](#changelog)

[Development Log](#development-log)

## Team Members

- Anthony Phung ([@tormin4](https://github.com/tormin4))
- Daniel La Rocque ([@dlarocque](https://github.com/dlarocque))
- Emerson Lesage-Dong ([@emersonlesage](https://github.com/emersonlesage))
- Izan Cuetara Diez ([@Unstavle](https://github.com/Unstavle))
- Samuel Barrett ([@samuelbarrett](https://github.com/samuelbarrett))

# Features IT-1

**Viewing all recipes**

Upon entering the app the user is able to view a list of recipes in a card layout, with recipe names and a preview image.

**Viewing a recipe**

From the list of recipes at the landing page of the application, a user is able to click on a recipe card to get a full-screen view of the recipe, containing the name of the recipe and recipe instructions.

**Editing a recipe**

From the full-screen view of a recipe, the user is able to click on the text boxes containing the recipe name and instructions to then be able to modify the text.  Any modifications to recipes will be persistent throughout the app, and are reflected when the user exits the full-screen view of the recipe.

**Deleting a recipe**

Once viewing a recipe, the user can delete the recipe by tapping the trash icon in the upper right corner of the window. A dialog prompt will appear, asking the user to confirm or cancel their action. Once confirmed, the app will return to the recipe list, and the deleted recipe will no longer appear in the list.

# Features IT-2

**Daily Meal Schedule**

Users can now schedule a meal by clicking on the + (PLUS) icon on a recipe from the Recipes List. A dialog will allow them to select a date, and then select which meal (breakfast, lunch or dinner) the recipe should be applied to.

In the Schedule tab, users will see meals they have added to any day, to keep track of their meal plans. They can scroll through the days using the arrows at the top, and can remove a recipe from a meal slot if they wish.

**Search for recipes**

Users can now search for recipes they want to see! In the Recipes List, a user can click the search button in the upper-right to open a textbox and search for the name of any recipe in the system. The list of recipes will filter accordingly.

**View, Edit and Delete Ingredients**

Users can now view the ingredients of any recipe from within the Recipe View. Clicking on the Edit (PENCIL) button beside the list of ingredients will display the ingredients in a full-screen view. From here, the user can edit quantities of any ingredient, or delete the ingredient from the recipe.

**Persistent Data**

With a real database powered by HSQLDB, changes now persist after closing/relaunching the app. Users benefit from the safety of knowing their changes have been saved to the database.

# Features IT-3

**Shopping List**

The Shopping List page is now fully functional! The Shopping List adds ingredients from all recipes scheduled in the future (including current day) so users can see exactly what they need for upcoming meals. When a meal is removed from the Schedule, the Shopping List is adjusted accordingly. Furthermore, all ingredients are converted to Grams or Millilitres to match common product labels in grocery stores.

**Editing and Deleting Ingredients**

Upon entering the Edit Ingredients page on a Recipe (Pencil button), a user can properly edit and remove ingredients from a recipe! These changes are persistent and enable greater flexibility for user customization of meals.

# FIXED

- Deleting ingredients from a recipe is now fully functional and persistent.
- Modifying ingredients of a recipe is fully functional, persistent, and no longer crashes the application.
- Fixed issue with incorrect list indices while Searching recipes (filtered list problem)
- Fixed date formatting of Scheduled meals to allow sorting in database.
- Unit tests are now more thorough and execute properly.

# KNOWN ISSUES

## Search Button

 - When the search bar on the recipe list page is opened and the keyboard is closed, the search bar remains open.
 - The search remains active until the text in the search box is cleared.
 - When a search is done and recipes are filtered, the incorrect recipe will be opened when a recipe is clicked.  It will open the recipe at the position that that recipe would be in the original, unfiltered list.

## Settings page

The settings page was not implemented in this iteration, so when a user attempts to visit the settings page by clicking
the three dots at the top-right corner of the application then 'settings', nothing will happen.

## Recipe Instructions

Some recipe instructions contain text with '\n'.
Also, recipe instructions with long instructions, the user cannot scroll through the long instructions since the edit page will open.

#

## Architecture

![Architecture Diagram](/docs/architecture-diagram.pdf)

## Project Structure

Important source code files are located at `recipe-planner/app/src/main/java/com/example/recipe_planner/`, the important packages within this directly are as follows.

### `application`

Contains the service that the presentation layer uses to access its instance of the stub database.

- `Main.java` contains the name of the application and the location of the database, and will be useful when a CLI version of the application is created
- `Service.java` allows the presentation layer to initialize the stub database, or access it if it has already been initialized

### `business`

Contains the business layer of our application, that is in the middle of our three-layer architecture (between presentation and persistence).

- `AccessRecipes.java` defines an interface that our presentation layer can call to interact with the recipe data in the persistence layer.
- `AccessSchedule.java` defines an interface that our presentation layer can call to interact with the schedule data in the persistence layer.
- `AccessIngredients.java` defines an interface that our presentation layer can call to interact with the ingredient data in the persistence layer.

### `objects`

Contains domain-specific objects.

- `Ingredient.java` is the class that defines an ingredient with a name, and a quantity of a unit (e.g. grams).
- `Recipe.java` is the class that defines a recipe with a name, a list of ingredients, and instructions
- `measurements` is a package that contains a hierarchy of objects that define the possible units that ingredients can be, as well as the conversions between these units
- `Schedule.java` defines the schedule, which models the Schedule page: it holds DaySchedules for each day.
- `DaySchedule.java` defines the schedule for a day, and represents a set of meals (breakfast, lunch, dinner).

### `persistence`

Contains the stub database that is currently used to store a list of recipes.

- `DataAccess.java` defines the interface that will be implemented by our database and stub database.
- `DataAccessDB.java` implements the `DataAccess.java` interface, and implements all of the methods to interact with the HSQLDB

### `presentation`

Contains the major UI elements for the application.

- `MainActivity.java` is the main activity for the application, and is responsible for setting up components that are present throughout the application (app bar, navigation bar)
- `MealSchedule.java` is a fragment representing a daily meal schedule, three meals, and the navigation through each day
- `RecipeList.java` is a fragment representing a scrollable, dynamic list of recipes, in a card view containing recipe names and preview images
- `RecipeRecyclerViewAdapter.java` is a RecyclerView adapter that can display a Recipe, this is used to display recipes in the list of recipes.
- `ShoppingList.java` is a stub fragment that simply contains a text view, this will be implemented at a later date
- `EditIngredientList.java` is a list of ingredients that are editable
- `EditIngredientListRecyclerViewAdapter.java` is the adapter for editable ingredients that is used in the list of ingredients to edit
- `IngredientRecyclerViewAdapter.java` is the adapter for the list of ingredients in the recipe view
- `ShoppingList.java` is a fragment that defines the Shopping List page, where all ingredients for recipes the user has scheduled are displayed.
- `ShoppingListRecyclerViewAdapter.java` is the adapter for the list of ingredients found in the ShoppingList fragment.

## Testing

### Environment

The application was tested on the Android Studio emulator for the Nexus 7 tablet, running Android 6.0 Marshmallow (API level 23).

### ACCEPTANCE TESTING NOTE

Our acceptance tests do not cover the case where the user schedules recipes through the UI. This is due to an external library we use for the Calendar/date picker dialog that would require mocks to run the Espresso tests. We are unable to implement this mock.

We attempt to overcome this by manually scheduling meals through AccessSchedule within the acceptance tests, so we can still verify through the UI that meals have been scheduled and can be deleted.

### Run all tests

- Ubuntu latest
- Script: `./gradlew test`
- [AllTests.java](/app/src/test/java/com/example/recipe_planner/AllTests.java)

## Development Log

[log.md](/docs/log.md)