package com.amannawabi.bakingtime;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class IngredientsActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdelingResource;

    @Before
    public void registerIdelingResource() {

        mIdelingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdelingResource);
    }

    @Test
    public void loadRecipeTest() {
        Espresso.onView(ViewMatchers.withId(R.id.recipe_rv)).perform(RecyclerViewActions.scrollToPosition(0));
        Espresso.onView(withText("NUTELLA PIE")).check(matches(isDisplayed()));
    }

    @After
    public void unRegisterIdelingResource() {
        IdlingRegistry.getInstance().unregister(mIdelingResource);
    }

    @Test
    public void ingredientsActivityTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipe_rv),
                        childAtPosition(
                                withId(R.id.activity_recipe),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.lv),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.ingredients_rv),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredients_fragment),
                                        0),
                                0),
                        isDisplayed()));
        recyclerView3.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.ingredient_name_tv), withText("Graham Cracker crumbs"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredeint_layout),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Graham Cracker crumbs")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.ingredient_name_tv), withText("cream cheese(softened)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredeint_layout),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("cream cheese(softened)")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
