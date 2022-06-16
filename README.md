# Recipe Planner

**[GitHub Repository](https://github.com/dlarocque/recipe-planner)**

## Outline

[Team Members](#team-members)

[Featues](#features)

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

## Features

**Viewing all recipes**

Upon entering the app the user is able to view a list of recipes in a card layout, with recipe names and a preview image.

**Viewing a recipe**

From the list of recipes at the landing page of the application, a user is able to click on a recipe card to get a full-screen view of the recipe, containing the name of the recipe and recipe instructions.

**Editing a recipe**

From the full-screen view of a recipe, the user is able to click on the text boxes containing the recipe name and instructions to then be able to modify the text.  Any modifications to recipes will be persistent throughout the app, and are reflected when the user exits the full-screen view of the recipe.

**Deleting a recipe**

Once viewing a recipe, the user can delete the recipe by tapping the trash icon in the upper right corner of the window. A dialog prompt will appear, asking the user to confirm or cancel their action. Once confirmed, the app will return to the recipe list, and the deleted recipe will no longer appear in the list.

## Missing features

**Creating a recipe**

We decided to exclude our implementation of creating a new recipe due to issues we encountered with floating buttons and linking to the Create A Recipe fragment. Resolving the issues proved too time-intensive under the conditions, so we stuck to Agile – to be included in a future iteration.

## Architecture

![Architecture Diagram](/docs/architecture-diagram.pdf)

## Project Structure

Important source code files are located at `recipe-planner/app/src/main/java/com/example/recipe_planner/`, the important packages within this directly are as follows.

### `application`
Contains the service that the presentation layer uses to access its instance of the stub database.

- `Service.java` allows the presentation layer to initialize the stub database, or access it if it has already been initialized

### `business`
Contains the business layer of our application, that is in the middle of our three-layer architecture (betweem presentation and persistence).

- `AccessRecipes.java` defines an interface that our presentation layer can call to interact with the persistence layer.

### `objects`
Contains domain-specific objects.

- `Ingredient.java` is the class that defines an ingredient with a name, and a quantity of a unit (e.g. grams).
- `Recipe.java` is the class that defines a recipe with a name, a list of ingredients, and instructions
- `measurements` is a package that contains a hierarchy of objects that define all of the possible units that ingredients can be, as well as the conversions between these units


### `persistence`

Contains the stub database that is currently used to store a list of recipes.

- `DataAccessStub.java` acts as the applications stub database.

### `presentation`

Contains the major UI elements for the application.

- `MainActivity.java` is the main activity for the application, and is responsible for setting up components that are present throughout the application (app bar, navigation bar)
- `MealSchedule.java` is a stub fragment that simply contains a text view, this will be implemented in future iterations
- `RecipeList.java` is a fragment representing a scrollabe, dynamic list of recipes, in a card view containing recipe names and preview images
- `RecipeRecyclerViewAdapter.java` is a RecyclerView adapter that can display a Recipe, this is used to display recipes in the list of recipes.
- `ShoppingList.java` is a stub fragment that simply contains a text view, this will be implemented at a later date

## Testing

### Environment

The application was tested on a Nexus 7 tablet, with Android 6.0 Marshmallow (API level 23).

### Unit Tests

This project uses GitHub actions to automate testing on all pull requests and all pushes to `develop` and `master` branches.

- Ubuntu latest
- Script: `./gradlew test`
- [AllTests.java](/app/src/test/java/com/example/recipe_planner/AllTests.java)


## Changelog

Changelog is located at `/docs/CHANGELOG.md` ([GitHub](https://github.com/dlarocque/recipe-planner/blob/iteration1/docs/CHANGELOG.md]))

## Development Log

