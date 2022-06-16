# Development Log – Iteration 1

Team 5

- Anthony Phung ([@tormin4](https://github.com/tormin4))
- Daniel La Rocque ([@dlarocque](https://github.com/dlarocque))
- Emerson Lesage-Dong ([@emersonlesage](https://github.com/emersonlesage))
- Izan Cuetara Diez ([@Unstavle](https://github.com/Unstavle))
- Samuel Barrett ([@samuelbarrett](https://github.com/samuelbarrett))

# Meetings

## June 2, 6 PM (1 hour)
Selected “As a user, I want to be able to create/remove/edit recipes” as the big user story to focus on for iteration 1.  Team members agreed that this user story seemed to be the most fundamental feature the application needs (creating, removing, and editing are the primary features of the app), and would provide a good base to begin working on later features.

Team members spent five minutes creating developer stories for each of the user stories that belong to the selected big user story, and then refined these stories together.  Some developer stories were created that did not necessarily belong to any user stories (e.g. setting up GitHub actions).

Gave estimates for development tasks (hours)
Once developer stories were agreed upon by all team members, the team then went over each of the developer stories and discussed which tasks needed to be done to complete each story and provided a time estimate (hours) based on the complexity and size of those tasks.


Team members then highlighted which developer stories they would complete over the iteration.  Discussion was made on how many hours each team member could commit.


## June 13, 4:30 PM (40 minutes)
Did an overview of what features have been done so far, and the basic structure of the codebase.

Discussed how the remaining stories should be completed, as well as discussing who’d be working on what for the days leading up to the submission date.

## June 15, 9:45 PM (30 minutes)
Resolving merge conflicts and working out bugs regarding creating recipes.
We summarised our work for the iteration and which features we weren’t able to complete on time. Made a plan for editing, revising, cleaning up code and submission of our release.

# Decisions

### June 2 (All members)
We will use GitHub actions runners to verify that the application builds before each PR, and that all tests pass.

### June 2 (All members)
We agreed upon a branching strategy that was detailed in the group chat, based on what most students used during their co-op work terms.

### June 9 (All members)
We agreed to follow a minimal commenting style, opting to let well-written code explain itself and reserve comments to situation where readability of a complex flow may be unclear or time-consuming to parse.

### June 9 (Daniel, Izan, Sam)
We will implement basic navigation through the app before moving on to other UI stories, as it will ease the development of the stories.

### June 10 (Sam, Daniel)
We will use google-java-format AOSP style (an Android Studio Plugin) as a standard for code formatting.
Auto-formatters in GitHub CI/CD are very limited, so we will need to stick to using the formatter in the IDE.

### June 12 (Daniel, Sam)
We will add a “Count” unit for ingredients that are simply an amount of something

### June 15 (All members)
Decided to exclude the feature of creating new recipes from the iteration release, due to difficulties with Android fragments and time constraints.

# Individual work

	Sam
	Create application architecture diagram. PR
	15 mins

	Izan
	Learned about interactive components in Figma
	1 hour

	Izan
	Create UI draft in Figma
	2 hour

	Daniel
	Initialize Android Project. PR
	30 mins

	Anthony
	Created Kanban board cards on Github for assigned work
	5 mins

	Daniel
	Add pre-commit configuration to run formatters after each commit. PR
	30 minutes

	Daniel
	Set up GitHub actions (Unit tests, build) to run after each change in a Pull Request and push to `develop` and `master` branches. PR
	2 hours

	Sam
	Create recipe, ingredient, IUnit class hierarchy. PR
	3 hours

	Daniel
	Add a recipe list page PR
	3 hours

	Sam
	Create unit tests for Recipe, Ingredient, IUnit classes. PR
	3 hours

	Daniel
	Learned and documented the basics of Android Development
	4 hours

	Daniel
	Implemented app navigation and navigation bar PR
	4 hours

	Daniel
	Created stub database PR
	2 hours

	Daniel
	Created business layer PR
	1 hour

	Izan
	Began implementing logic for deleting or hiding recipes based on default vs user-made
	1 hour

	Sam
	Learned about fragments/views and navigation
	1.5 hours

	Anthony
	Learned the basics of fragment-based navigation, bundling
	2 hours

	Anthony
	Acclimated to the existing codebase, started feature branch
	2 hours

	Sam
	Began implementing RecipeView, was completed separately in PR.
	1 hour

	Anthony
	Created fragment for editing recipes, text fields for editing recipe name and instructions, getters and setters for the db PR
	7 hours

	Daniel
	Made some touch-ups to the recipe view
	1.5 hours

	Daniel
	Worked on documentation in README.md  PR
	30 minutes

	Sam
	Touching up/reviewing README.md
	15 minutes

	Sam
	Implemented testing suites (AllTests, ObjectTestSuite) PR,
	1 hour

	Sam
	Updated Recipe tests
	15 minutes

	Emerson
	Created feature branch, familiarized self with android studio and the repo
	1 Hour

	Emerson
	Learned about fragments and navigation
	1.5 Hours

	Emerson
	Worked on button to navigate to create recipe fragment
	2 Hours

	Izan
	Finished all the work for deleting/hiding recipes, both the logic and UI parts, and updated the Recipe Unit Tests
	4 Hours

	Emerson
	Worked on bug where recipe list does not inflate
	4 Hours

	Sam
	Investigated bugs with floating button elements in fragments not displaying correctly. Learned from documentation.
	1 hour

	Sam
	updated documentation: added more complete and missing features to README, added log.txt to repo
	30 minutes

	Sam
	Started cleaning up code: refactoring and formatters
	15 minutes

    Daniel
    Removed unused files and code, renamed layout files, commented presentation files, and made minor fixes
    2 hours

# Outstanding Bugs

## Back button not fully functional (severity: medium)
The back button currently only brings the user back to the initial page in the app, rather than the most recently visited page in the app
