# Development Log – Iteration 3

Team 5

- Anthony Phung ([@tormin4](https://github.com/tormin4))
- Daniel La Rocque ([@dlarocque](https://github.com/dlarocque))
- Emerson Lesage-Dong ([@emersonlesage](https://github.com/emersonlesage))
- Izan Cuetara Diez ([@Unstavle](https://github.com/Unstavle))
- Samuel Barrett ([@samuelbarrett](https://github.com/samuelbarrett))

# Meetings

## Iteration 3 Planning – July 18, 2022

Completed Retrospective activity as a team
Discussed requirements to complete by the end of iteration 3, and the big user stories that the team would work on during the iteration
Members assigned themselves to user stories
Created and modified user stories that to be more appropriate to requirements
Created user stories to satisfy database requirements
Members created developer tasks for their assigned user stories
Members assigned themselves to developer tasks and provided hour estimates for them

## Iteration 3 handin - July 28, 2022

Team met for conclusion of iteration 3, including solving merge conflicts, discussing final changes and completing documentation.

## Discussions / Decisions

Team decided that we couldn't mock the external library necessary to properly test recipe scheduling through the UI.
Team decided not to include feature: adding new recipes, on the basis of testing difficulties and time constraints.

# Log

```
Sam
Documented new user stories and dev tasks for iteration 3 submission
30 minutes

Daniel
Outlined Integration Tests
30 minutes

Daniel
Outlined Acceptance tests
1 hour

Daniel
Resolved gradle dependency issues with espresso 
3 hours

Izan
Fixed bug with recipe filtering
3 hours

Izan
Looked into issue resetting the database
2 hours

Daniel
Investigating and discussing integration tests
2 hours

Sam
Investigating and discussing integration tests
3 hours

Anthony
Worked on displaying all current and future required ingredients for scheduled meals
5 hours

Daniel
Created test database
1 hour

Daniel
Wrote PersistenceHSQLDB seam tests
1.5 hours

Daniel
Resolved issues around testing fragments with RecyclerViews, and dependency issues with new related espresso testing libraries
4 hours

Daniel
Debugged issue relating to test database not opening successfully
1 hour

Daniel
Wrote acceptance tests for recipe lists
1 hour

Izan
Updated gitignore
15 minutes

Izan
Worked on displaying ingredient amounts in Shopping List
8 hours

Izan
Investigated database issues and rebased shopping list branch
2 hours

Anthony 
Reviewed pull requests
30 minutes

Emerson
Looked at pull requests
1 hour

Sam
Added more thorough unit tests to Access classes
2 hours

Sam
Fixed persistence issues with HSQLDB commits, resetting and re-initialization.
3 hours

Sam
Fixed creation of Unit classes when re-constructing objects to return from DB query function
30 minutes

Anthony
Refactored and reformatted UI code 
1 hour

Izan
Fixed some issues with converting units for the shopping list page and other little things
3 hours

Izan
Reviewed some PRs
1.5 hour

Izan
Worked on some more shopping list stuff
2 hours

Anthony
Fixed critical errors that caused crashes based on user input in edit 
4 hours

Daniel
Shared information about acceptance tests approach and issues with team members who were writing acceptance tests
1 hour

Emerson
Added insertion functions as well as unit tests and a persistence test for them
6 hours

Izan
Worked on editing units of ingredients and tests
5 hours

Sam
Added support for fetching all scheduled recipes to stub (w/ tests)
1 hour

Emerson
Looked into Dialog UI elements
1 hour

Anthony
Finished correcting the behaviour of editing ingredient names and amounts
5 hours

Anthony
Wrote acceptance tests for shoppinglist and editIngredients
3 hours

Daniel
Added acceptance tests for navigation
1 hour

Anthony
Fixed up previous unit test resilience
1 hour

Izan
Finished work on editing units drop down and its unit tests and merged with main branch
5 hours

Sam
Fixed date formatting to allow correct sorting DB tables by date
1 hour

Anthony 
Finishing touches on code
30 minutes

Emerson 
Finished UI and logic for adding new recipes
6.5 hours

Daniel
Resolving merge conflicts and merged all pending pull requests, regression testing
2 hours

Daniel
Added acceptance tests for shopping list
45 mins
```

# Outstanding Bugs

Documented in `README.md`.