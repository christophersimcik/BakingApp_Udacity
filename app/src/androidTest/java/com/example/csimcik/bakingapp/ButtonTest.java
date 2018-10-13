package com.example.csimcik.bakingapp;
/**
 * Created by csimcik on 8/11/2017.
 */
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
       @RunWith(AndroidJUnit4.class)
public class ButtonTest {
           private IdlingResource idlingResource;
           private int numTest;

           @Rule
           public ActivityTestRule<MainActivity> mButton = new ActivityTestRule<>(MainActivity.class);

           @Before
           public void regIdleService() {
               idlingResource = new IdleRES(
                       InstrumentationRegistry.getTargetContext());
               IdlingRegistry.getInstance().register(idlingResource);
           }

           @Test
           public void ClickRecipeButton() {
               int pos;
               for (int i = 0; i < 4; i++) {
                   pos = i;
                   onView(withId(R.id.recycler_view_main))
                           .perform(RecyclerViewActions.scrollToPosition(i));
                   onView(withId(R.id.recycler_view_main))
                           .perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));

                   Log.i("insNum", String.valueOf(Detail.insView.getAdapter().getItemCount()));

                   int ingNum = Detail.ingView.getAdapter().getItemCount();
                   int insNum = Detail.insView.getAdapter().getItemCount();
                   numTest = insNum;
                   for (int j = 0; j < ingNum; j++) {
                       onView(withId(R.id.ing_view)).perform(RecyclerViewActions.scrollToPosition(j));
                   }






                   for (int h = 0; h < insNum; h++) {
                       onView(withId(R.id.ins_view)).perform(RecyclerViewActions.scrollToPosition(h));
                   }

                   Espresso.pressBack();

               }
           }
       }


