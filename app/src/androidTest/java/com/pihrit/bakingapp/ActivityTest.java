package com.pihrit.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.pihrit.bakingapp.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private CountingIdlingResource mCountingIdlingResource;

    @Before
    public void registerCountingIdlingResource() {
        mCountingIdlingResource = mActivityTestRule.getActivity().getCountingIdlingResource();
        IdlingRegistry.getInstance().register(mCountingIdlingResource);
    }

    @After
    public void unregisterCountingIdlingResource() {
        if (mCountingIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mCountingIdlingResource);
        }
    }

    @Test
    public void checkRecyclerViewIsVisible() {
        onView(withId(R.id.rv_recipes)).check(matches(isDisplayed()));
    }

    /**
     * Opens the DetailsActivity and checks that the ingredients textView has the wanted text
     */
    @Test
    public void verifyThatDetailActivityIsOpened() {
        onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.steps_fragment_container)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_step_ingredients)).check(matches(withText(R.string.ingredients)));
    }
}
