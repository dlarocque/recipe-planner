# Development Log – Iteration 1

Team 5

- Anthony Phung ([@tormin4](https://github.com/tormin4))
- Daniel La Rocque ([@dlarocque](https://github.com/dlarocque))
- Emerson Lesage-Dong ([@emersonlesage](https://github.com/emersonlesage))
- Izan Cuetara Diez ([@Unstavle](https://github.com/Unstavle))
- Samuel Barrett ([@samuelbarrett](https://github.com/samuelbarrett))

# Meetings

June 21, 2022

## Iteration 2 Planning

Discussed requirements to complete by the end of iteration 2, and the big user stories that the team would work on during the iteration
Members assigned themselves to user stories
Created and modified user stories that to be more appropriate to requirements
Created user stories to satisfy database requirements
Members created developer tasks for their assigned user stories
Members assigned themselves to developer tasks and provided hour estimates for them

## Discussions / Decisions

Team has agreed to finishing tasks 3 days before the iteration deadline (July 9) to give time for refactoring, clean-up and incomplete stories to be completed
Team decided to refactor Unit classes to Count and ConvertibleUnit, and hence refactoring conversion methods for easier use and testing. This was based on feedback received for Iteration 1 and discussion of several possible improvements.
Log
(name) 
(description of task)
(time spent on task)

# Log

```
Sam
Writing new and modified user story cards for IT-2 (document)
1 hour

Sam
Reading sample DB code and learning about HSQLDB
30 mins

Daniel
Creating Schedule View Page
3 hours

Daniel
Creating Schedule and DaySchedule classes and integrating with Schedule Fragment
2 hours

Sam
Added HSQLDB to project, wrote DB script to match stub
1.5 hours

Daniel
Writing Unit Tests for Schedule and DaySchedule
2 hours

Sam
Investigating/adding requirements and fixes to-do, based on IT-1 feedback
30 mins

Sam
Investigating alternatives to units of measurement classes/design based on feedback
2 hours

Emerson
Worked on logic for adding recipes and looked at database interface
2 hours

Daniel
Made changes based on code review for schedule view PR
30 minutes

Daniel
Added functionality for selecting dates to schedule recipes for
3.5 hours

Daniel
Added button to delete scheduled recipes
1 hour

Daniel
Added save button to meal schedule UI
1.5 hours

Daniel 
Created stub data and data access for saved schedules, with unit tests
1.5 hours

Sam
refactor units of measurement: new and improved conversion
2 hours

Sam
add db to device files on launch, fix primary keys, investigate CLI db access (SqlTool, JDBC)
1.5 hours

Anthony
Explored LiveData as a possible option for dynamic recipe ingredient lists (Add, delete, edit, etc.)
3 hours

Emerson
Dealt with gradle file sync error
1.5 hours

Sam
Wrote unit tests for improved Unit classes and conversion
1.5 hours

Emerson
Partially implemented database interface. Investigated error in which db wasn't properly being initialized
4 hours

Sam
Fixed a bug with db script: separate population from initialization to circumvent weird sql errors
3.5 hours

Emerson
Helped debug db script. Testing different SQL scripts and file locations
1.5 hours

Anthony 
Started work on feature branch view_ingredients
1 hour

Anthony
Started work on editing a recipe’s ingredients 
2.5 hours

Daniel
Fixed bug where a deleted recipe was able to be displayed in a meal schedule
1 hour

Izan
Started work on feature: search recipes (added search bar to recipe list page)
6 hours

Sam
Implement database, its initialization and a few queries
4 hours

Sam
Fix RecipeView to conform to Access/DataAccess, utilize Recipe IDs
1 hour

Anthony
Finished main ingredient viewing features in recipeview fragment
6 hours

Anthony
Started work on ingredient editing view and logic
4 hours

Sam
Get database working in place of stub
2 hours

Emerson
Completed initial unit tests for DataAccess interface with stub db
2 hours

Sam
Finish new units and conversions, wrote tests, refactors for suggested changes
1.5 hours

Izan
Finished Search recipes feature, including fixes after rebasing with real DB changes
8 hours

Daniel
Refactored hsqldb query code
1 hour

Daniel
Added schedule data and queries in hsqldb
1.5 hours

Daniel
Integrated schedule changes with database, as opposed to the stub database
4 hours

Daniel
Resolved and merged conflicts between database and schedule database branches
1 hour

Izan
Wrote Unit Tests for the Search recipes feature
2 hours

Sam
Copiloting with team members to resolve merge conflicts, bugs, and help learn about persistence layer functionality
3 hours

Emerson
Assisted with resolving merge conflicts
1 hour

Izan
Resolved conflicts from rebasing onto develop after other changes, and merged
1 hour

Anthony
Finished UI for editing, deleting recipe ingredients. Started work on backend interactions and logic behind actions.
4 hours

Anthony
Resolved merge conflicts between database and view ingredients branches
1 hour

Daniel
Integrated save day schedule feature with the database
1.5 hours

Emerson
Added additional unit tests for db interface
1.5 hours


Emerson
Created dev task and user stories doc
1 hour

Sam
Updating README and architecture documentation
1 hour

Anthony
Finished writing tests
4 hours

Daniel
Wrote unit tests for the business layer
2 hours

Daniel
Updated README.md before submission
1 hour
```

# Outstanding Bugs

Documented in `README.md`.