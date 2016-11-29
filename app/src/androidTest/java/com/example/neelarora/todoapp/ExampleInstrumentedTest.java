package com.example.neelarora.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.MenuItem;
import android.view.View;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import static java.util.EnumSet.allOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.neelarora.todoapp", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void test_newTask() {

        //Go through the steps to create a task, then cancel it at the end
        onView(withId(R.id.action_add_task)).perform(click());
        onView(withText("Add a new task")).check(matches(isDisplayed()));
        onView(withClassName(endsWith("EditText"))).perform(typeText("Test1"));
        onView(withId(android.R.id.button2)).perform(click());
        //onView(withId(R.id.list_todo))
        //        .check(matches(not(withAdaptedData(withItemContent("Test1")))));

        //Create a task
        onView(withId(R.id.action_add_task)).perform(click());
        onView(withText("Add a new task")).check(matches(isDisplayed()));
        onView(withClassName(endsWith("EditText"))).perform(typeText("Test1"));
        onView(withId(android.R.id.button1)).perform(click());
        //Confirm that it has been displayed
        onView(withText("Test1")).check(matches(isDisplayed()));
        onView(allOf(withText("Done"), hasSibling(withText("Test1")))).perform(click());
    }

    @Test
    public void test_removeTask() {

        for (int i = 0; i < 9; i++) {
            //Create a task
            onView(withId(R.id.action_add_task)).perform(click());
            onView(withText("Add a new task")).check(matches(isDisplayed()));
            onView(withClassName(endsWith("EditText"))).perform(typeText("Test" + Integer.toString(i)));
            onView(withId(android.R.id.button1)).perform(click());
        }
        for (int i = 0; i < 9; i++)    {
            //Remove a task
            onView(allOf(withText("Done"), hasSibling(withText("Test" + Integer.toString(i))))).perform(click());
        }

    }

    @Test
    public void test_geoTag()    {
        //Create a task
        onView(withId(R.id.action_add_task)).perform(click());
        onView(withText("Add a new task")).check(matches(isDisplayed()));
        onView(withClassName(endsWith("EditText"))).perform(typeText("Test1"));
        onView(withId(android.R.id.button1)).perform(click());
        //Confirm that it has been displayed
        onView(withText("Test1")).check(matches(isDisplayed()));

        //Only name
        onView(allOf(withText("GeoTag"), hasSibling(withText("Test1")))).perform(click());
        onView(withId(R.id.fragment_add_geofence_name)).perform(typeText("Geotag1"));
        onView(withText("Add")).perform(click());
        onView(withText("Cancel")).perform(click());

        //Only Lat
        onView(allOf(withText("GeoTag"), hasSibling(withText("Test1")))).perform(click());
        onView(withId(R.id.fragment_add_geofence_latitude)).perform(typeText("45"));

        onView(withText("Add")).perform(click());
        onView(withText("Cancel")).perform(click());

        //Only Long
        onView(allOf(withText("GeoTag"), hasSibling(withText("Test1")))).perform(click());
        onView(withId(R.id.fragment_add_geofence_longitude)).perform(typeText("45"));
        onView(withText("Add")).perform(click());
        onView(withText("Cancel")).perform(click());

        //Only rad
        onView(allOf(withText("GeoTag"), hasSibling(withText("Test1")))).perform(click());
        onView(withId(R.id.fragment_add_geofence_radius)).perform(typeText("1"));
        onView(withText("Add")).perform(click());
        onView(withText("Cancel")).perform(click());

        //All Fields, Lat High
        onView(allOf(withText("GeoTag"), hasSibling(withText("Test1")))).perform(click());
        onView(withId(R.id.fragment_add_geofence_name)).perform(typeText("Geotag1"));
        onView(withId(R.id.fragment_add_geofence_latitude)).perform(typeText("91"));
        onView(withId(R.id.fragment_add_geofence_longitude)).perform(typeText("45"));
        onView(withId(R.id.fragment_add_geofence_radius)).perform(typeText("1"));
        onView(withText("Add")).perform(click());

        //All Fields, Lat Low
        onView(withId(R.id.fragment_add_geofence_latitude)).perform(replaceText("-91"));
        onView(withText("Add")).perform(click());
        onView(withId(R.id.fragment_add_geofence_latitude)).perform(replaceText("45"));

        //All Fields, Long High
        onView(withId(R.id.fragment_add_geofence_longitude)).perform(replaceText("181"));
        onView(withText("Add")).perform(click());

        //All Fields, Long Low
        onView(withId(R.id.fragment_add_geofence_longitude)).perform(replaceText("-181"));
        onView(withText("Add")).perform(click());
        onView(withId(R.id.fragment_add_geofence_longitude)).perform(replaceText("45"));

        //All Fields, Radius High
        onView(withId(R.id.fragment_add_geofence_radius)).perform(replaceText("21"));
        onView(withText("Add")).perform(click());

        //All Fields, Radius Low
        onView(withId(R.id.fragment_add_geofence_radius)).perform(replaceText("-1"));
        onView(withText("Add")).perform(click());

        //All Fields, Valid Entry
        onView(withId(R.id.fragment_add_geofence_radius)).perform(replaceText("1"));
        onView(withText("Add")).perform(click());


        onView(allOf(withText("Done"), hasSibling(withText("Test1")))).perform(click());

        //button2 = cancel
        //button1 = add
    }


    @Test
    public void test_editTask()   {
        //Create a task
        onView(withId(R.id.action_add_task)).perform(click());
        onView(withText("Add a new task")).check(matches(isDisplayed()));
        onView(withClassName(endsWith("EditText"))).perform(typeText("Test1"));
        onView(withId(android.R.id.button1)).perform(click());
        //Confirm that it has been displayed
        onView(withText("Test1")).check(matches(isDisplayed()));
        //Setup to edit task, then cancel
        onView(allOf(withText("Edit"), hasSibling(withText("Test1")))).perform(click());
        onView(withText("What do you want to change?")).check(matches(isDisplayed()));
        onView(withClassName(endsWith("EditText"))).perform(typeText("Test1--Changed"));
        onView(withId(android.R.id.button2)).perform(click());
        //Confirm the task wasn't edited
        onView(withText("Test1")).check(matches(isDisplayed()));
        //Edit the task
        onView(allOf(withText("Edit"), hasSibling(withText("Test1")))).perform(click());
        onView(withText("What do you want to change?")).check(matches(isDisplayed()));
        onView(withClassName(endsWith("EditText"))).perform(typeText("Test1--Changed"));
        onView(withId(android.R.id.button1)).perform(click());

        //Confirm that it has been changed
        onView(withText("Test1--Changed")).check(matches(isDisplayed()));

        onView(allOf(withText("Done"), hasSibling(withText("Test1--Changed")))).perform(click());
    }
}
