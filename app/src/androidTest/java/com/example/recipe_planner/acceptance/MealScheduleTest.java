package com.example.recipe_planner.acceptance;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

import android.text.format.DateFormat;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.recipe_planner.R;
import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.business.AccessSchedule;
import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.presentation.MainActivity;
import com.example.recipe_planner.utils.CalendarUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MealScheduleTest {
    private static final String testDbName = "database/RecipesTest";
    private static DataAccess dataAccess;
    private AccessRecipes accessRecipes;
    private AccessSchedule accessSchedule;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        dataAccess = Services.getDataAccess();
        accessRecipes = new AccessRecipes();
        accessSchedule = new AccessSchedule();
        dataAccess.reset();
    }

    @After
    public void tearDown() {}

    public void navigateToMealSchedule() {
        onView(withId(R.id.mealSchedule)).perform(click());
    }

    private String formatDay(Date date) {
        return DateFormat.format("EEE MMM dd", date).toString();
    }

    @Test
    public void updateViewedScheduleTest() {
        navigateToMealSchedule();

        Date date = Calendar.getInstance().getTime();
        String formattedDate = formatDay(date);

        onView(withId(R.id.dateText)).check(matches(withText(containsString(formattedDate))));
        onView(withId(R.id.nextDayButton)).perform(click());

        date = CalendarUtils.incrementDay(date, 1);
        formattedDate = formatDay(date);

        onView(withId(R.id.dateText)).check(matches(withText(containsString(formattedDate))));

        date = CalendarUtils.incrementDay(date, -1);
        formattedDate = formatDay(date);

        onView(withId(R.id.previousDayButton)).perform(click());

        onView(withId(R.id.dateText)).check(matches(withText(containsString(formattedDate))));

        date = CalendarUtils.incrementDay(date, -1);
        formattedDate = formatDay(date);

        onView(withId(R.id.previousDayButton)).perform(click());
        onView(withId(R.id.dateText)).check(matches(withText(containsString(formattedDate))));

        onView(withId(R.id.nextDayButton)).perform(click());
    }

    @Test
    public void noScheduledRecipesTest() {
        navigateToMealSchedule();

        // No meals are scheduled on this day
        onView(withId(R.id.breakfastRecipeName))
                .check(matches(withText(containsString("No Meal Scheduled"))));
        onView(withId(R.id.lunchRecipeName))
                .check(matches(withText(containsString("No Meal Scheduled"))));
        onView(withId(R.id.dinnerRecipeName))
                .check(matches(withText(containsString("No Meal Scheduled"))));

        onView(withId(R.id.descheduleBreakfastButton)).check(matches(isNotEnabled()));
        onView(withId(R.id.descheduleLunchButton)).check(matches(isNotEnabled()));
        onView(withId(R.id.descheduleDinnerButton)).check(matches(isNotEnabled()));
    }

    @Test
    public void scheduleMealTest() {
        Recipe recipe = accessRecipes.getRecipes().get(0);
        Date date = Calendar.getInstance().getTime();
        accessSchedule.setMeal(date, DaySchedule.Meal.BREAKFAST, recipe);

        navigateToMealSchedule();

        onView(withId(R.id.dateText)).check(matches(withText(formatDay(date))));

        onView(withId(R.id.breakfastRecipeName))
                .check(matches(withText(containsString(recipe.getName()))));
        onView(withId(R.id.descheduleBreakfastButton)).check(matches(isEnabled()));
    }

    @Test
    public void descheduleMealTest() {
        Recipe recipe = accessRecipes.getRecipes().get(0);
        Date date = Calendar.getInstance().getTime();
        accessSchedule.setMeal(date, DaySchedule.Meal.BREAKFAST, recipe);

        navigateToMealSchedule();
        onView(withId(R.id.descheduleBreakfastButton)).perform(click());

        onView(withId(R.id.descheduleBreakfastButton)).check(matches(isNotEnabled()));
        onView(withId(R.id.breakfastRecipeName))
                .check(matches(withText(containsString("No Meal Scheduled"))));
    }
}
