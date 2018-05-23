package com.example.a7142885.doorpanes;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by 7142885 on 2/27/2017.
 */

public class DashboardUITest {
    @Rule
    public ActivityTestRule<Dashboard> mActivityRule = new ActivityTestRule<>(
            Dashboard.class);




    @Test
    public void navDrawerTest(){

        onView(withId(R.id.toolbar))
                .perform(click());


        onView((withId(R.id.weekView))
        ).check(matches(isDisplayed()));



    }

    @Test
    public void fabTest(){

        onView(withId(R.id.fab))
                .perform(click());


        onView((withId(R.id.weekView))
        ).check(matches(isDisplayed()));



    }








}


