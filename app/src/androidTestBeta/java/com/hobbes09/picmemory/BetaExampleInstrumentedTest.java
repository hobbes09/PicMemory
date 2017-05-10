package com.hobbes09.picmemory;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.test.InstrumentationTestCase;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import com.hobbes09.picmemory.utils.Contants;
import com.hobbes09.picmemory.view.activity.MainActivity;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BetaExampleInstrumentedTest extends InstrumentationTestCase {

    private UiDevice device;
    private MockWebServer server;

    @Rule
    public ActivityTestRule<MainActivity> flowRule = new ActivityTestRule(
            MainActivity.class){

        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();

            device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            assertThat(device, notNullValue());
        }
    };


    @Before
    public void beforeTest() throws Exception {
        // Called before each test
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        assertThat(device, notNullValue());

    }

    @After
    public void afterTest() {
        // Called after each test
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.hobbes09.picmemory.beta", appContext.getPackageName());
    }

    @Test
    public void testGameFlow() throws Exception {
        // TODO : Should be using Idling resource instead of this
        Thread.sleep(25000);

        for (int i = 0; i < 9; i++){
            clickCell(i);
        }

        // Check toast message
        onView(withText("Great job !!")).inRoot(withDecorView(not(is(
                flowRule.getActivity().getWindow().getDecorView())))).check(matches(
                isDisplayed()));

    }

    public void clickCell(int index) throws InterruptedException {
        Thread.sleep(500);

        onView(withId(R.id.rvGrid))
                .perform(actionOnItemAtPosition(index, click()));

    }



}
