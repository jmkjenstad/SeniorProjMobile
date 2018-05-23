package com.example.a7142885.doorpanes;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by Jayson on 2/25/2017.
 */

@RunWith(AndroidJUnit4.class)

public class UITest {

    private String username;
    private String pword;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        username = "abe.lincoln@sdsmt.edu";
        pword = "asdasdasd";
    }

    @Test

    public void loginTest(){
        username = "jayson";
        pword = "asdasdasd";
        // Type text and then press the button.
        onView(withId(R.id.email))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(pword), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView((withId(R.id.email_login_form))
        ).check(matches(isDisplayed()));



    }

    @Test
    public void loginTest2() {

        username = "abe.lincoln@sdsmt.edu";
        pword = "a";
        // Type text and then press the button.
        onView(withId(R.id.email))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(pword), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView((withId(R.id.email_login_form))
        ).check(matches(isDisplayed()));


    }

    @Test
    public void loginTest3() {

        username = "";
        pword = "asdasdasd";
        // Type text and then press the button.
        onView(withId(R.id.email))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(pword), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView((withId(R.id.email_login_form))
        ).check(matches(isDisplayed()));


    }

    @Test
    public void loginTest4() {

        username = "";
        pword = "";
        // Type text and then press the button.
        onView(withId(R.id.email))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(pword), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView((withId(R.id.email_login_form))
        ).check(matches(isDisplayed()));


    }



    @Test
    public void loginTest5() {

        username = "jayson.kjenstad@mines.sdsmt.edu";
        pword = "M!nes85";
        // Type text and then press the button.
        onView(withId(R.id.email))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(pword), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView((withId(R.id.content_choose))
        ).check(matches(isDisplayed()));


    }


}

