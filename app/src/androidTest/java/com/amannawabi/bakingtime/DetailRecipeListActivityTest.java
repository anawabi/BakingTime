package com.amannawabi.bakingtime;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DetailRecipeListActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void detailActivityTest() {
        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.recipe_grid_view),
                        childAtPosition(
                                withId(R.id.activity_recipe),
                                0)))
                .atPosition(0);
        linearLayout.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Recipe Ingredients"),
                        childAtPosition(
                                allOf(withId(R.id.lv),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Recipe Ingredients")));

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("Starting prep"),
                        childAtPosition(
                                allOf(withId(R.id.lv),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                2),
                        isDisplayed()));
        textView2.check(matches(withText("Starting prep")));

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("Prep the cookie crust."),
                        childAtPosition(
                                allOf(withId(R.id.lv),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                3),
                        isDisplayed()));
        textView3.check(matches(withText("Prep the cookie crust.")));

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("Finishing Steps"),
                        childAtPosition(
                                allOf(withId(R.id.lv),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                7),
                        isDisplayed()));
        textView4.check(matches(withText("Finishing Steps")));

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
